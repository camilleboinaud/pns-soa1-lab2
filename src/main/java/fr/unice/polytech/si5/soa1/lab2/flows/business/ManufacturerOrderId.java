package fr.unice.polytech.si5.soa1.lab2.flows.business;

import fr.unice.polytech.si5.soa1.lab2.flows.utils.Pair;

/**
 * Created by lecpie on 11/1/15.
 */
public class ManufacturerOrderId extends Pair<Manufacturer, Integer> {

    public Manufacturer getManufacturer() {
        return getLeft();
    }

    public void setManufacturer (Manufacturer manufacturer) {
        this.setLeft(manufacturer);
    }

    public Integer getId() {
        return getRight();
    }

    public void setId (Integer id) {
        this.setRight(id);
    }
}
