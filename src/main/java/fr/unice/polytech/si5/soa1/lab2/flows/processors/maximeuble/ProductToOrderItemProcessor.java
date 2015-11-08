package fr.unice.polytech.si5.soa1.lab2.flows.processors.maximeuble;

import fr.unice.polytech.si5.soa1.lab2.flows.business.maximeuble.*;
import fr.unice.polytech.si5.soa1.lab2.flows.utils.Pair;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 * Created by camille on 08/11/15.
 */
public class ProductToOrderItemProcessor implements Processor {

    public void process(Exchange exchange) throws Exception {
        // retrieving the body of the exchanged message
        Product input = (Product) exchange.getIn().getBody();
        // transforming the input
        Pair<fr.unice.polytech.si5.soa1.lab2.flows.business.OrderItem, Integer> item = exchange.getIn().getHeader("item",
                Pair.class);
        OrderItem output = builder(input, item);
        // Putting the output inside the body of the message
        exchange.getIn().setBody(output);
    }

    private OrderItem builder (Product input,  Pair<fr.unice.polytech.si5.soa1.lab2.flows.business.OrderItem, Integer> meta) {
        OrderItem orderItem = new OrderItem();

        orderItem.setProduct(input);
        orderItem.setQty(meta.getRight());
        orderItem.setOption(""); //TODO unmock

        return orderItem;
    }
}
