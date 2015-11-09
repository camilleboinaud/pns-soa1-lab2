package fr.unice.polytech.si5.soa1.lab2.flows.processors.minibo;

import fr.unice.polytech.si5.soa1.lab2.flows.business.OrderItem;
import fr.unice.polytech.si5.soa1.lab2.flows.business.shopping3000.Manufacturer;
import fr.unice.polytech.si5.soa1.lab2.flows.utils.Pair;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.jms.JmsMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by camille on 09/11/15.
 */
public class ExchangePairToItemIdListProcessor implements Processor {

    public void process(Exchange exchange) throws Exception {
        List<Exchange> in = exchange.getIn().getBody(List.class);

        List<Integer> itms = new ArrayList<Integer>();

        for(Exchange exc : in){
            itms.add(((Pair<OrderItem, Integer>) exc.getIn().getBody()).getLeft().getManufacturerId());
        }

        exchange.getIn().setBody(itms);
    }

}
