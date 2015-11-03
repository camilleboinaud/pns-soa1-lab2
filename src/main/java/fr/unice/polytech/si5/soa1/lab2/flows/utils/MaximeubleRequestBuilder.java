package fr.unice.polytech.si5.soa1.lab2.flows.utils;

import fr.unice.polytech.si5.soa1.lab2.flows.business.maximeuble.Product;

/**
 * Created by lecpie on 11/2/15.
 */
public class MaximeubleRequestBuilder {

    public String buildProductRequest (int productId) {
        StringBuilder builder = new StringBuilder();

        /*
        builder.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:sal=\"http://salesmanagement.soa1.polytech.unice.fr/\">\n");
        builder.append("    <soapenv:Header/>\n");
        builder.append("    <soapenv:Body>\n");
        */
        builder.append("        <sal:findProductById xmlns:sal=\"http://salesmanagement.soa1.polytech.unice.fr/\">\n");
        builder.append("            <product_id>"+ productId + "</product_id>\n");
        builder.append("        </sal:findProductById>\n");
        /*
        builder.append("   </soapenv:Body>\n");
        builder.append("</soapenv:Envelope>\n");
        */
        return builder.toString();
    }
}
