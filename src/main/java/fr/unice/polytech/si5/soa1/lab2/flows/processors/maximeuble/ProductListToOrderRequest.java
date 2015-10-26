package fr.unice.polytech.si5.soa1.lab2.flows.processors.maximeuble;

import fr.unice.polytech.si5.soa1.lab2.flows.business.Order;
import fr.unice.polytech.si5.soa1.lab2.flows.business.maximeuble.OrderItem;
import fr.unice.polytech.si5.soa1.lab2.flows.business.maximeuble.OrderRequest;
import fr.unice.polytech.si5.soa1.lab2.flows.business.maximeuble.Product;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.Collection;
import java.util.List;

/**
 * Created by lecpie on 10/26/15.
 */
public class ProductListToOrderRequest implements Processor{
    public void process(Exchange exchange) throws Exception {
        // retrieving the body of the exchanged message
        List<OrderItem> input = (List<OrderItem>) exchange.getIn().getBody();
        // transforming the input
        OrderRequest output = builder(input);
        // Putting the output inside the body of the message
        exchange.getIn().setBody(output);
    }

    OrderRequest builder(List<OrderItem> productList) {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setOrder(productList);

        return orderRequest;
    }
}
