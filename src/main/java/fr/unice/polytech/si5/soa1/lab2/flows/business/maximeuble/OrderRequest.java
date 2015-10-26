package fr.unice.polytech.si5.soa1.lab2.flows.business.maximeuble;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.List;

@XmlType
public class OrderRequest implements Serializable {

    private List <OrderItem> order;
    private int orderId;

    @XmlElement(name = "order_item", required = true)
    public List<OrderItem> getOrder() { return order; }
    public void setOrder (List <OrderItem> order) { this.order = order; }

    @XmlElement(name = "order_id", required = false)
    public int getOrderId() { return orderId; }
    public void setOrderId (int orderId) { this.orderId = orderId; }

    public String toString() {
        String ret = "OrderRequest (items=[";

        for (OrderItem item : order) {
            ret += item + ","; // one more but who cares now
        }

        ret += "])";

        return ret;
    }
}
