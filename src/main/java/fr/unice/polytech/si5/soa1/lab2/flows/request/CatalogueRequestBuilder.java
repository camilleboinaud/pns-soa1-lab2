package fr.unice.polytech.si5.soa1.lab2.flows.request;

import fr.unice.polytech.si5.soa1.lab2.flows.business.shopping3000.Manufacturer;

/**
 * Created by camille on 08/11/15.
 */
public class CatalogueRequestBuilder {

    public String buildCatalogListRequest(Manufacturer manufacturer) {
        if (manufacturer == Manufacturer.MINIBO){
            return buildMiniboCatalogListRequest();
        } else if (manufacturer == Manufacturer.MAXIMEUBLE){
            return buildMaximeubleCatalogListRequest();
        }
        return null;
    }

    public String buildCatalogGetItemRequest(Manufacturer manufacturer,String id) {
        StringBuilder builder = new StringBuilder();
        if (manufacturer == Manufacturer.MINIBO){
            return (new MiniboOrderRequestBuilder()).buildMiniboProductRequest(id);
        }  else if (manufacturer == Manufacturer.MAXIMEUBLE){
            return (new MaximeulbleOrderRequestBuilder()).buildMaximeubleProductRequest(id);
        }
        return builder.toString();
    }

    public String buildMiniboCatalogListRequest(){
        StringBuilder builder = new StringBuilder();
        builder.append("<ws:listContents xmlns:ws=\"http://ws.lab1.soa1.polytech.unice.fr/\">\n");
        builder.append("<contentType>ITEM</contentType>\n");
        builder.append("</ws:listContents>\n");
        return builder.toString();
    }

    public String buildMaximeubleCatalogListRequest(){
        StringBuilder builder = new StringBuilder();
        builder.append("<ws:listContents xmlns:ws=\"http://ws.lab1.soa1.polytech.unice.fr/\">\n");
        builder.append("<contentType>ITEM</contentType>\n");
        builder.append("</ws:listContents>\n");
        return builder.toString();
    }


}
