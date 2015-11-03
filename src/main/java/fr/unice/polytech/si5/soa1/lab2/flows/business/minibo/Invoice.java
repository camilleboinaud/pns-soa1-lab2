package fr.unice.polytech.si5.soa1.lab2.flows.business.minibo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Calendar;

/**
 * Created by camille on 28/09/15.
 */

@XmlType(name = "invoice")
public class Invoice extends StorableContent{


    private Calendar createdAt = Calendar.getInstance();
    private Calendar updatedAt = Calendar.getInstance();
    private Order order = null;
    private boolean paid = false;

    public Invoice(){
        super();
        this.order = new Order();
    }

    public Invoice(Order order){
        super();
        this.order = order;
    }

    @XmlElement(required = true)
    public Calendar getCreatedAt() {
        return createdAt;
    }

    @XmlElement(required = true)
    public Calendar getUpdatedAt() {
        return updatedAt;
    }

    @XmlElement(required = true)
    public Order getOrder() {
        return order;
    }

    @XmlElement(required = true)
    public boolean isPaid() {
        return paid;
    }

    public void setCreatedAt(Calendar createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Calendar updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setPaid(){
        this.paid = true;
        this.updatedAt = Calendar.getInstance();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Invoice)) return false;

        Invoice invoice = (Invoice) o;

        if (isPaid() != invoice.isPaid()) return false;
        if (getCreatedAt() != null ? !getCreatedAt().equals(invoice.getCreatedAt()) : invoice.getCreatedAt() != null)
            return false;
        if (getUpdatedAt() != null ? !getUpdatedAt().equals(invoice.getUpdatedAt()) : invoice.getUpdatedAt() != null)
            return false;
        return !(getOrder() != null ? !getOrder().equals(invoice.getOrder()) : invoice.getOrder() != null);

    }

    @Override
    public int hashCode() {
        int result = getCreatedAt() != null ? getCreatedAt().hashCode() : 0;
        result = 31 * result + (getUpdatedAt() != null ? getUpdatedAt().hashCode() : 0);
        result = 31 * result + (getOrder() != null ? getOrder().hashCode() : 0);
        result = 31 * result + (isPaid() ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", order=" + order +
                ", paid=" + paid +
                '}';
    }
}
