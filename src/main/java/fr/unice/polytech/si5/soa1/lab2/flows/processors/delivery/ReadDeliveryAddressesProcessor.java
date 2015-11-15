package fr.unice.polytech.si5.soa1.lab2.flows.processors.delivery;

import fr.unice.polytech.si5.soa1.lab2.flows.business.Address;
import fr.unice.polytech.si5.soa1.lab2.flows.utils.Pair;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import javax.mail.internet.AddressException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

/**
 * Created by sth on 11/9/15.
 */
public class ReadDeliveryAddressesProcessor implements Processor {

    private XPath xpath = XPathFactory.newInstance().newXPath();

    public void process(Exchange exchange) throws Exception {

        Pair response = (Pair) exchange.getIn().getBody();

        Pair<Address, Address> addresses = new Pair<Address, Address>();

        addresses.setLeft( new Address(
                xpath.evaluate("//left/address", response.getLeft()),
                xpath.evaluate("//left/zipcode", response.getLeft()),
                xpath.evaluate("//left/city", response.getLeft())
        ));

        addresses.setLeft( new Address(
                xpath.evaluate("//right/address", response.getRight()),
                xpath.evaluate("//right/zipcode", response.getRight()),
                xpath.evaluate("//right/city", response.getRight())
        ));

        exchange.getIn().setBody(addresses);
    }
}
