package fr.unice.polytech.si5.soa1.lab2.flows;

import static fr.unice.polytech.si5.soa1.lab2.flows.utils.Endpoints.*;

import fr.unice.polytech.si5.soa1.lab2.flows.business.Address;
import fr.unice.polytech.si5.soa1.lab2.flows.processors.FilterOrderByManufacturer;
import fr.unice.polytech.si5.soa1.lab2.flows.utils.aggregators.ListAggregationStrategy;
import org.apache.camel.builder.RouteBuilder;

public class HandleFullOrder extends RouteBuilder {

    private static FilterOrderByManufacturer subOrderProc = new FilterOrderByManufacturer();

    @Override
    public void configure() throws Exception {

        from(HANDLE_FULL_ORDER)
                .log("partitionning order by manufacturer")
                .process(subOrderProc)
                .log("suborders created : ${body}")
                .setHeader("customer_address", simple("${body[0].customer.address}", Address.class))
                .setHeader("nmanufactuer", simple("${body.size()}"))
                .split(body())
                .choice()
                .when(simple("${body.items[0].left.manufacturer} == 'MINIBO'"))
                .log("Routing to minibo order handler")
                .to(HANDLE_MINIBO_ORDER)
                .when(simple("${body.items[0].left.manufacturer} == 'MAXIMEUBLE'"))
                .log("Routing to maximeuble order handler")
                .to(HANDLE_MAXIMEUBLE_ORDER)
                .otherwise()
                        .log("Could not map order to known manufacturer")
                        .stop()
                .end()
                .aggregate(new ListAggregationStrategy())
                .header("customer_address")
                .completionSize(header("nmanufactuer"))
                .completionTimeout(3000L)
                .log("all suborder processed")

        ;
    }

}
