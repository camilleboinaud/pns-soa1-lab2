package fr.unice.polytech.si5.soa1.lab2.flows.processors.common;

import fr.unice.polytech.si5.soa1.lab2.flows.business.shopping3000.Shopping3000ID;
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
        List<Shopping3000ID> idList = new ArrayList<Shopping3000ID>();

        for (Exchange e : in) {
            idList.add(e.getIn().getBody(Shopping3000ID.class));
        }

        exchange.getIn().setBody(idList);
    }

}
