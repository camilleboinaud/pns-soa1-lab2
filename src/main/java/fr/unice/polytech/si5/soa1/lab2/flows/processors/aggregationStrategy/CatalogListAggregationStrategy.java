package fr.unice.polytech.si5.soa1.lab2.flows.processors.aggregationStrategy;

import fr.unice.polytech.si5.soa1.lab2.flows.business.shopping3000.Shopping3000CatalogItem;
import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sth on 11/8/15.
 */
public class CatalogListAggregationStrategy implements AggregationStrategy {
    public Exchange aggregate(Exchange exchange0, Exchange exchange1) {
        System.out.println("CatalogListAggregationStrategy");
        if (exchange0 == null) {
            return exchange1;
        } else {
            List<Shopping3000CatalogItem> firstList = (ArrayList<Shopping3000CatalogItem>)exchange0.getIn().getBody();
            List<Shopping3000CatalogItem> secondList = (ArrayList<Shopping3000CatalogItem>)exchange1.getIn().getBody();
            List<Shopping3000CatalogItem> newList = new ArrayList<Shopping3000CatalogItem>();
            newList.addAll(firstList);
            newList.addAll(secondList);
            exchange0.getIn().setBody(newList);
            return exchange0;
        }
    }
}
