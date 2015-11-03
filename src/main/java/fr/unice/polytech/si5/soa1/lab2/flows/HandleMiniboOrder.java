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

public class HandleMiniboOrder extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from(HANDLE_MINIBO_ORDER)
                .log("minibo order handler...")
                .setProperty("order", body())
                .to(START_MINIBO_ORDER)
                .setProperty("order_id", body())
                        // mock minibo order result
                .setBody(property("order_id"))
                .log("minibo order made with id : ${body}")
        ;

        from(START_MINIBO_ORDER)
                .bean(RequestBuilder.class, "buildStartMiniboOrder()")
                .to(MINIBO_ORDER_SERVICE)
                .process(res2id)
                .log("id: ${body}")
        ;

    }

    private static Processor res2id = new Processor() {
        private XPath xpath = XPathFactory.newInstance().newXPath(); // feature:install camel-saxon

        public void process(Exchange exchange) throws Exception {
            Node source = exchange.getIn().getBody(DOMSource.class).getNode();

            int id = Integer.parseInt(xpath.evaluate("//id/text()", source));

            exchange.getIn().setBody(id);
        }
    };
}
