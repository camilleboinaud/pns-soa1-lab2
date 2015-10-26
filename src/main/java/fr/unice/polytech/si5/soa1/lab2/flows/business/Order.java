package fr.unice.polytech.si5.soa1.lab2.flows.business;


import fr.unice.polytech.si5.soa1.lab2.flows.utils.Pair;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Order implements Serializable {

    private List<Pair<OrderItem, Integer>> items;
    private Customer customer;

    public Order(){
        this.items = new ArrayList<Pair<OrderItem, Integer>>();
    }

    public Order(Customer customer, Object... args){

        this.customer = customer;

        if(args.length > 0) {
            if (args.length == 1 && args[0] instanceof List) {
                items = (List<Pair<OrderItem, Integer>>)args[0];
            } else {
                throw new IllegalArgumentException();
            }
        } else {
            items = new ArrayList<Pair<OrderItem, Integer>>();
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;

        Order order = (Order) o;

        if (getItems() != null ? !getItems().equals(order.getItems()) : order.getItems() != null) return false;
        return !(getCustomer() != null ? !getCustomer().equals(order.getCustomer()) : order.getCustomer() != null);

    }

    @Override
    public int hashCode() {
        int result = getItems() != null ? getItems().hashCode() : 0;
        result = 31 * result + (getCustomer() != null ? getCustomer().hashCode() : 0);
        return result;
    }

    public List<Pair<OrderItem, Integer>> getItems() {
        return items;
    }

    public void setItems(List<Pair<OrderItem, Integer>> items) {
        this.items = items;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "Order{" +
                "items=" + items +
                ", customer=" + customer +
                '}';
    }
}
