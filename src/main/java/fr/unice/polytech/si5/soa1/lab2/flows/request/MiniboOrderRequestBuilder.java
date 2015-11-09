package fr.unice.polytech.si5.soa1.lab2.flows.request;

import fr.unice.polytech.si5.soa1.lab2.flows.business.Customer;

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

    public String buildSetCustomerMiniboOrder(String orderId, Customer customer){

        StringBuilder builder = new StringBuilder();

        builder.append("<ws:provideCustomerShipmentData xmlns:ws=\"http://ws.lab1.soa1.polytech.unice.fr/\">\n");
        builder.append("<orderId>"+orderId+"</orderId>\n");
        builder.append("<firstname>not_specified</firstname>\n");
        builder.append("<lastname>"+customer.getName()+"</lastname>\n");
        builder.append("<address>"+customer.getAddress().getAddress()+"</address>\n");
        builder.append("<zipcode>"+customer.getAddress().getZipcode()+"</zipcode>\n");
        builder.append("<city>"+customer.getAddress().getCity()+"</city>\n");
        builder.append("<country>not_specified</country>\n");
        builder.append("<phone>not_specified</phone>\n");
        builder.append("<email>"+customer.getEmail()+"</email>\n");
        builder.append("</ws:provideCustomerShipmentData>\n");

        return builder.toString();

    }



}
