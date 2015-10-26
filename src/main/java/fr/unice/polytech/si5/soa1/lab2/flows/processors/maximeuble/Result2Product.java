package fr.unice.polytech.si5.soa1.lab2.flows.processors.maximeuble;

import fr.unice.polytech.si5.soa1.lab2.flows.business.Order;
import fr.unice.polytech.si5.soa1.lab2.flows.business.maximeuble.OrderRequest;
import fr.unice.polytech.si5.soa1.lab2.flows.business.maximeuble.Product;
import fr.unice.polytech.si5.soa1.lab2.flows.business.maximeuble.ProductType;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 * Created by lecpie on 10/26/15.
 */
public class Result2Product implements Processor {

    public void process(Exchange exchange) throws Exception {

        /*
        // retrieving the body of the exchanged message
        Order input = (Order) exchange.getIn().getBody();
        // transforming the input
        */

        Product output =  builder(null);
        // Putting the output inside the body of the message
        exchange.getIn().setBody(output);
    }

    private Product builder (Object mockedParam) {
        Product p = new Product();

        p.setId(0);
        p.setCollection("collection");
        p.setDescription("descr");
        p.setImageLink("imglink");
        p.setPrice(100.0);
        p.setProductType(ProductType.CHAIR);
        p.setName("mock product name");

        return p;
    }
}
