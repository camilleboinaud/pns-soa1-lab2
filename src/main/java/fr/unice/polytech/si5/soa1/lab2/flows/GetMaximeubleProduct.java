package fr.unice.polytech.si5.soa1.lab2.flows;

import static fr.unice.polytech.si5.soa1.lab2.flows.utils.Endpoints.*;

import fr.unice.polytech.si5.soa1.lab2.flows.business.maximeuble.Product;
import fr.unice.polytech.si5.soa1.lab2.flows.business.maximeuble.ProductType;
import fr.unice.polytech.si5.soa1.lab2.flows.utils.MaximeubleRequestBuilder;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

/**
 * Created by lecpie on 10/26/15.
 */
public class GetMaximeubleProduct extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from(GET_MAXIMEUBLE_PRODUCT)
                .log("building product request for id : ${body.left.manufacturerId}")
                .bean(MaximeubleRequestBuilder.class, "buildProductRequest(${body.left.manufacturerId})")
                .to(MAXIMEUBLE_CATALOG_SERVICE)
                //.log("supposedly xml output : ${body}")
                .process(res2product)
                .log("got product : ${body}")
                ;
    }


    private static Processor res2product = new Processor() {
        private XPath xpath = XPathFactory.newInstance().newXPath(); // feature:install camel-saxon

        public void process(Exchange exchange) throws Exception {
            Node response = exchange.getIn().getBody(DOMSource.class).getNode();

            Product output =  builder(response);

            exchange.getIn().setBody(output);
        }

        private Product builder (Node response) throws Exception{
            Product p = new Product();

            p.setName(xpath.evaluate("//name/text()", response));
            p.setCollection(xpath.evaluate("//collection/text()", response));
            p.setId(Integer.parseInt(xpath.evaluate("//id/text()", response)));
            p.setDescription(xpath.evaluate("//description/text()", response));
            p.setImageLink(xpath.evaluate("//image_link/text()", response));
            p.setPrice(Double.parseDouble(xpath.evaluate("//price/text()", response)));
            p.setProductType(ProductType.valueOf(xpath.evaluate("//product_type/text()", response)));

            return p;
        }
    };
}
