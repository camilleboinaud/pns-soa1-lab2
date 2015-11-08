package fr.unice.polytech.si5.soa1.lab2.flows.processors.maximeuble;

import fr.unice.polytech.si5.soa1.lab2.flows.business.Address;
import fr.unice.polytech.si5.soa1.lab2.flows.business.Order;
import fr.unice.polytech.si5.soa1.lab2.flows.business.maximeuble.Client;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 * Created by camille on 08/11/15.
 */
public class CustomerBuildingProcessor implements Processor {

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

}
