package fr.unice.polytech.si5.soa1.lab2.flows;

import static fr.unice.polytech.si5.soa1.lab2.flows.utils.Endpoints.*;

import fr.unice.polytech.si5.soa1.lab2.flows.business.Order;
import fr.unice.polytech.si5.soa1.lab2.flows.business.OrderItem;
import fr.unice.polytech.si5.soa1.lab2.flows.processors.SubOrders;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.processor.aggregate.AggregationStrategy;

import java.util.ArrayList;
import java.util.List;

public class HandleFullOrder extends RouteBuilder {

    private static SubOrders subOrderProc = new SubOrders();

    @Override
    public void configure() throws Exception {

        from(HANDLE_FULL_ORDER)
                .log("partitionning order by manufacturer")
                .process(subOrderProc)
                .log("suborders created : ${body}")
                .split(body())
                .choice()
                    .when(simple("${body.items[0].manufacturer} == 'MINIBO'"))
                        .log("Routing to minibo order handler")
                        .to(HANDLE_MINIBO_ORDER)
                    .when(simple("${body.items[0].manufacturer} == 'MAXIMEUBLE'"))
                        .log("Routing to maximeuble order handler")
                        .to(HANDLE_MAXIMEUBLE_ORDER)
                    .otherwise()
                        .log("Could not map order to known manufacturer")
                        .stop()
                .end()
        //TODO Aggregate back in a single mixed order and give it an id
        .log("all suborder processed")

        ;
    }

}
