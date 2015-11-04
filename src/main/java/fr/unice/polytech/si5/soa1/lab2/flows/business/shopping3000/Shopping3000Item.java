package fr.unice.polytech.si5.soa1.lab2.flows.business.shopping3000;

import fr.unice.polytech.si5.soa1.lab2.flows.utils.Pair;

import java.io.Serializable;

/**
 * Created by Tianhao on 10/25/2015.
 */
public class Shopping3000Item implements Serializable {
    private Pair<Manufacturer,Integer> id;
    private String name;
    private Double price;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        int result = getPrice() != null ? getPrice().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Shopping3000Item {" +
                "name='" + name + '\'' +
                ",manufacturer='" + id.getLeft() + '\'' +
                ",id='" + id.getRight() + '\'' +
                ",description=" + description +
                ",price=" + price +
                '}';
    }
}
