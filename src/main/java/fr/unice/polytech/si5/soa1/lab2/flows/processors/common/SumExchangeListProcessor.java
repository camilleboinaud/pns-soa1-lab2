package fr.unice.polytech.si5.soa1.lab2.flows.processors.common;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.List;

/**
 * Created by lecpie on 11/14/15.
 */
public class SumExchangeListProcessor implements Processor {
    public void process(Exchange exchange) throws Exception {
        List<Exchange> list = exchange.getIn().getBody(List.class);

        double sum = 0.0;
        for (Exchange e : list) {
            sum += e.getIn().getBody(Integer.class);
        }

        exchange.getIn().setBody(sum);
    }
}
