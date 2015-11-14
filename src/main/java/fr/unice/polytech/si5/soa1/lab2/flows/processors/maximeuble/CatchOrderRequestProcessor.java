package fr.unice.polytech.si5.soa1.lab2.flows.processors.maximeuble;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.StringWriter;

/**
 * Created by camille on 14/11/15.
 */
public class CatchOrderRequestProcessor implements Processor {

    private XPath xpath = XPathFactory.newInstance().newXPath(); // feature:install camel-saxon

    public void process(Exchange exchange) throws Exception {
        Node source = exchange.getIn().getBody(DOMSource.class).getNode();

        Node node = (Node) xpath.evaluate("//order_request", source, XPathConstants.NODE);

        StringWriter writer = new StringWriter();
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        transformer.transform(new DOMSource(node), new StreamResult(writer));
        String xml = writer.toString();

        exchange.getIn().setBody(xml);
    }

}
