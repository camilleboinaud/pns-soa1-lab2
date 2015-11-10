package fr.unice.polytech.si5.soa1.lab2.flows.business;

import java.io.Serializable;

/**
 * Created by camille on 26/10/15.
 */
public class Address implements Serializable{

    protected String address;
    protected String zipcode;
    protected String city;

    public Address() {}

    public Address(String address, String zipcode, String city){
        this.address = address;
        this.zipcode = zipcode;
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;

        Address address1 = (Address) o;

        if (getAddress() != null ? !getAddress().equals(address1.getAddress()) : address1.getAddress() != null)
            return false;
        if (getZipcode() != null ? !getZipcode().equals(address1.getZipcode()) : address1.getZipcode() != null)
            return false;
        return !(getCity() != null ? !getCity().equals(address1.getCity()) : address1.getCity() != null);

    }

    @Override
    public int hashCode() {
        int result = getAddress() != null ? getAddress().hashCode() : 0;
        result = 31 * result + (getZipcode() != null ? getZipcode().hashCode() : 0);
        result = 31 * result + (getCity() != null ? getCity().hashCode() : 0);
        return result;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Address{" +
                "address='" + address + '\'' +
                ", zipcode='" + zipcode + '\'' +
                ", city='" + city + '\'' +
                '}';
    }

}
