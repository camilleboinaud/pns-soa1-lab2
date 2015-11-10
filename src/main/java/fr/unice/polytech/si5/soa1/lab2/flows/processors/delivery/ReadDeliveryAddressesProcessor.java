package fr.unice.polytech.si5.soa1.lab2.flows.processors.delivery;

import fr.unice.polytech.si5.soa1.lab2.flows.utils.Pair;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

/**
 * Created by sth on 11/9/15.
 */
public class ReadDeliveryAddressesProcessor implements Processor {

    private XPath xpath = XPathFactory.newInstance().newXPath();

    public void process(Exchange exchange) throws Exception {
        Pair response = (Pair) exchange.getIn().getBody();
        Pair<String,String> addresses = new Pair<String,String>();
        String fromAddress = xpath.evaluate("//left/text()", response.getLeft());
        String toAddress = xpath.evaluate("//right/text()", response.getRight());
        addresses.setLeft(fromAddress);
        addresses.setRight(toAddress);
        exchange.getIn().setBody(addresses);
    }
}
