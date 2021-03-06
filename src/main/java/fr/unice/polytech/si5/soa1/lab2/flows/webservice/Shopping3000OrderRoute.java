package fr.unice.polytech.si5.soa1.lab2.flows.webservice;

import static fr.unice.polytech.si5.soa1.lab2.flows.utils.Endpoints.*;

import fr.unice.polytech.si5.soa1.lab2.flows.business.Customer;
import fr.unice.polytech.si5.soa1.lab2.flows.business.Order;
import fr.unice.polytech.si5.soa1.lab2.flows.business.OrderItem;
import fr.unice.polytech.si5.soa1.lab2.flows.processors.common.*;
import fr.unice.polytech.si5.soa1.lab2.flows.request.Shopping3000RequestBuilder;
import fr.unice.polytech.si5.soa1.lab2.flows.utils.Pair;
import fr.unice.polytech.si5.soa1.lab2.flows.utils.Shopping3000Info;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.processor.aggregate.GroupedExchangeAggregationStrategy;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lecpie on 11/9/15.
 */
public class Shopping3000OrderRoute extends RouteBuilder {

    private static Map<Integer, Order> orders = new HashMap<Integer, Order>();
    private static int nextid = 0;

    private synchronized int getNextid() {
        return nextid++;
    }

    private static Processor exclstsum = new SumExchangeListProcessor();
    private static Processor itm2ctlgitmrqst = new OrderItemToItemRequest();
    private static Processor mltplwthqty = new MultiplyWithQtyProcessor();
    private static Processor addmetaid = new AddMetaIdProcessor();
    private static Processor addDeliveryToOrderAmount = new AddDeliveryToOrderAMountProcessor();


    public int startOrder() {
        int id = getNextid();

        orders.put(id, new Order());

        return id;
    }

    public void addOrderItem(int orderId, OrderItem orderItem, int qty) {
        Order order = orders.get(orderId);

        Pair <OrderItem, Integer> newItem = new Pair<OrderItem, Integer>();
        newItem.setLeft(orderItem);
        newItem.setRight(qty);

        order.getItems().add(newItem);
    }

    public void setCustomer(int orderId, Customer customer) {
        orders.get(orderId).setCustomer(customer);
    }

    public boolean validateOrder(int orderId) {
        return orders.containsKey(orderId) && orders.get(orderId).getItems().size() > 0
                && orders.get(orderId).getCustomer() != null;
    }

    public double getAmount (int orderId) {
        return orders.get(orderId).getItems().size();
    }

    public Order getOrder(int orderId) {
        return orders.get(orderId);
    }

    public int registerOrder(Order order) {
        int id = getNextid();
        orders.put(id, order);

        return id;
    }

    @Override
    public void configure() throws Exception {

        from(ORDER_SERVICE_INTERCEPTOR)
                .choice()
                .when(simple("${in.headers.operationName} == 'start_order'"))
                    .to("direct:start_order")
                .when(simple("${in.headers.operationName} == 'add_order_item'"))
                    .to("direct:add_order_item")
                .when(simple("${in.headers.operationName} == 'set_customer'"))
                    .to("direct:set_customer")
                .when(simple("${in.headers.operationName} == 'validate_order'"))
                    .to("direct:validate_order")
                .when(simple("${in.headers.operationName} == 'get_amount'"))
                    .to("direct:get_amount")
                .when(simple("${in.headers.operationName} == 'get_order'"))
                    .to("direct:get_order")
                .when(simple("${in.headers.operationName} == 'register_order'"))
                    .to("direct:register_order")
                .otherwise()
                .log("### Unexpected request in order route")
                    .stop()
                .endChoice()
        ;

        from(START_ORDER)
            .log("### Start_order")
            .bean(Shopping3000OrderRoute.class, "startOrder()")
            .log("[ORDER n°${body}] Set order id : ${body}")
        ;

        from(ADD_ORDER_ITEM)
                .log("add_order_item")
                .bean(Shopping3000OrderRoute.class, "addOrderItem(${body[0]},${body[1]},${body[2]})")
        ;

        from(SET_CUSTOMER)
                .log("set_customer")
                .bean(Shopping3000OrderRoute.class, "setCustomer(${body[0]},${body[1]})")
        ;

        from(VALIDATE_ORDER)
                .log("[ORDER n°${exchangeProperty.shop3000_order_id}] Validate_order with id ${body}")
                .setProperty("order_id", body())
                .setHeader("shop3000_order_id", body())
                .bean(Shopping3000OrderRoute.class, "validateOrder(${body})")
                .choice()
                .when(simple("${body} == true"))
                    .setBody(property("order_id"))
                    .setHeader("shop3000_order_id", body())
                    .bean(Shopping3000OrderRoute.class, "getOrder(${body})")
                    .process(addmetaid)
                    .log("[ORDER n°${exchangeProperty.shop3000_order_id}] Sending order to order processing route")
                    .to(SHOPPING_3000_PAYMENT)
                    .setBody(constant(true))
                .otherwise()
                    .setBody(constant(false))
                .end()
                .log("[ORDER n°${exchangeProperty.shop3000_order_id}] Validated order : ${body}")
        ;

        from(GET_AMOUNT)
                .to(GET_ORDER)
                .setProperty("dstAddress", simple("${body.customer.address}"))
                .split(simple("${body.items}"))
                .aggregationStrategy(new GroupedExchangeAggregationStrategy())
                    .setProperty("qty", simple("${body.right}"))
                    .setBody(simple("${body.left}"))
                    .process(itm2ctlgitmrqst)
                    .to(HANDLE_FULL_CATALOG_GET_ITEM)
                    .setBody(simple("${body.price}"))
                    .process(mltplwthqty)
                .end()
                .process(exclstsum)
                .setHeader("order_amount", body())
                .bean(Shopping3000RequestBuilder.class, "buildDeliveryPriceRequest(${exchangeProperty.dstAddress})")
                .to(SHOPPING_3000_DELIVERY_SERVICE)
                .process(addDeliveryToOrderAmount)
                .log("[ORDER n°${exchangeProperty.shop3000_order_id}] Get amount : ${body}")
        ;


        from(GET_ORDER)
                .bean(Shopping3000OrderRoute.class, "getOrder(${body})")
                .end()
        ;

        from (REGISTER_ORDER)
                .bean(Shopping3000OrderRoute.class, "registerOrder(${body})")
                .end()
        ;

    }


}
