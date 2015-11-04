package fr.unice.polytech.si5.soa1.lab2.flows.utils;

import fr.unice.polytech.si5.soa1.lab2.flows.business.shopping3000.Manufacturer;
import fr.unice.polytech.si5.soa1.lab2.flows.business.maximeuble.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import java.io.StringWriter;

/**
 * Created by Tianhao on 10/25/2015.
 */
public class RequestBuilder {

    public String buildCatalogListRequest(Manufacturer manufacturer) {
        if (manufacturer == Manufacturer.MINIBO){
            return buildMiniboCatalogListRequest();
        } else if (manufacturer == Manufacturer.MAXIMEUBLE){
            return buildMaximeubleCatalogListRequest();
        }
        return null;
    }

    public String buildMaximeubleMakeOrderRequest(MakeOrderParams params) throws JAXBException {
        StringBuilder builder = new StringBuilder();

        JAXBContext ctx = JAXBContext.newInstance(OrderRequest.class, Client.class, Product.class, OrderItem.class, ProductType.class);

        Marshaller orderRequestMarshaller = ctx.createMarshaller();
        Marshaller clientMarshaller = ctx.createMarshaller();
        orderRequestMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
        clientMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);

        StringWriter orderRequestrSw = new StringWriter();
        StringWriter ClientSw = new StringWriter();

        orderRequestMarshaller.marshal(new JAXBElement(new QName("order_request"), OrderRequest.class, params.getOrderRequest()), ClientSw);
        clientMarshaller.marshal(new JAXBElement(new QName("client"), Client.class, params.getClient()), ClientSw);

        String orderRequestString = orderRequestrSw.toString();
        String clientString = ClientSw.toString();

        builder.append("<sal:makeOrder xmlns:sal=\"http://salesmanagement.soa1.polytech.unice.fr/\">\n");
        builder.append(orderRequestString);
        builder.append(clientString);
        builder.append("</sal:makeOrder>\n");

        return builder.toString();
    }

    public String buildCatalogGetItemRequest(Manufacturer manufacturer,String id) {
        StringBuilder builder = new StringBuilder();
        if (manufacturer == Manufacturer.MINIBO){
            return buildMiniboProductRequest(id);
        }  else if (manufacturer == Manufacturer.MAXIMEUBLE){
            return buildMaximeubleProductRequest(id);
        }
        return builder.toString();
    }

    public String buildStartMiniboOrder() {
        StringBuilder builder = new StringBuilder();
        builder.append("<ws:startOrder xmlns:ws=\"http://ws.lab1.soa1.polytech.unice.fr/\">\n");
        builder.append("</ws:startOrder>\n");

        return builder.toString();
    }



    private String buildMiniboCatalogListRequest(){
        StringBuilder builder = new StringBuilder();
        builder.append("<ws:listContents xmlns:ws=\"http://ws.lab1.soa1.polytech.unice.fr/\">\n");
        builder.append("<contentType>ITEM</contentType>\n");
        builder.append("</ws:listContents>\n");
        return builder.toString();
    }

    private String buildMaximeubleCatalogListRequest(){
        StringBuilder builder = new StringBuilder();
        builder.append("<ws:listContents xmlns:ws=\"http://ws.lab1.soa1.polytech.unice.fr/\">\n");
        builder.append("<contentType>ITEM</contentType>\n");
        builder.append("</ws:listContents>\n");
        return builder.toString();
    }

    public String buildMiniboProductRequest(String id) {
        StringBuilder builder = new StringBuilder();
        builder.append("<ws:displayContent xmlns:ws=\"http://ws.lab1.soa1.polytech.unice.fr/\">\n");
        builder.append("<contentType>ITEM</contentType>\n");
        builder.append("<contentId>"+id+"</contentId>\n");
        builder.append("</ws:displayContent>\n");

        return builder.toString();
    }

    public String buildMaximeubleProductRequest(String id) {
        StringBuilder builder = new StringBuilder();
        builder.append("<sal:findProductById xmlns:sal=\"http://salesmanagement.soa1.polytech.unice.fr/\">\n");
        builder.append("<product_id>"+id+"</product_id>\n");
        builder.append("</sal:findProductById>\n");

        return builder.toString();
    }

}
