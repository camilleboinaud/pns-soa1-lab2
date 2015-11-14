package fr.unice.polytech.si5.soa1.lab2.flows;

import org.apache.camel.builder.RouteBuilder;
import static fr.unice.polytech.si5.soa1.lab2.flows.utils.Endpoints.*;

/**
 * Created by camille on 14/11/15.
 */
public class Shopping3000PaymentProcess extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from(SHOPPING_3000_PAYMENT)
                .log("################ processing full payment : ${body}")
                .to(HANDLE_FULL_ORDER);

    }

}
