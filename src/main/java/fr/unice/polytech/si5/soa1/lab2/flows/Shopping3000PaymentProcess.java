package fr.unice.polytech.si5.soa1.lab2.flows;

import fr.unice.polytech.si5.soa1.lab2.flows.request.Shopping3000RequestBuilder;
import org.apache.camel.builder.RouteBuilder;
import static fr.unice.polytech.si5.soa1.lab2.flows.utils.Endpoints.*;

/**
 * Created by camille on 14/11/15.
 */
public class Shopping3000PaymentProcess extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from(SHOPPING_3000_PAYMENT)
                .setProperty("order", simple("${body}"))
                .setProperty("order_id", simple("${body.id}"))
                .setBody(simple("${body.id}"))
                .to(GET_AMOUNT)
                .log("[ORDER n°${exchangeProperty.order_id}] Order amount : ${body}")
                .log("buildPaymentRequest(${exchangeProperty.order.customer.creditcard}, ${body})")
                .bean(Shopping3000RequestBuilder.class, "buildPaymentRequest(${exchangeProperty.order.customer.creditcard}, ${body})")
                .to(SHOPPING_3000_BANK_SERVICE)
                .choice()
                    .when().xpath("//paymentResult = function:simple('true')")
                        .log("[ORDER n°${exchangeProperty.order_id}] PAYMENT SUCCESS")
                        .setBody(simple("${header.order}"))
                        .to(HANDLE_FULL_ORDER)
                    .otherwise()
                        .log("[ORDER n°${exchangeProperty.order_id}] PAYMENT FAILED")
                .end();

    }

}
