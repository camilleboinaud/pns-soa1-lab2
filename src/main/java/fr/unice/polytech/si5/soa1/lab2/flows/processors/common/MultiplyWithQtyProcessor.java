package fr.unice.polytech.si5.soa1.lab2.flows.processors.common;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 * Created by lecpie on 11/14/15.
 */
public class MultiplyWithQtyProcessor implements Processor {
    public void process(Exchange exchange) throws Exception {
        double price = exchange.getIn().getBody(Double.class);
        int qty = exchange.getProperty("qty", Integer.class);

        exchange.getIn().setBody(qty * price);
    }
}
