package fr.unice.polytech.si5.soa1.lab2.flows;

import static fr.unice.polytech.si5.soa1.lab2.flows.utils.Endpoints.*;

import fr.unice.polytech.si5.soa1.lab2.flows.business.Address;
import fr.unice.polytech.si5.soa1.lab2.flows.business.Order;
import fr.unice.polytech.si5.soa1.lab2.flows.business.maximeuble.Client;
import fr.unice.polytech.si5.soa1.lab2.flows.business.maximeuble.OrderRequest;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

/**
 * Created by lecpie on 10/26/15.
 */
public class MakeMaximeubleClient extends RouteBuilder {

    Processor data2client = new Processor() {
        public void process(Exchange exchange) throws Exception {
            // retrieving the body of the exchanged message
            Order input = (Order) exchange.getIn().getBody();
            // transforming the input
            Client output =  builder(input);
            // Putting the output inside the body of the message
            exchange.getIn().setBody(output);
        }

        private Client builder (Order genericOrder) {
            Client client = new Client();

            client.setName(genericOrder.getCustomer().getName());
            client.setEmail(genericOrder.getCustomer().getEmail());

            Address addr = genericOrder.getCustomer().getAddress();
            client.setAddress(addr.getAddress() + " - " + addr.getZipcode() + " - " + addr.getCity());

            return client;
        }
    };

    @Override
    public void configure() throws Exception {
        from (MAKE_MAXIMEUBLE_CLIENT)
                .process(data2client)
                .log("route output : ${body}")
                ;
    }
}
