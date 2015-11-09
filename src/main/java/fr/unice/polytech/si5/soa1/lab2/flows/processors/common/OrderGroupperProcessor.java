package fr.unice.polytech.si5.soa1.lab2.flows.processors.common;

import fr.unice.polytech.si5.soa1.lab2.flows.business.shopping3000.Manufacturer;
import fr.unice.polytech.si5.soa1.lab2.flows.utils.Pair;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.jms.JmsMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by camille on 08/11/15.
 */
public class OrderGroupperProcessor implements Processor {

    public void process(Exchange exchange) throws Exception {
        List<Exchange> in = exchange.getIn(JmsMessage.class).getBody(List.class);
        List<Pair<Manufacturer,Integer>> idList = new ArrayList<Pair<Manufacturer,Integer>>();

        for (Exchange e : in) {
            idList.add((Pair<Manufacturer,Integer>)e.getIn().getBody());
        }

        exchange.getIn().setBody(idList);
    }

}
