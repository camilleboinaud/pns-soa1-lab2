package fr.unice.polytech.si5.soa1.lab2.flows.processors.common;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lecpie on 11/14/15.
 */
public class MailRequestProcessor implements Processor {
    public void process(Exchange exchange) throws Exception {
        String text = exchange.getIn().getBody(String.class);
        String email = exchange.getProperty("email", String.class);

        List<String> array = new ArrayList();
        array.add(email);
        array.add(text);

        exchange.getIn().setBody(array);
    }
}
