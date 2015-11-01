package fr.unice.polytech.si5.soa1.lab2.flows;

import static fr.unice.polytech.si5.soa1.lab2.flows.utils.Endpoints.*;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
/*
/**
 * Created by lecpie on 10/30/15.
 */
public class MakeMaximeubleOrder extends RouteBuilder {

    Processor res2id = new Processor() {
        public void process(Exchange exchange) throws Exception {
            // TODO handle real data
            exchange.getIn().setBody(4);
        }
    };

    @Override
    public void configure() throws Exception {
        from (MAKE_MAXIMEUBLE_ORDER)
                //TODO call webservice
                .process(res2id)
                ;
    }
}
