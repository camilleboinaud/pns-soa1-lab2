package fr.unice.polytech.si5.soa1.lab2.flows;

import static fr.unice.polytech.si5.soa1.lab2.flows.utils.Endpoints.*;
import org.apache.camel.builder.RouteBuilder;


/**
 * Created by lecpie on 10/24/15.
 */
public class HandleMaxiMeubleOrder extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from(HANDLE_MAXIMEUBLE_ORDER)
                .log("maximeuble order handler...")
        ;
    }

}
