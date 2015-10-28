package fr.unice.polytech.si5.soa1.lab2.flows;

import static fr.unice.polytech.si5.soa1.lab2.flows.utils.Endpoints.*;

import fr.unice.polytech.si5.soa1.lab2.flows.business.maximeuble.Client;
import fr.unice.polytech.si5.soa1.lab2.flows.business.maximeuble.MakeOrderParams;
import fr.unice.polytech.si5.soa1.lab2.flows.business.maximeuble.OrderRequest;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.apache.camel.processor.aggregate.GroupedExchangeAggregationStrategy;

import java.util.List;


/**
 * Created by lecpie on 10/24/15.
 */
public class HandleMaxiMeubleOrder extends RouteBuilder {

    Processor joinParams = new Processor () {

        public void process(Exchange exchange) throws Exception {
            List<Exchange> exchanges = (List<Exchange>) exchange.getIn().getBody();

            MakeOrderParams params = builder(exchanges);

            exchange.getIn().setBody(params);
        }

        private MakeOrderParams builder(List<Exchange> exchanges) {
            MakeOrderParams params = new MakeOrderParams();

            for (Exchange exc : exchanges) {
                Object o = exc.getIn().getBody();
                System.out.println(o);
                if (o instanceof OrderRequest) {
                    params.setOrderRequest(exc.getIn().getBody(OrderRequest.class));
                }
                else if (o instanceof Client) {
                    params.setClient(exc.getIn().getBody(Client.class));
                }
            }

            return params;
        }
    };

    @Override
    public void configure() throws Exception {
        from(HANDLE_MAXIMEUBLE_ORDER)
                .log("maximeuble order handler...")
                .setHeader("input_order", body())
                .multicast(new GroupedExchangeAggregationStrategy())
                    .parallelProcessing()
                    .to(MAKE_MAXIMEUBLE_CLIENT)
                    .to(MAKE_MAXIMEUBLE_ORDERREQUEST)
                .end()
                .process(joinParams)
                .log("multicast output : ${body}")
        ;
    }

}
