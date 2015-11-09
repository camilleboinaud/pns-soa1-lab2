package fr.unice.polytech.si5.soa1.lab2.flows.processors.catalogue;


import fr.unice.polytech.si5.soa1.lab2.flows.business.shopping3000.Manufacturer;
import fr.unice.polytech.si5.soa1.lab2.flows.utils.Pair;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import javax.xml.transform.Source;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

/**
 * Created by sth on 11/9/15.
 */
public class ReadPairIdProcessor implements Processor {
    private XPath xpath = XPathFactory.newInstance().newXPath();

    public void process(Exchange exchange) throws Exception {
        Pair response = (Pair) exchange.getIn().getBody();
        Pair<Manufacturer,Integer> pairId = new Pair<Manufacturer, Integer>();
        Manufacturer manufacturer = Manufacturer.valueOf(xpath.evaluate("//left/text()",response.getLeft()));
        Integer id = Integer.valueOf(xpath.evaluate("//right/text()", response.getRight()));
        pairId.setLeft(manufacturer);
        pairId.setRight(id);
        exchange.getIn().setBody(pairId);
    }
}
