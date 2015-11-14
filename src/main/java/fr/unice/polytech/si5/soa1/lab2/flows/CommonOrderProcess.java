package fr.unice.polytech.si5.soa1.lab2.flows;

import static fr.unice.polytech.si5.soa1.lab2.flows.utils.Endpoints.*;

import fr.unice.polytech.si5.soa1.lab2.flows.processors.common.FilterOrderByManufacturer;
import fr.unice.polytech.si5.soa1.lab2.flows.processors.common.IDEnhancementProcessor;
import fr.unice.polytech.si5.soa1.lab2.flows.processors.common.MailRequestProcessor;
import fr.unice.polytech.si5.soa1.lab2.flows.processors.common.OrderGroupperProcessor;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.processor.aggregate.GroupedExchangeAggregationStrategy;

import static fr.unice.polytech.si5.soa1.lab2.flows.utils.Endpoints.*;

/**
 * Created by camille on 08/11/15.
 */
public class CommonOrderProcess extends RouteBuilder {

    private static Processor subOrderProc = new FilterOrderByManufacturer();
    private static Processor addManufacturerMeta = new IDEnhancementProcessor();
    private static Processor orderGroupper = new OrderGroupperProcessor();
    private static Processor mailrqstproc = new MailRequestProcessor();

    @Override
    public void configure() throws Exception {

        /**
         * This floaw allows to filter and process all orders
         * using each manufacturer's specific process.
         */
        from(HANDLE_FULL_ORDER)
                .log("Full order route input : ${body}")
                .setProperty("shop3000_order_id", simple("${body.id}"))
                .log("Associated shopping3000 order id : ${headers.shop3000_order_id}")
                .log("partitionning order by manufacturer")
                .process(subOrderProc)
                .log("suborders created : ${body}")
                .split(body(), new GroupedExchangeAggregationStrategy())
                .log("manufacturer : ${body.items[0].left.manufacturer}")
                .choice()
                    .when(simple("${body.items[0].left.manufacturer} == 'MINIBO'"))
                        .log("Routing to minibo order handler")
                        .to(HANDLE_MINIBO_ORDER)
                        .log("minibo choice output : ${body}")
                        .setHeader("manufacturer", constant("MINIBO"))
                        .endChoice()
                    .when(simple("${body.items[0].left.manufacturer} == 'MAXIMEUBLE'"))
                        .log("Routing to maximeuble order handler")
                        .to(HANDLE_MAXIMEUBLE_ORDER)
                        .log("maximeuble choice output : ${body}")
                        .setHeader("manufacturer", constant("MAXIMEUBLE"))
                        .endChoice()
                    .otherwise()
                        .log("Could not map order to known manufacturer")
                        .stop()
                        .endChoice()
                .end()
                .log("adding metadata to raw id")
                .process(addManufacturerMeta)
                .end()
                .process(orderGroupper)
                .log("split end output : ${body}")
                .setBody(property("shop3000_order_id"))
                .log("shop 3000 order id : ${body}")
                .to(SEND_CONFIRMATION_EMAIL)
                .log("all suborder processed")
                .to(PAY_ORDER_TO_MANUFACTURER)
        ;

        from(PAY_ORDER_TO_MANUFACTURER)
                .split(body())
                    .choice()
                        .when(simple("${body.left} == 'MINIBO'"))
                            .to(MINIBO_ORDER_PAYMENT)
                            .endChoice()
                        .when(simple("${body.left} == 'MAXIMEUBLE'"))
                            .to(MAXIMEUBLE_ORDER_PAYMENT)
                            .endChoice()
                        .otherwise()
                            .log("Could not map order to known manufacturer")
                            .stop()
                            .endChoice()
                        .end()
                .end()
                .log("payment to manufacturer : ${body} achieved")
        ;

        from(SEND_CONFIRMATION_EMAIL)
                .setProperty("shop3000_order_id", body())
                .to("direct:get_order")
                .setProperty("email", simple("${body.customer.email}"))
                .setBody(simple("${exchangeProperty.shop3000_order_id}"))
                .to("direct:get_amount")
                .setProperty("price", body())
                .setBody(simple("${headers.shop3000_order_id}"))
                .setBody(simple("Your order has been processed, your id is ${exchangeProperty.shop3000_order_id} and the amount is ${exchangeProperty.price} Euros. Thank you for your trust !"))
                .log("sending mail to ${exchangeProperty.email} with content : ${body}")
                .process(mailrqstproc)
                .to("activemq:sendEmail")
        ;

    }

}
