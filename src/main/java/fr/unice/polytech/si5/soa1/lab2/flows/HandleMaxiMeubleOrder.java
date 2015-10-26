package fr.unice.polytech.si5.soa1.lab2.flows;

import static fr.unice.polytech.si5.soa1.lab2.flows.utils.Endpoints.*;

import fr.unice.polytech.si5.soa1.lab2.flows.business.Manufacturer;
import fr.unice.polytech.si5.soa1.lab2.flows.business.Order;
import fr.unice.polytech.si5.soa1.lab2.flows.utils.Pair;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

import javax.swing.*;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by lecpie on 10/24/15.
 */
public class HandleMaxiMeubleOrder extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from(HANDLE_MAXIMEUBLE_ORDER)
                .log("maximeuble order handler...")
                .setHeader("input_order", body())
                .multicast()
                    .parallelProcessing()
                    .to(MAKE_MAXIMEUBLE_ORDERREQUEST)
                .end()
        ;
    }

}
