package fr.unice.polytech.si5.soa1.lab2.flows.request;

import fr.unice.polytech.si5.soa1.lab2.flows.business.Customer;
import fr.unice.polytech.si5.soa1.lab2.flows.business.shopping3000.Shopping3000Resources;

/**
 * Created by camille on 08/11/15.
 */
public class MiniboOrderRequestBuilder {

    public String buildMiniboProductRequest(String id) {
        StringBuilder builder = new StringBuilder();
        builder.append("<ws:displayContent xmlns:ws=\"http://ws.lab1.soa1.polytech.unice.fr/\">\n");
        builder.append("<contentType>ITEM</contentType>\n");
        builder.append("<contentId>"+id+"</contentId>\n");
        builder.append("</ws:displayContent>\n");

        return builder.toString();
    }

    public String buildStartMiniboOrder() {
        StringBuilder builder = new StringBuilder();
        builder.append("<ws:startOrder xmlns:ws=\"http://ws.lab1.soa1.polytech.unice.fr/\">\n");
        builder.append("</ws:startOrder>\n");

        return builder.toString();
    }

    public String buildAddItemMiniboOrder(String orderId, String itemId, String quantity){

        StringBuilder builder = new StringBuilder();

        builder.append("<ws:updateItemToCart xmlns:ws=\"http://ws.lab1.soa1.polytech.unice.fr/\">\n");
        builder.append("<orderId>"+orderId+"</orderId>\n");
        builder.append("<itemId>"+itemId+"</itemId>\n");
        builder.append("<quantity>"+quantity+"</quantity>\n");
        builder.append("</ws:updateItemToCart>\n");

        return builder.toString();

    }

    public String buildSetCustomerMiniboOrder(String orderId){

        StringBuilder builder = new StringBuilder();

        builder.append("<ws:provideCustomerShipmentData xmlns:ws=\"http://ws.lab1.soa1.polytech.unice.fr/\">\n");
        builder.append("<orderId>"+orderId+"</orderId>\n");
        builder.append("<firstname>not_specified</firstname>\n");
        builder.append("<lastname>"+ Shopping3000Resources.shopping3000Data.getName()+"</lastname>\n");
        builder.append("<address>"+Shopping3000Resources.shopping3000Data.getAddress().getAddress()+"</address>\n");
        builder.append("<zipcode>"+Shopping3000Resources.shopping3000Data.getAddress().getZipcode()+"</zipcode>\n");
        builder.append("<city>"+Shopping3000Resources.shopping3000Data.getAddress().getCity()+"</city>\n");
        builder.append("<country>not_specified</country>\n");
        builder.append("<phone>not_specified</phone>\n");
        builder.append("<email>"+Shopping3000Resources.shopping3000Data.getEmail()+"</email>\n");
        builder.append("</ws:provideCustomerShipmentData>\n");

        return builder.toString();

    }

    public String buildPayOrderMinibo(String orderId){

        StringBuilder builder = new StringBuilder();

        builder.append("<ws:payOrder xmlns:ws=\"http://ws.lab1.soa1.polytech.unice.fr/\">\n");
        builder.append("<orderId>"+orderId+"</orderId>");
        builder.append("<creditCardNumber>"+Shopping3000Resources.shopping3000Data.getCreditcard().getNumber()+"</creditCardNumber>");
        builder.append("<email>"+Shopping3000Resources.shopping3000Data.getEmail()+"</email>");
        builder.append("</ws:payOrder>\n");

        return builder.toString();

    }



}
