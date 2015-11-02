package fr.unice.polytech.si5.soa1.lab2.flows;

import fr.unice.polytech.si5.soa1.lab2.flows.business.CatalogItem;
import fr.unice.polytech.si5.soa1.lab2.flows.utils.RequestBuilder;
import fr.unice.polytech.si5.soa1.lab2.flows.business.Manufacturer;
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
                .bean(RequestBuilder.class, "buildCatalogListRequest()")
                .to(MINIBO_CATALOG_SERVICE)
                .log("result get from minibo")
                .process(result2listItem)
        ;

        from(HANDLE_MAXIMEUBLE_CATALOG_LIST)
                .log("list all catalog in maximeuble")
                .bean(RequestBuilder.class, "buildCatalogListRequest()")
                .to(MINIBO_CATALOG_SERVICE)
                .log("result get from maximeuble")
                .process(result2listItem)
        ;

        from(HANDLE_MINIBO_CATALOG_GET_ITEM)
                .log("get minibo item")
                .bean(RequestBuilder.class, "buildCatalogGetItemRequest(${body.id.left},${body.id.right})")
                .to(MINIBO_CATALOG_SERVICE)
                .log("result get item from minibo")
                .process(result2item)
        ;

        from(HANDLE_MAXIMEUBLE_CATALOG_GET_ITEM)
                .log("get minibo item")
                .bean(RequestBuilder.class, "buildCatalogGetItemRequest(${body.id.left},${body.id.right})")
                .to(MAXIMEUBLE_CATALOG_SERVICE)
                .log("result get item from maximeuble")
                .process(result2item)
        ;

    }

    private static Processor result2listItem = new Processor() {

        private XPath xpath = XPathFactory.newInstance().newXPath();    // feature:install camel-saxon

        public void process(Exchange exchange) throws Exception {
            Source response = (Source) exchange.getIn().getBody();

            List<CatalogItem> listItems = new ArrayList<CatalogItem>();
            NodeList contentsNodeList = (NodeList) xpath.evaluate("//BusinessManagementService_list_contents", response, XPathConstants.NODESET);
            for (int i = 0; i < contentsNodeList.getLength(); i++) {
                Node node = contentsNodeList.item(i);
                CatalogItem item = new CatalogItem();
                Integer id = Integer.parseInt(xpath.evaluate("id/text()", node));
                item.setId(new Pair<Manufacturer, Integer>(Manufacturer.MINIBO,id));
                item.setName(xpath.evaluate("name/text()", node));
                item.setDescription(xpath.evaluate("description/text()", node));
                item.setPrice(Double.parseDouble(xpath.evaluate("price/text()", node)));
                listItems.add(item);
            }

            exchange.getIn().setBody(listItems,ArrayList.class);
            System.out.println("process done");
        }
    };

    private static Processor result2item = new Processor() {

        private XPath xpath = XPathFactory.newInstance().newXPath();    // feature:install camel-saxon

        public void process(Exchange exchange) throws Exception {
            Source response = (Source) exchange.getIn().getBody();
            System.out.println(response);
        }
    };
}
