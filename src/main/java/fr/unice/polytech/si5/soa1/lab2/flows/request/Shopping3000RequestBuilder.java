package fr.unice.polytech.si5.soa1.lab2.flows.request;

import fr.unice.polytech.si5.soa1.lab2.flows.business.CreditCard;

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

}
