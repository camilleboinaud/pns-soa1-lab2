package fr.unice.polytech.si5.soa1.lab2.flows.business.minibo;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by camille on 28/09/15.
 */
@XmlType(name = "customer")
public class Customer extends StorableContent{

    private String firstname;
    private String lastname;
    private String address;
    private String zipcode;
    private String city;
    private String country;
    private String phone;
    private String email;

    public Customer(){
        super();
        this.firstname = "";
        this.lastname = "";
        this.address = "";
        this.zipcode = "";
        this.city = "";
        this.country = "";
        this.phone = "";
        this.email = "";

    }

    public Customer(String firstname, String lastname, String address, String zipcode,
                    String city, String country, String phone, String email){
        super();
        this.firstname = firstname;
        this.lastname = lastname;
        this.address = address;
        this.zipcode = zipcode;
        this.city = city;
        this.country = country;
        this.phone = phone;
        this.email = email;
    }

    @XmlElement(required = true)
    public String getEmail(){
        return this.email;
    }
    @XmlElement(required = true)
    public String getFirstname() {
        return firstname;
    }
    @XmlElement(required = true)
    public String getLastname() {
        return lastname;
    }
    @XmlElement(required = true)
    public String getAddress() {
        return address;
    }
    @XmlElement(required = true)
    public String getZipcode() {
        return zipcode;
    }
    @XmlElement(required = true)
    public String getCity() {
        return city;
    }
    @XmlElement(required = true)
    public String getCountry() {
        return country;
    }
    @XmlElement(required = true)
    public String getPhone() {
        return phone;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;

        Customer customer = (Customer) o;

        if (getFirstname() != null ? !getFirstname().equals(customer.getFirstname()) : customer.getFirstname() != null)
            return false;
        if (getLastname() != null ? !getLastname().equals(customer.getLastname()) : customer.getLastname() != null)
            return false;
        if (getAddress() != null ? !getAddress().equals(customer.getAddress()) : customer.getAddress() != null)
            return false;
        if (getZipcode() != null ? !getZipcode().equals(customer.getZipcode()) : customer.getZipcode() != null)
            return false;
        if (getCity() != null ? !getCity().equals(customer.getCity()) : customer.getCity() != null) return false;
        if (getCountry() != null ? !getCountry().equals(customer.getCountry()) : customer.getCountry() != null)
            return false;
        if (getPhone() != null ? !getPhone().equals(customer.getPhone()) : customer.getPhone() != null) return false;
        return !(getEmail() != null ? !getEmail().equals(customer.getEmail()) : customer.getEmail() != null);

    }

    @Override
    public int hashCode() {
        int result = getFirstname() != null ? getFirstname().hashCode() : 0;
        result = 31 * result + (getLastname() != null ? getLastname().hashCode() : 0);
        result = 31 * result + (getAddress() != null ? getAddress().hashCode() : 0);
        result = 31 * result + (getZipcode() != null ? getZipcode().hashCode() : 0);
        result = 31 * result + (getCity() != null ? getCity().hashCode() : 0);
        result = 31 * result + (getCountry() != null ? getCountry().hashCode() : 0);
        result = 31 * result + (getPhone() != null ? getPhone().hashCode() : 0);
        result = 31 * result + (getEmail() != null ? getEmail().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", address='" + address + '\'' +
                ", zipcode='" + zipcode + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
