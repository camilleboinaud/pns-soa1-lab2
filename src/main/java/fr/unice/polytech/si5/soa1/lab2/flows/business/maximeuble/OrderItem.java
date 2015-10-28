package fr.unice.polytech.si5.soa1.lab2.flows.business.maximeuble;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

@XmlType
public class OrderItem implements Serializable {
    private Product product;
    private int qty;
    private String option;

    @XmlElement(name = "product", required = true)
    public Product getProduct() { return product; }
    public void setProduct (Product product) { this.product = product; }

    @XmlElement(name = "qty", required = true)
    public int getQty() { return qty; }
    public void setQty (int qty) { this.qty = qty; }

    @XmlElement(name = "option", required = false)
    String getOption() { return option; }
    public void setOption (String option) { this.option = option; }


    @Override
    public String toString() {
        return "OrderItem{" +
                "product=" + product +
                ", qty=" + qty +
                ", option='" + option + '\'' +
                '}';
    }
}
