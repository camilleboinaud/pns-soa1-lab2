package fr.unice.polytech.si5.soa1.lab2.flows.request;

import fr.unice.polytech.si5.soa1.lab2.flows.business.Address;
import fr.unice.polytech.si5.soa1.lab2.flows.business.CreditCard;
import fr.unice.polytech.si5.soa1.lab2.flows.utils.Shopping3000Info;

/**
 * Created by camille on 14/11/15.
 */
public class Shopping3000RequestBuilder {

    public String buildPaymentRequest(CreditCard creditCard, double amount){
        StringBuilder builder = new StringBuilder();

        builder.append("<sal:PayByCreditCard xmlns:sal=\"http://webservice.flows.lab2.soa1.si5.polytech.unice.fr/\">\n");
        builder.append("<CreditCard>\n");
        builder.append("<csc>"+creditCard.getCsc()+"</csc>\n");
        builder.append("<number>"+creditCard.getNumber()+"</number>\n");
        builder.append("<validity>"+creditCard.getValidity()+"</validity>\n");
        builder.append("</CreditCard>\n");
        builder.append("<Amount>"+amount+"</Amount>\n");
        builder.append("</sal:PayByCreditCard>\n");

        return builder.toString();
    }

    public String buildDeliveryPriceRequest(Address dstAddress){

        StringBuilder builder = new StringBuilder();
        Address srcAddress = Shopping3000Info.SHOPPING3000_ADDRESS;

        builder.append("<sal:CalculateDeliveryPriceByAddresses xmlns:sal=\"http://webservice.flows.lab2.soa1.si5.polytech.unice.fr/\">\n");
        builder.append("<addresses>\n");

        builder.append("<left>\n");
        builder.append("<address>" + srcAddress.getAddress() + "</address>\n");
        builder.append("<zipcode>" + srcAddress.getZipcode() + "</zipcode>\n");
        builder.append("<city>" + srcAddress.getCity() + "</city>\n");
        builder.append("</left>\n");

        builder.append("<right>\n");
        builder.append("<address>" + dstAddress.getAddress() + "</address>\n");
        builder.append("<zipcode>" + dstAddress.getZipcode() + "</zipcode>\n");
        builder.append("<city>" + dstAddress.getCity() + "</city>\n");
        builder.append("</right>\n");

        builder.append("</addresses>\n");
        builder.append("</sal:CalculateDeliveryPriceByAddresses>\n");

        return builder.toString();
    }

}
