package fr.unice.polytech.si5.soa1.lab2.flows;

import static fr.unice.polytech.si5.soa1.lab2.flows.utils.Endpoints.*;

import fr.unice.polytech.si5.soa1.lab2.flows.business.maximeuble.Client;
import fr.unice.polytech.si5.soa1.lab2.flows.business.maximeuble.MakeOrderParams;
import fr.unice.polytech.si5.soa1.lab2.flows.business.maximeuble.OrderRequest;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.processor.aggregate.AggregationStrategy;


/**
 * Created by lecpie on 10/24/15.
 */
public class HandleMaxiMeubleOrder extends RouteBuilder {

    private static AggregationStrategy joinParams = new AggregationStrategy() {
        public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
            Exchange exc;
            MakeOrderParams params;

            if (oldExchange == null) {
                params = new MakeOrderParams();
                exc = newExchange;
            }
            else if (newExchange == null) {
                return newExchange;
            }
            else {
                params = oldExchange.getIn().getBody(MakeOrderParams.class);
                exc = oldExchange;
            }

            try {
                params.setClient(exc.getIn().getBody(Client.class));
            }
            catch (Exception e) {
                try {
                    params.setOrderRequest(exc.getIn().getBody(OrderRequest.class));
                }
                catch (Exception e2) {}
            }

            return exc;
        }
    };

    @Override
    public void configure() throws Exception {
        from(HANDLE_MAXIMEUBLE_ORDER)
                .log("maximeuble order handler...")
                .setHeader("input_order", body())
                .multicast()
                    .aggregationStrategy(joinParams)
                    .parallelProcessing()
                    .to(MAKE_MAXIMEUBLE_ORDERREQUEST)
                    .to(MAKE_MAXIMEUBLE_CLIENT)
                .end()
                .log("multicast output : ${body}")
        ;
    }

}
