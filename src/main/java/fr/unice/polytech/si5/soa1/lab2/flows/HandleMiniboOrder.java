package fr.unice.polytech.si5.soa1.lab2.flows;

import static fr.unice.polytech.si5.soa1.lab2.flows.utils.Endpoints.*;

import org.apache.camel.builder.RouteBuilder;

public class HandleMiniboOrder extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from(HANDLE_MINIBO_ORDER)
                .log("minibo order handler...")
        ;
    }
}
