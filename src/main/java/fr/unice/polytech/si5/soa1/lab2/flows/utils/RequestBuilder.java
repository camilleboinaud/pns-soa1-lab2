package fr.unice.polytech.si5.soa1.lab2.flows.utils;

import fr.unice.polytech.si5.soa1.lab2.flows.business.Manufacturer;

/**
 * Created by Tianhao on 10/25/2015.
 */
public class RequestBuilder {

    public String buildCatalogListRequest() {
        StringBuilder builder = new StringBuilder();
        builder.append("<ws:listContents xmlns:ws=\"http://ws.lab1.soa1.polytech.unice.fr/\">\n");
        builder.append("<contentType>ITEM</contentType>\n");
        builder.append("</ws:listContents>\n");
        return builder.toString();
    }

    public String buildCatalogGetItemRequest(Manufacturer manufacturer,String id) {
        StringBuilder builder = new StringBuilder();
        if (manufacturer == Manufacturer.MINIBO){
            builder.append("<ws:displayContent xmlns:ws=\"http://ws.lab1.soa1.polytech.unice.fr/\">\n");
            builder.append("<contentType>ITEM</contentType>\n");
            builder.append("<contentId>"+id+"</contentId>\n");
            builder.append("</ws:displayContent>\n");
        }  else if (manufacturer == Manufacturer.MAXIMEUBLE){
            builder.append("<sal:findProductById xmlns:sal=\"http://salesmanagement.soa1.polytech.unice.fr/\">\n");
            builder.append("<product_id>"+id+"</product_id>\n");
            builder.append("</sal:findProductById>\n");
        }
        return builder.toString();
    }


}
