package fr.unice.polytech.si5.soa1.lab2.flows.processors.minibo;

import fr.unice.polytech.si5.soa1.lab2.flows.business.minibo.Item;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.w3c.dom.Node;

import javax.xml.transform.dom.DOMSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

/**
 * Created by camille on 08/11/15.
 */
public class ItemTranslationProcessor implements Processor{

    private XPath xpath = XPathFactory.newInstance().newXPath();

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

}
