package fr.unice.polytech.si5.soa1.lab2.flows.processors;


import fr.unice.polytech.si5.soa1.lab2.flows.business.*;
import fr.unice.polytech.si5.soa1.lab2.flows.business.shopping3000.Manufacturer;
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

            String address      = item.get("address");
            String name         = item.get("name");
            String email        = item.get("email");
            String zipcode      = item.get("zipcode");
            String city         = item.get("city");
            Manufacturer type   = Manufacturer.valueOf(item.get("manufacturer"));
            int product         = Integer.valueOf(item.get("itemid"));
            int quantity        = Integer.valueOf(item.get("quantity"));
            String creditcard_number   = item.get("creditcard");
            String creditcard_validity   = item.get("expiration");
            String creditcard_csc   = item.get("csc");

            try {

                Customer customer = new Customer(
                        name,
                        new Address(address, zipcode, city),
                        email,
                        new CreditCard(creditcard_number, creditcard_validity, creditcard_csc)
                );

                if(order.getCustomer() == null){
                    order.setCustomer(customer);
                }

                order.getItems().add(new Pair<OrderItem, Integer>(
                    new OrderItem(type, Integer.valueOf(product)),
                    Integer.valueOf(quantity)
                ));

            } catch (NullPointerException e){
                e.printStackTrace();
                return null;
            }
        }

        return order;
    }
}
