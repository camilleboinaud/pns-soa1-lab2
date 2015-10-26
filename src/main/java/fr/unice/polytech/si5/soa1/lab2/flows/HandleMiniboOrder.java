package fr.unice.polytech.si5.soa1.lab2.flows;

import static fr.unice.polytech.si5.soa1.lab2.flows.utils.Endpoints.*;

import fr.unice.polytech.si5.soa1.lab2.flows.business.Manufacturer;
import fr.unice.polytech.si5.soa1.lab2.flows.business.Order;
import fr.unice.polytech.si5.soa1.lab2.flows.utils.Pair;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

import java.util.Random;

public class HandleMiniboOrder extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from(HANDLE_MINIBO_ORDER)
                .log("minibo order handler...")
        ;
    }
}
