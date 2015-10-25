package fr.unice.polytech.si5.soa1.lab2.flows.business;


import fr.unice.polytech.si5.soa1.lab2.flows.utils.Pair;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Order implements Serializable {

    private List<Pair<OrderItem, Integer>> items;
    private String address;
    private String email;
    private String name;

    public Order(){
        this.items = new ArrayList<Pair<OrderItem, Integer>>();
    }

    public Order(String address, String email, String name, Object... args){

        this.address = address;
        this.email = email;
        this.name = name;

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
        if (getAddress() != null ? !getAddress().equals(order.getAddress()) : order.getAddress() != null) return false;
        if (getEmail() != null ? !getEmail().equals(order.getEmail()) : order.getEmail() != null) return false;
        return !(getName() != null ? !getName().equals(order.getName()) : order.getName() != null);

    }

    @Override
    public int hashCode() {
        int result = getItems() != null ? getItems().hashCode() : 0;
        result = 31 * result + (getAddress() != null ? getAddress().hashCode() : 0);
        result = 31 * result + (getEmail() != null ? getEmail().hashCode() : 0);
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        return result;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Pair<OrderItem, Integer>> getItems() {
        return items;
    }

    public void setItems(ArrayList<Pair<OrderItem, Integer>> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Order {" +
                "address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", items=" + items + '\'' +
                '}';
    }

}
