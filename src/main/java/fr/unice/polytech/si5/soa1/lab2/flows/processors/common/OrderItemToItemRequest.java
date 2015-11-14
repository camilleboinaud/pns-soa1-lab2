package fr.unice.polytech.si5.soa1.lab2.flows.processors.common;

import fr.unice.polytech.si5.soa1.lab2.flows.business.OrderItem;
import fr.unice.polytech.si5.soa1.lab2.flows.business.shopping3000.Manufacturer;
import fr.unice.polytech.si5.soa1.lab2.flows.utils.Pair;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 * Created by lecpie on 11/14/15.
 */
public class OrderItemToItemRequest implements Processor {
    public void process(Exchange exchange) throws Exception {
        OrderItem orderItem = exchange.getIn().getBody(OrderItem.class);

        Pair<Manufacturer,Integer> request = new Pair<Manufacturer, Integer>();
        request.setLeft(orderItem.getManufacturer());
        request.setRight(orderItem.getManufacturerId());

        exchange.getIn().setBody(request);
    }
}
