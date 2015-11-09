package fr.unice.polytech.si5.soa1.lab2.flows.processors.translator;

import fr.unice.polytech.si5.soa1.lab2.flows.business.maximeuble.Product;
import fr.unice.polytech.si5.soa1.lab2.flows.business.minibo.Item;
import fr.unice.polytech.si5.soa1.lab2.flows.business.shopping3000.Manufacturer;
import fr.unice.polytech.si5.soa1.lab2.flows.business.shopping3000.Shopping3000CatalogItem;
import fr.unice.polytech.si5.soa1.lab2.flows.utils.Pair;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 * Created by sth on 11/8/15.
 */
public class Item2CatalogItemTranslator {

    /**
     * Transfer minibo item into shopping3000CatalogItem
     * @param item
     * @return catalogItem
     */
    private static Shopping3000CatalogItem miniboItem2CatalogItem(Item item){
        Shopping3000CatalogItem catalogItem = new Shopping3000CatalogItem();
        catalogItem.setId(new Pair<Manufacturer, Integer>(Manufacturer.MINIBO,item.getId()));
        catalogItem.setDescription(item.getDescription());
        catalogItem.setPrice(item.getPrice());
        catalogItem.setName(item.getName());
        return catalogItem;
    }

    public static Processor miniboItem2CatalogItemTranslator = new Processor() {
        public void process(Exchange exchange) throws Exception {
            Item item = exchange.getIn().getBody(Item.class);
            System.out.println(item.toString());
            Shopping3000CatalogItem catalogItem = miniboItem2CatalogItem(item);
            exchange.getIn().setBody(catalogItem);
        }
    };

    /**
     * Transfer maximeuble item into shopping3000CatalogItem
     * @param item
     * @return catalogItem
     */
    private static Shopping3000CatalogItem maximeubleItem2CatalogItem(Product item){
        Shopping3000CatalogItem catalogItem = new Shopping3000CatalogItem();
        catalogItem.setId(new Pair<Manufacturer, Integer>(Manufacturer.MAXIMEUBLE,item.getId()));
        catalogItem.setDescription(item.getDescription());
        catalogItem.setName(item.getName());
        catalogItem.setPrice(item.getPrice());
        return catalogItem;
    }

    public static Processor malleableItem2CatalogItemTranslator = new Processor() {
        public void process(Exchange exchange) throws Exception {
            Product item = exchange.getIn().getBody(Product.class);
            System.out.println(item.toString());
            Shopping3000CatalogItem catalogItem = maximeubleItem2CatalogItem(item);
            exchange.getIn().setBody(catalogItem);
        }
    };
}
