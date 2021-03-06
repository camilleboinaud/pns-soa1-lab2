package fr.unice.polytech.si5.soa1.lab2.flows.webservice;

import fr.unice.polytech.si5.soa1.lab2.flows.business.shopping3000.Manufacturer;
import fr.unice.polytech.si5.soa1.lab2.flows.business.shopping3000.Shopping3000CatalogItem;
import fr.unice.polytech.si5.soa1.lab2.flows.business.shopping3000.Shopping3000CatalogItemList;
import fr.unice.polytech.si5.soa1.lab2.flows.utils.Pair;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

/**
 * Created by Tianhao on 10/25/2015.
 */
@WebService(serviceName = "Shopping3000CatalogService")
public interface Shopping3000CatalogService {

    @WebMethod(operationName = "CatalogListAllItems")
    @WebResult(name="listItems")
    Shopping3000CatalogItemList listAllItems();

    @WebMethod(operationName = "CatalogGetItem")
    @WebResult(name="item")
    Shopping3000CatalogItem getItem(@WebParam(name = "Shopping3000ID") Pair<Manufacturer,Integer> id);
}
