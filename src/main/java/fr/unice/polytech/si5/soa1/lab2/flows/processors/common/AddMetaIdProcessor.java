package fr.unice.polytech.si5.soa1.lab2.flows.processors.common;

import fr.unice.polytech.si5.soa1.lab2.flows.business.Order;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 * Created by lecpie on 11/14/15.
 */
public class AddMetaIdProcessor implements Processor {
    public void process(Exchange exchange) throws Exception {
        Order order = exchange.getIn().getBody(Order.class);
        int id = exchange.getIn().getHeader("shop3000_order_id", Integer.class);

        order.setId(id);
        exchange.getIn().setBody(order);
    }
}
