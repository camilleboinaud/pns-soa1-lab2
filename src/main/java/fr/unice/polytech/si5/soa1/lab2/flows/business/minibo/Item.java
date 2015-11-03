package fr.unice.polytech.si5.soa1.lab2.flows.business.minibo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by camille on 28/09/15.
 */

@XmlType(name = "item")
public class Item extends StorableContent{

    private Double price = null;
    private String name = null;
    private String description = null;

    public Item(){
        super();
        this.price = 0.0;
        this.name = "";
        this.description = "";
    }

    public Item(Double price, String name, String description){
        super();
        this.price = price;
        this.name = name;
        this.description = description;
    }

    @XmlElement(required = true)
    public Double getPrice() {
        return price;
    }

    @XmlElement(required = true)
    public String getName() {
        return name;
    }

    @XmlElement(required = true)
    public String getDescription() {
        return description;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;

        Item item = (Item) o;

        if (getPrice() != null ? !getPrice().equals(item.getPrice()) : item.getPrice() != null) return false;
        if (getName() != null ? !getName().equals(item.getName()) : item.getName() != null) return false;
        return !(getDescription() != null ? !getDescription().equals(item.getDescription()) : item.getDescription() != null);

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
        return "Item{" +
                ", price=" + price +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
