package fr.unice.polytech.si5.soa1.lab2.flows.processors.common;

import fr.unice.polytech.si5.soa1.lab2.flows.business.shopping3000.Manufacturer;
import fr.unice.polytech.si5.soa1.lab2.flows.utils.Pair;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 * Created by camille on 08/11/15.
 */
public class IDEnhancementProcessor implements Processor{

    public void process(Exchange exchange) throws Exception {

        Integer id = exchange.getIn().getBody(Integer.class);

        Manufacturer manufacturer = Manufacturer.valueOf(exchange.getIn().getHeader("manufacturer", String.class));

        Pair<Manufacturer,Integer> shopping3000ID = new Pair<Manufacturer,Integer>();
        shopping3000ID.setRight(id);
        shopping3000ID.setLeft(manufacturer);

        exchange.getIn().setBody(shopping3000ID);

    }

}
