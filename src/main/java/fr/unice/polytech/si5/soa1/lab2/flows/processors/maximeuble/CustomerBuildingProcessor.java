package fr.unice.polytech.si5.soa1.lab2.flows.processors.maximeuble;

import fr.unice.polytech.si5.soa1.lab2.flows.business.Address;
import fr.unice.polytech.si5.soa1.lab2.flows.business.Order;
import fr.unice.polytech.si5.soa1.lab2.flows.business.maximeuble.Client;
import fr.unice.polytech.si5.soa1.lab2.flows.business.shopping3000.Shopping3000Resources;
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
        Client output =  builder();
        // Putting the output inside the body of the message
        exchange.getIn().setBody(output);
    }

    private Client builder () {
        Client client = new Client();

        client.setName(Shopping3000Resources.shopping3000Data.getName());
        client.setEmail(Shopping3000Resources.shopping3000Data.getEmail());

        Address addr = Shopping3000Resources.shopping3000Data.getAddress();

        client.setAddress(addr.getAddress() + " - " + addr.getZipcode() + " - " + addr.getCity());

        return client;
    }

}
