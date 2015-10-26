package fr.unice.polytech.si5.soa1.lab2.flows.business;

import java.io.Serializable;

/**
 * Created by camille on 26/10/15.
 */
public class Customer implements Serializable{

    protected String name;
    protected Address address;
    protected String email;
    protected CreditCard creditCard;

    public Customer(String name, Address address, String email, CreditCard creditCard){

        if(name == null || address == null || email == null || creditCard == null) {
            throw new NullPointerException();
        } else {
            this.name = name;
            this.address = address;
            this.email = email;
            this.creditCard = creditCard;
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;

        Customer customer = (Customer) o;

        if (getName() != null ? !getName().equals(customer.getName()) : customer.getName() != null) return false;
        if (getAddress() != null ? !getAddress().equals(customer.getAddress()) : customer.getAddress() != null)
            return false;
        if (getEmail() != null ? !getEmail().equals(customer.getEmail()) : customer.getEmail() != null) return false;
        return !(getCreditcard() != null ? !getCreditcard().equals(customer.getCreditcard()) : customer.getCreditcard() != null);

    }

    @Override
    public int hashCode() {
        int result = getName() != null ? getName().hashCode() : 0;
        result = 31 * result + (getAddress() != null ? getAddress().hashCode() : 0);
        result = 31 * result + (getEmail() != null ? getEmail().hashCode() : 0);
        result = 31 * result + (getCreditcard() != null ? getCreditcard().hashCode() : 0);
        return result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public CreditCard getCreditcard() {
        return creditCard;
    }

    public void setCreditcard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "name='" + name + '\'' +
                ", address=" + address +
                ", email='" + email + '\'' +
                ", creditCard='" + creditCard + '\'' +
                '}';
    }
}
