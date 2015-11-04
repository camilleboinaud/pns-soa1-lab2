package fr.unice.polytech.si5.soa1.lab2.flows;

import static fr.unice.polytech.si5.soa1.lab2.flows.utils.Endpoints.*;

import fr.unice.polytech.si5.soa1.lab2.flows.business.shopping3000.Manufacturer;
import fr.unice.polytech.si5.soa1.lab2.flows.business.shopping3000.Shopping3000ID;
import fr.unice.polytech.si5.soa1.lab2.flows.processors.FilterOrderByManufacturer;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsMessage;
import org.apache.camel.processor.aggregate.GroupedExchangeAggregationStrategy;

import java.util.ArrayList;
import java.util.List;

public class HandleFullOrder extends RouteBuilder {

    private static FilterOrderByManufacturer subOrderProc = new FilterOrderByManufacturer();

    @Override
    public void configure() throws Exception {

        from(HANDLE_FULL_ORDER)
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
                .log("all suborder processed")
        ;
    }

    private static Processor addManufacturerMeta = new Processor() {
        public void process(Exchange exchange) throws Exception {
            Integer id = exchange.getIn().getBody(Integer.class);

            Manufacturer manufacturer = Manufacturer.valueOf(exchange.getIn().getHeader("manufacturer", String.class));

            Shopping3000ID shopping3000ID = new Shopping3000ID();
            shopping3000ID.setId(id);
            shopping3000ID.setManufacturer(manufacturer);

            exchange.getIn().setBody(shopping3000ID);
        }
    };

    private static Processor orderGroupper = new Processor() {
        public void process(Exchange exchange) throws Exception {
            List<Exchange> in = exchange.getIn(JmsMessage.class).getBody(List.class);
            List<Shopping3000ID> idList = new ArrayList<Shopping3000ID>();

            for (Exchange e : in) {
                idList.add(e.getIn().getBody(Shopping3000ID.class));
            }

            exchange.getIn().setBody(idList);
        }

    };

}
