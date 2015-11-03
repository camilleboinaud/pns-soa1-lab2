package fr.unice.polytech.si5.soa1.lab2.flows;

import static fr.unice.polytech.si5.soa1.lab2.flows.utils.Endpoints.*;

import fr.unice.polytech.si5.soa1.lab2.flows.utils.RequestBuilder;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.w3c.dom.Node;

import javax.xml.transform.dom.DOMSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

/*
/**
 * Created by lecpie on 10/30/15.
 */
public class MakeMaximeubleOrder extends RouteBuilder {

    Processor res2id = new Processor() {
        private XPath xpath = XPathFactory.newInstance().newXPath(); // feature:install camel-saxon

        public void process(Exchange exchange) throws Exception {
            Node source = exchange.getIn().getBody(DOMSource.class).getNode();

            int orderId = Integer.parseInt(xpath.evaluate("//id/text()", source));

            exchange.getIn().setBody(orderId);
        }
    };

    @Override
    public void configure() throws Exception {
        from (MAKE_MAXIMEUBLE_ORDER)
                .log("make order : ${body}")
                .bean(RequestBuilder.class, "buildMaximeubleMakeOrderRequest(${body})")
                .log("request : ${body}")
                .to(MAXIMEUBLE_ORDER_SERVICE)
                .log("order service result : ${body}")
                .process(res2id)
                ;
    }
}
