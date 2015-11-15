package fr.unice.polytech.si5.soa1.lab2.flows.processors.common;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.w3c.dom.Node;

import javax.xml.transform.dom.DOMSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

/**
 * Created by camille on 15/11/15.
 */
public class AddDeliveryToOrderAMountProcessor implements Processor {

    private XPath xpath = XPathFactory.newInstance().newXPath(); // feature:install camel-saxon

    public void process(Exchange exchange) throws Exception {

        Node source = exchange.getIn().getBody(DOMSource.class).getNode();

        double delivery = Double.parseDouble(xpath.evaluate("//deliveryPrice/text()", source));
        double amount = exchange.getIn().getHeader("order_amount", Double.class);

        exchange.getIn().setBody(delivery + amount);
    }

}
