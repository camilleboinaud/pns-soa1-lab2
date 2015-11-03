package fr.unice.polytech.si5.soa1.lab2.flows.business.minibo;


import fr.unice.polytech.si5.soa1.lab2.flows.utils.Pair;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.lang.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by camille on 28/09/15.
 */

@XmlType(name = "order")
public class Order extends StorableContent{

    private Collection<Pair<Item, Integer>> cart = null;
    private Customer customer;
    private Package packaging = null;
    private Delivery delivery = null;
    private OrderStatus status = null;

    public Order(){
        this.cart = new ArrayList<Pair<Item, Integer>>();
        this.status = OrderStatus.NOT_VALIDATED;
    }

    @XmlElement(required = true)
    public Collection<Pair<Item, Integer>> getCart() {
        return cart;
    }

    public void setCart(Collection<Pair<Item, Integer>> cart){
        this.cart = cart;
    }

    public boolean addItemToCart(Item item, Integer number){
        if(number >= 0) {
            for(Pair<Item, Integer> pair : cart){
                if(pair.getLeft() == item){
                    cart.remove(pair);
                    break;
                }
            }
            cart.add(new Pair<Item, Integer>(item, number));
            return true;
        }
        return false;
    }

    public boolean removeItemFromCart(Item item){
        for(Pair<Item, Integer> pair : cart){
            if(pair.getLeft() == item){
                cart.remove(pair);
                return true;
            }
        }
        return false;
    }

    @XmlElement(required = true)
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer){
        this.customer = customer;
    }

    @XmlElement(required = true)
    public Package getPackaging() {
        return packaging;
    }

    public void setPackaging(Package packaging) {
        this.packaging = packaging;
    }

    @XmlElement(required = true)
    public Delivery getDelivery() {
        return delivery;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    @XmlElement(required = true)
    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;

        Order order = (Order) o;

        if (getCart() != null ? !getCart().equals(order.getCart()) : order.getCart() != null) return false;
        if (getCustomer() != null ? !getCustomer().equals(order.getCustomer()) : order.getCustomer() != null)
            return false;
        if (getPackaging() != null ? !getPackaging().equals(order.getPackaging()) : order.getPackaging() != null)
            return false;
        return !(getDelivery() != null ? !getDelivery().equals(order.getDelivery()) : order.getDelivery() != null);

    }

    @Override
    public int hashCode() {
        int result = getCart() != null ? getCart().hashCode() : 0;
        result = 31 * result + (getCustomer() != null ? getCustomer().hashCode() : 0);
        result = 31 * result + (getPackaging() != null ? getPackaging().hashCode() : 0);
        result = 31 * result + (getDelivery() != null ? getDelivery().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Order{" +
                "cart=" + cart +
                ", customer=" + customer +
                ", packaging=" + packaging +
                ", delivery=" + delivery +
                '}';
    }
}
