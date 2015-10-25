package fr.unice.polytech.si5.soa1.lab2.flows.processors;


import fr.unice.polytech.si5.soa1.lab2.flows.business.Manufacturer;
import fr.unice.polytech.si5.soa1.lab2.flows.business.Order;
import fr.unice.polytech.si5.soa1.lab2.flows.business.OrderItem;
import fr.unice.polytech.si5.soa1.lab2.flows.utils.Pair;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.*;

public class Csv2Order implements Processor {

    public void process(Exchange exchange) throws Exception {
        // retrieving the body of the exchanged message
        List<Map<String, String> > input = (List<Map<String, String> >) exchange.getIn().getBody();
        // transforming the input into a person
        Order output =  builder(input);
        // Putting the person inside the body of the message
        exchange.getIn().setBody(output);
    }

    private Order builder(List<Map<String, String> > data) {

        Order order = new Order();

        for (Map <String, String> item : data) {
            String address = item.get("address");
            String name    = item.get("name");
            String email   = item.get("email");

            if (order.getAddress() == null && address != null) {
                order.setAddress(address);
            }

            if (order.getEmail() == null && email != null) {
                order.setEmail(email);
            }

            if (order.getName() == null && name != null) {
                order.setName(name);
            }

            order.getItems().add(new Pair<OrderItem, Integer>(
                    new OrderItem(
                            Manufacturer.valueOf((item.get("manufacturer"))),
                            Integer.valueOf(item.get("itemid"))
                    ),
                    Integer.valueOf(item.get("quantity")))
            );
        }

        return order;
    }
}
