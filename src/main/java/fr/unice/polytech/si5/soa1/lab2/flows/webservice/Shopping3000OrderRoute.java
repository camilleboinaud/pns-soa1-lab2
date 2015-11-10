package fr.unice.polytech.si5.soa1.lab2.flows.webservice;

import fr.unice.polytech.si5.soa1.lab2.flows.business.Customer;
import fr.unice.polytech.si5.soa1.lab2.flows.business.Order;
import fr.unice.polytech.si5.soa1.lab2.flows.business.OrderItem;
import fr.unice.polytech.si5.soa1.lab2.flows.utils.Pair;
import org.apache.camel.builder.RouteBuilder;

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

    public int startOrder() {
        int id = getNextid();

        orders.put(id, new Order());

        return id;
    }

    public void addOrderItem(int orderId, OrderItem orderItem, int qty) {
        Order order = orders.get(orderId);

        Pair <OrderItem, Integer> newItem = new Pair<OrderItem, Integer>();
        newItem.setLeft(orderItem);

        order.getItems().add(null);
    }

    public void setCustomer(int orderId, Customer customer) {

    }

    public boolean validateOrder(int orderId) {
        return false;
    }

    @Override
    public void configure() throws Exception {

        from("cxf:/Shopping3000OrderService?serviceClass=fr.unice.polytech.si5.soa1.lab2.flows.webservice.Shopping3000OrderService")
                .choice()
                .when(simple("${in.headers.operationName} == 'start_order'"))
                    .log("start_order")
                    .to("direct:start_order")
                .when(simple("${in.headers.operationName} == 'add_order_item'"))
                    .log("add_order_item")
                    .to("direct:add_order_item")
                .when(simple("${in.headers.operationName} == 'set_customer'"))
                    .log("set_customer")
                    .to("direct:set_customer")
                .when(simple("${in.headers.operationName} == 'validate_order'"))
                    .log("validate_order")
                    .to("direct:validate_order")
                .when(simple("${in.headers.operationName} == 'get_amount'"))
                    .log("get_amount")
                    .to("direct:get_amount")
                .otherwise()
                .log("unexpected request in order route")
                    .stop()
                .endChoice()
        ;

        from("direct:start_order")
            .log("start_order")
            .setBody(constant(3))
            .log("set order id : ${body}")
        ;

        from("direct:add_order_item")
                .log("add_order_item")
        ;

        from("direct:set_customer")
                .log("set_customer")
        ;

        from("direct:validate_order")
                .log("validate_order")
                .setBody(constant(true))
                .log("validated order : ${body}")
        ;

        from("direct:get_amount")
                .log("get_amount")
                .setBody(constant(1000.0))
                .log("amount : ${body}")
        ;


    }
}
