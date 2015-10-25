package fr.unice.polytech.si5.soa1.lab2.flowcatalog;

import fr.unice.polytech.si5.soa1.lab2.flowcatalog.business.Item;
import fr.unice.polytech.si5.soa1.lab2.flowcatalog.utils.RequestBuilder;
import fr.unice.polytech.si5.soa1.lab2.flows.business.Manufacturer;
import fr.unice.polytech.si5.soa1.lab2.flows.utils.Pair;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

import javax.xml.transform.Source;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

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
                .process(result2listItem)
        ;
    }

    private static Processor result2listItem = new Processor() {

        private XPath xpath = XPathFactory.newInstance().newXPath();    // feature:install camel-saxon

        public void process(Exchange exchange) throws Exception {
            Source response = (Source) exchange.getIn().getBody();
            Item result = new Item();
            result.setId(new Pair<Manufacturer, Integer>(Manufacturer.MINIBO,Integer.parseInt(xpath.evaluate("//id/text()", response))));
            result.setName(xpath.evaluate("//name/text()", response));
            result.setDescription(xpath.evaluate("//description/text()", response));
            result.setPrice(Double.parseDouble(xpath.evaluate("//price/text()",response)));
            exchange.getIn().setBody(result);
        }
    };
}
