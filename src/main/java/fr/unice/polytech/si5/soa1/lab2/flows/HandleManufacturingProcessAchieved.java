package fr.unice.polytech.si5.soa1.lab2.flows;

import static fr.unice.polytech.si5.soa1.lab2.flows.utils.Endpoints.*;
import fr.unice.polytech.si5.soa1.lab2.flows.utils.aggregators.*;
import org.apache.camel.builder.RouteBuilder;

/**
 * Created by camille on 25/10/15.
 */
public class HandleManufacturingProcessAchieved extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from(HANDLE_MANUFACTURING_PROCESS_ACHIEVED)
                .log("Start agregation")
                .aggregate(header("customer_address"), new ListAggregationStrategy())
                .completionTimeout(3000)
                .log("Aggregation achieved : ${header.customer_address}");
    }

}
