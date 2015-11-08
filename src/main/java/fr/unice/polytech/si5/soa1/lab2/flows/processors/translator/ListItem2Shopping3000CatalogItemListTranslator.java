package fr.unice.polytech.si5.soa1.lab2.flows.processors.translator;

import fr.unice.polytech.si5.soa1.lab2.flows.business.shopping3000.Shopping3000CatalogItemList;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.List;

/**
 * Created by sth on 11/8/15.
 */
public class ListItem2Shopping3000CatalogItemListTranslator {

    /**
     * translate the list into list of shopping3000 catalogItem
     */
    public static Processor list2CatalogListTranslator = new Processor() {
        public void process(Exchange exchange) throws Exception {
            List list = exchange.getIn().getBody(List.class);
            System.out.println(list.toString());
            Shopping3000CatalogItemList newList = new Shopping3000CatalogItemList();
            newList.setCatalogItemList(list);
            exchange.getIn().setBody(newList);
        }
    };
}
