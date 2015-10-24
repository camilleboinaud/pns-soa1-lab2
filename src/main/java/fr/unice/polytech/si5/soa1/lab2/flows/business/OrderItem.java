package fr.unice.polytech.si5.soa1.lab2.flows.business;


public class OrderItem {
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

    private Manufacturer manufacturer;
    private int manufacturerId;
}
