package fr.unice.polytech.si5.soa1.lab2.flowcatalog.utils;

/**
 * Created by Tianhao on 10/25/2015.
 */
public class RequestBuilder {

    public String buildCatalogListRequest() {
        StringBuilder builder = new StringBuilder();
        builder.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ws=\"http://ws.lab1.soa1.polytech.unice.fr/\">\n");
        builder.append("   <soapenv:Header/>\n");
        builder.append("    <soapenv:Body>\n");
        builder.append("   <ws:listContents>\n");
        builder.append("   <contentType>ITEM</contentType>\n");
        builder.append("   </ws:listContents>\n");
        builder.append("   </soapenv:Body>\n");
        builder.append("   </soapenv:Envelope>\n");
        return builder.toString();
    }

}
