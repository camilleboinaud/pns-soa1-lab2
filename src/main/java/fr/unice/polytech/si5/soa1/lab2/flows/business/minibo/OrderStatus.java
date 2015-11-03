package fr.unice.polytech.si5.soa1.lab2.flows.business.minibo;

/**
 * Created by camille on 05/10/15.
 */
public enum OrderStatus {

    NOT_VALIDATED ("Your order will be validated as sooner as possible"),
    IN_PREPARATION("Your order has been validated, it is now going to be prepared for shipment"),
    SHIPPED("Your order has been shipped, you will receive it quickly");

    private final String description;

    OrderStatus(String description){
        this.description = description;
    }

    public String toString(){
        return this.description;
    }
}
