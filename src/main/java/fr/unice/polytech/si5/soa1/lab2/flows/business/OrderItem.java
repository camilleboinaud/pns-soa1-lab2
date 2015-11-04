package fr.unice.polytech.si5.soa1.lab2.flows.business;


import fr.unice.polytech.si5.soa1.lab2.flows.business.shopping3000.Manufacturer;

import java.io.Serializable;

public class OrderItem implements Serializable {

    private Manufacturer manufacturer;
    private int manufacturerId;

    public OrderItem(Manufacturer manufacturer, int manufacturerId){
        this.manufacturer = manufacturer;
        this.manufacturerId = manufacturerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderItem)) return false;

        OrderItem orderItem = (OrderItem) o;

        if (getManufacturerId() != orderItem.getManufacturerId()) return false;
        return getManufacturer() == orderItem.getManufacturer();

    }

    @Override
    public int hashCode() {
        int result = getManufacturer().hashCode();
        result = 31 * result + getManufacturerId();
        return result;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public int getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(int manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    @Override
    public String toString() {
        return "OrderItem {" +
                "manufacturer='" + manufacturer + '\'' +
                ", manufacturerId='" + manufacturerId + '\'' +
                '}';
    }
}
