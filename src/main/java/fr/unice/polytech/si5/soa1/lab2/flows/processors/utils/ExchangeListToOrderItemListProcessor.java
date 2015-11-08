package fr.unice.polytech.si5.soa1.lab2.flows.processors.utils;

import fr.unice.polytech.si5.soa1.lab2.flows.business.maximeuble.OrderItem;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.jms.JmsMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by camille on 08/11/15.
 */
public class ExchangeListToOrderItemListProcessor implements Processor {

    public void process(Exchange exchange) throws Exception {
        List<Exchange> in = exchange.getIn().getBody(List.class);

        List<OrderItem> itms = new ArrayList<OrderItem>();

        for (Exchange exc : in) {
            itms.add(exc.getIn(JmsMessage.class).getBody(OrderItem.class));
        }

        exchange.getIn().setBody(itms);
    }

}
