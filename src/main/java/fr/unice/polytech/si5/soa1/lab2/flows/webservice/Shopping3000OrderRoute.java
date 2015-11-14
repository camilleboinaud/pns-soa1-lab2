package fr.unice.polytech.si5.soa1.lab2.flows.webservice;

import fr.unice.polytech.si5.soa1.lab2.flows.business.Customer;
import fr.unice.polytech.si5.soa1.lab2.flows.business.Order;
import fr.unice.polytech.si5.soa1.lab2.flows.business.OrderItem;
import fr.unice.polytech.si5.soa1.lab2.flows.processors.common.OrderResponseToOrder;
import fr.unice.polytech.si5.soa1.lab2.flows.request.CatalogueRequestBuilder;
import fr.unice.polytech.si5.soa1.lab2.flows.utils.Endpoints;
import fr.unice.polytech.si5.soa1.lab2.flows.utils.Pair;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lecpie on 11/9/15.
 */
public class Shopping3000OrderRoute extends RouteBuilder {

    private static Map<Integer, Order> orders = new HashMap<Integer, Order>();
    private static int nextid = 0;

    private static Processor res2order = new OrderResponseToOrder();

    private synchronized int getNextid() {
        return nextid++;
    }

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


    @Override
    public void configure() throws Exception {

        from("cxf:/Shopping3000OrderService?serviceClass=fr.unice.polytech.si5.soa1.lab2.flows.webservice.Shopping3000OrderService&wrapped=true")
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
                .otherwise()
                .log("unexpected request in order route")
                    .stop()
                .endChoice()
        ;

        from("direct:start_order")
            .log("start_order")
            .bean(Shopping3000OrderRoute.class, "startOrder()")
            .log("set order id : ${body}")
        ;

        from("direct:add_order_item")
                .log("add_order_item")
                .bean(Shopping3000OrderRoute.class, "addOrderItem(${body[0]},${body[1]},${body[2]})")
        ;

        from("direct:set_customer")
                .log("set_customer")
                .bean(Shopping3000OrderRoute.class, "setCustomer(${body[0]},${body[1]})")
        ;

        from("direct:validate_order")
                .log("validate_order")
                .setProperty("order_id", body())
                .bean(Shopping3000OrderRoute.class, "validateOrder(${body})")
                .log("validate order state : ${body}")
                .choice()
                .when(simple("${body} == true"))
                    .setBody(property("order_id"))
                    .bean(Shopping3000OrderRoute.class, "getOrder(${body})")
                    .to(Endpoints.HANDLE_FULL_ORDER)
                    .setBody(constant(true))
                .otherwise()
                    .setBody(constant(false))
                .end()
                .log("validated order : ${body}")
        ;

        from("direct:get_amount")
                .log("get_amount")
                .bean(Shopping3000OrderRoute.class, "getAmount(${body})")
                .log("amount : ${body}")
        ;

        from("direct:get_order")
                .bean(Shopping3000OrderRoute.class, "getOrder(${body})")
                //.process(res2order)
                ;


    }


}
