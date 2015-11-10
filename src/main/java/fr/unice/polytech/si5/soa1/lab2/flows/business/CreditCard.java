package fr.unice.polytech.si5.soa1.lab2.flows.business;

import java.io.Serializable;

/**
 * Created by camille on 26/10/15.
 */
public class CreditCard implements Serializable {

    protected String number;
    protected String validity;
    protected String csc;

    public  CreditCard() {}

    public CreditCard(String number, String validity, String csc) {
        this.number = number;
        this.validity = validity;
        this.csc = csc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CreditCard)) return false;

        CreditCard that = (CreditCard) o;

        if (getNumber() != null ? !getNumber().equals(that.getNumber()) : that.getNumber() != null) return false;
        if (getValidity() != null ? !getValidity().equals(that.getValidity()) : that.getValidity() != null)
            return false;
        return !(getCsc() != null ? !getCsc().equals(that.getCsc()) : that.getCsc() != null);

    }

    @Override
    public int hashCode() {
        int result = getNumber() != null ? getNumber().hashCode() : 0;
        result = 31 * result + (getValidity() != null ? getValidity().hashCode() : 0);
        result = 31 * result + (getCsc() != null ? getCsc().hashCode() : 0);
        return result;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }

    public String getCsc() {
        return csc;
    }

    public void setCsc(String csc) {
        this.csc = csc;
    }

    @Override
    public String toString() {
        return "CreditCard{" +
                "number='" + number + '\'' +
                ", validity='" + validity + '\'' +
                ", csc='" + csc + '\'' +
                '}';
    }
}
