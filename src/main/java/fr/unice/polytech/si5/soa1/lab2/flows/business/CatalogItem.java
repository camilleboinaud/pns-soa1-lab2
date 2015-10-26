package fr.unice.polytech.si5.soa1.lab2.flows.business;

import fr.unice.polytech.si5.soa1.lab2.flows.business.Manufacturer;
import fr.unice.polytech.si5.soa1.lab2.flows.utils.Pair;

import java.io.Serializable;

/**
 * Created by Tianhao on 10/25/2015.
 */
public class CatalogItem implements Serializable {
    private Pair<Manufacturer,Integer> id;
    private String name;
    private double price;
    private String description;

    public Pair<Manufacturer, Integer> getId() {
        return id;
    }

    public void setId(Pair<Manufacturer, Integer> id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "CatalogItem {" +
                ", name='" + name + '\'' +
                ", manufacturer='" + id.getLeft() + '\'' +
                ", id='" + id.getRight() + '\'' +
                ", description=" + description +
                ", price=" + price +
                '}';
    }
}
