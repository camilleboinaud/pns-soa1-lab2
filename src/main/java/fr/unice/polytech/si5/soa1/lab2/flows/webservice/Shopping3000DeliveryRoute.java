package fr.unice.polytech.si5.soa1.lab2.flows.webservice;

import fr.unice.polytech.si5.soa1.lab2.flows.processors.delivery.ReadDeliveryAddressesProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;

/**
 * Created by sth on 11/9/15.
 */
public class Shopping3000DeliveryRoute extends RouteBuilder {

    static ReadDeliveryAddressesProcessor readDeliveryAddressesProcessor = new ReadDeliveryAddressesProcessor();

    @Override
    public void configure() throws Exception {


        from("direct:badDeliveryRequest")
                .log("This is a bad request!")
        ;

        from("direct:evaluateDeliveryPrice")
            .process(evaluateDeliveryPrice)
        ;

        from("cxf:/Shopping3000DeliveryService?serviceClass=fr.unice.polytech.si5.soa1.lab2.flows.webservice.Shopping3000DeliveryService")
                .choice()
                .when(simple("${in.headers.operationName} == 'CalculateDeliveryPriceByAddresses'"))
                    .setBody(simple("${body[0]}"))
                .log("calculate delivery price: ${body.left}  ${body.right}")
                .process(readDeliveryAddressesProcessor)
                .to("direct:evaluateDeliveryPrice")
                .otherwise()
                    .to("direct:badDeliveryRequest").stop()
                .endChoice()
        ;

    }

    public static Processor evaluateDeliveryPrice = new Processor() {
        public void process(Exchange exchange) throws Exception {
            Double price = 13.0;
            exchange.getIn().setBody(price);
        }
    };
}
