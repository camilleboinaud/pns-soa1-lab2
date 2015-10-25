package fr.unice.polytech.si5.soa1.lab2.flows.utils.aggregators;

import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by camille on 25/10/15.
 */
public class ListAggregationStrategy implements AggregationStrategy{

    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {

        if (oldExchange == null) {
            List<Object> list = new ArrayList<Object>();
            list.add(newExchange.getIn().getBody());
            newExchange.getIn().setBody(list);
            return newExchange;
        } else {
            List<Object> list = oldExchange.getIn().getBody(ArrayList.class);
            list.add(newExchange.getIn().getBody());
            return oldExchange;
        }

    }

}
