package fr.unice.polytech.si5.soa1.lab2.flows.request;

import fr.unice.polytech.si5.soa1.lab2.flows.business.maximeuble.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import java.io.StringWriter;

/**
 * Created by camille on 08/11/15.
 */
public class MaximeulbleOrderRequestBuilder {

    public String buildMaximeubleProductRequest(String id) {
        StringBuilder builder = new StringBuilder();
        builder.append("<sal:findProductById xmlns:sal=\"http://salesmanagement.soa1.polytech.unice.fr/\">\n");
        builder.append("<product_id>"+id+"</product_id>\n");
        builder.append("</sal:findProductById>\n");

        return builder.toString();
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

}