package fr.unice.polytech.si5.soa1.lab2.flows.processors.maximeuble;

import fr.unice.polytech.si5.soa1.lab2.flows.business.maximeuble.Product;
import fr.unice.polytech.si5.soa1.lab2.flows.business.maximeuble.ProductType;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.w3c.dom.Node;

import javax.xml.transform.dom.DOMSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

/**
 * Created by camille on 08/11/15.
 */
public class ProductTranslationProcessor implements Processor {

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

}
