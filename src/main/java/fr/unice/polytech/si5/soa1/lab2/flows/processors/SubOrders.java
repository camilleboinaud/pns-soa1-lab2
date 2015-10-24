package fr.unice.polytech.si5.soa1.lab2.flows.processors;

import fr.unice.polytech.si5.soa1.lab2.flows.business.Manufacturer;
import fr.unice.polytech.si5.soa1.lab2.flows.business.Order;
import fr.unice.polytech.si5.soa1.lab2.flows.business.OrderItem;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.*;


public class SubOrders implements Processor {

    public void process(Exchange exchange) throws Exception {
        // retrieving the body of the exchanged message
        Order input = (Order) exchange.getIn().getBody();
        // transforming the input into a person
        Collection <Order> output =  builder(input);
        // Putting the person inside the body of the message
        exchange.getIn().setBody(output);
    }

    private Collection <Order> builder (Order order) {
        Map <Manufacturer, Order> suborders = new HashMap<Manufacturer, Order>();

        for (OrderItem item : order.getItems()) {
            if (!suborders.containsKey(item.getManufacturer())) {
                Order subOrder = new Order();
                subOrder.setName(order.getName());
                subOrder.setEmail(order.getEmail());
                subOrder.setAddress(order.getAddress());
                subOrder.setItems(new ArrayList<OrderItem>());

                suborders.put(item.getManufacturer(), subOrder);
            }

            suborders.get(item.getManufacturer()).getItems().add(item);
        }

        return suborders.values();
    }

}
