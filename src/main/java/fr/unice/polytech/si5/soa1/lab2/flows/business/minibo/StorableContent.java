package fr.unice.polytech.si5.soa1.lab2.flows.business.minibo;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import java.lang.*;

/**
 * Created by camille on 28/09/15.
 */

@XmlType(name = "storable_content")
@XmlSeeAlso({ Customer.class, Delivery.class, Invoice.class, Item.class, Order.class, Package.class})
public class StorableContent {

    private Integer id = null;

    @XmlElement(required = false)
    public Integer getId(){
        return id;
    }

    public void setId(Integer id){
        this.id = id;
    }

}
