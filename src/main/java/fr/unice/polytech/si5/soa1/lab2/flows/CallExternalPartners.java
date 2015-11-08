package fr.unice.polytech.si5.soa1.lab2.flows;

import fr.unice.polytech.si5.soa1.lab2.flows.business.OrderItem;
import fr.unice.polytech.si5.soa1.lab2.flows.business.shopping3000.Shopping3000CatalogItem;
import fr.unice.polytech.si5.soa1.lab2.flows.business.shopping3000.Shopping3000ID;
import fr.unice.polytech.si5.soa1.lab2.flows.processors.translator.ItemList2CatalogItemListTranslator;
import fr.unice.polytech.si5.soa1.lab2.flows.utils.RequestBuilder;
import fr.unice.polytech.si5.soa1.lab2.flows.business.shopping3000.Manufacturer;
import fr.unice.polytech.si5.soa1.lab2.flows.utils.Pair;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.w3c.dom.NodeList;

import org.w3c.dom.Node;
import javax.xml.transform.Source;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import java.util.ArrayList;
import java.util.List;

import static fr.unice.polytech.si5.soa1.lab2.flows.utils.Endpoints.*;

/**
 * Created by Tianhao on 10/25/2015.
 */
public class CallExternalPartners extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from(HANDLE_MINIBO_CATALOG_LIST)
                .log("list all catalog in minibo")
                .setHeader("manufacturer", constant("MINIBO"))
                .bean(RequestBuilder.class, "buildCatalogListRequest(${header.manufacturer})")
                .to(MINIBO_CATALOG_SERVICE)
                .log("result get from minibo")
                .process(ItemList2CatalogItemListTranslator.miniboItemList2CatalogListItem)
        ;

        from(HANDLE_MAXIMEUBLE_CATALOG_LIST)
                .log("list all catalog in maximeuble")
                .setHeader("manufacturer", constant("MAXIMEUBLE"))
                .bean(RequestBuilder.class, "buildCatalogListRequest(${header.manufacturer})")
                .to(MAXIMEUBLE_CATALOG_SERVICE)
                .log("result get from maximeuble")
                .process(ItemList2CatalogItemListTranslator.maximeubleItemList2CatalogListItem)
        ;

        from(HANDLE_MAXIMEUBLE_CATALOG_GET_ITEM)
                .log("start getting maximeuble item")
                .process(createOrderItemByshopping3000ID)
                .to(GET_MAXIMEUBLE_PRODUCT)
        ;

    }

    /**
     * create a orderItem by shopping 3000ID in order to get maximeuble product
     */
    private static Processor createOrderItemByshopping3000ID = new Processor() {

        public void process(Exchange exchange) throws Exception {
            Shopping3000ID id = exchange.getIn().getBody(Shopping3000ID.class);
            System.out.println(id.toString());
            OrderItem orderItem = new OrderItem(Manufacturer.MAXIMEUBLE,id.getId());
            exchange.getIn().setBody(orderItem);
        }
    };


}
