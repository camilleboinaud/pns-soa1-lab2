package fr.unice.polytech.si5.soa1.lab2.flows.business.shopping3000;

import fr.unice.polytech.si5.soa1.lab2.flows.business.shopping3000.Manufacturer;
import fr.unice.polytech.si5.soa1.lab2.flows.utils.Pair;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by lecpie on 11/1/15.
 */
@XmlType(name = "Shopping3000ID")
public class Shopping3000ID {

    private Pair<Manufacturer, Integer> id = new Pair<Manufacturer, Integer>();

    @XmlElement(name = "Manufacturer", required = true)
    public Manufacturer getManufacturer() {
        return this.id.getLeft();
    }

    public void setManufacturer (Manufacturer manufacturer) {
        this.id.setLeft(manufacturer);
    }

    public Integer getId() {
        return this.id.getRight();
    }

    @XmlElement(name = "ID", required = true)
    public void setId (Integer id) {
        this.id.setRight(id);
    }
}
