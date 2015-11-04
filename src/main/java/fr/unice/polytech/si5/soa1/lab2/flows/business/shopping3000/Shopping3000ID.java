package fr.unice.polytech.si5.soa1.lab2.flows.business.shopping3000;

import fr.unice.polytech.si5.soa1.lab2.flows.business.shopping3000.Manufacturer;
import fr.unice.polytech.si5.soa1.lab2.flows.utils.Pair;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by lecpie on 11/1/15.
 */
@XmlType
public class Shopping3000ID extends Pair<Manufacturer, Integer> {

    @XmlElement(name = "Manufacturer", required = true)
    public Manufacturer getManufacturer() {
        return getLeft();
    }

    public void setManufacturer (Manufacturer manufacturer) {
        this.setLeft(manufacturer);
    }

    public Integer getId() {
        return getRight();
    }

    @XmlElement(name = "ID", required = true)
    public void setId (Integer id) {
        this.setRight(id);
    }
}
