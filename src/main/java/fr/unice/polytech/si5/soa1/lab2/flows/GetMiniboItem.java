package fr.unice.polytech.si5.soa1.lab2.flows;

import fr.unice.polytech.si5.soa1.lab2.flows.business.maximeuble.ProductType;
import fr.unice.polytech.si5.soa1.lab2.flows.business.minibo.Item;
import fr.unice.polytech.si5.soa1.lab2.flows.utils.RequestBuilder;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.w3c.dom.Node;

import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import static fr.unice.polytech.si5.soa1.lab2.flows.utils.Endpoints.HANDLE_MINIBO_CATALOG_GET_ITEM;
import static fr.unice.polytech.si5.soa1.lab2.flows.utils.Endpoints.MINIBO_CATALOG_SERVICE;

/**
 * Created by sth on 11/4/15.
 */
public class GetMiniboItem extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from(HANDLE_MINIBO_CATALOG_GET_ITEM)
                .log("get minibo item")
                .bean(RequestBuilder.class, "buildCatalogGetItemRequest(${body.left},${body.right})")
                .to(MINIBO_CATALOG_SERVICE)
                .log("result get item from minibo")
                .process(result2item)
        ;
    }

    private static Processor result2item = new Processor() {

        private XPath xpath = XPathFactory.newInstance().newXPath();    // feature:install camel-saxon

        public void process(Exchange exchange) throws Exception {
            Node response = exchange.getIn().getBody(DOMSource.class).getNode();
            Item output =  builder(response);
            exchange.getIn().setBody(output);
        }

        private Item builder (Node response) throws Exception{
            Item item = new Item();

            item.setName(xpath.evaluate("//name/text()", response));
            item.setDescription(xpath.evaluate("//description/text()", response));
            item.setPrice(Double.parseDouble(xpath.evaluate("//price/text()", response)));
            item.setId(Integer.parseInt(xpath.evaluate("//id/text()", response)));

            return item;
        }
    };
}
