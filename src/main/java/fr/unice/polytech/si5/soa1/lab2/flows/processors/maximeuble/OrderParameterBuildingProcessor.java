package fr.unice.polytech.si5.soa1.lab2.flows.processors.maximeuble;

import fr.unice.polytech.si5.soa1.lab2.flows.business.maximeuble.Client;
import fr.unice.polytech.si5.soa1.lab2.flows.business.maximeuble.MakeOrderParams;
import fr.unice.polytech.si5.soa1.lab2.flows.business.maximeuble.OrderRequest;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.List;

/**
 * Created by camille on 08/11/15.
 */
public class OrderParameterBuildingProcessor implements Processor {

    public void process(Exchange exchange) throws Exception {
        List<Exchange> exchanges = (List<Exchange>) exchange.getIn().getBody();

        MakeOrderParams params = builder(exchanges);

        exchange.getIn().setBody(params);
    }

    private MakeOrderParams builder(List<Exchange> exchanges) {
        MakeOrderParams params = new MakeOrderParams();

        for (Exchange exc : exchanges) {
            Object o = exc.getIn().getBody();
            if (o instanceof OrderRequest) {
                params.setOrderRequest(exc.getIn().getBody(OrderRequest.class));
            }
            else if (o instanceof Client) {
                params.setClient(exc.getIn().getBody(Client.class));
            }
        }

        return params;
    }
}
