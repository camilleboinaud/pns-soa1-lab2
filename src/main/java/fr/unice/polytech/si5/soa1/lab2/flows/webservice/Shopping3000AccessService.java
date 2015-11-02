package fr.unice.polytech.si5.soa1.lab2.flows.webservice;

import fr.unice.polytech.si5.soa1.lab2.flows.business.CatalogItem;
import fr.unice.polytech.si5.soa1.lab2.flows.business.Manufacturer;
import fr.unice.polytech.si5.soa1.lab2.flows.utils.Pair;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Tianhao on 10/25/2015.
 */
@WebService(serviceName = "Shopping3000AccessService")
public interface Shopping3000AccessService {

    @WebMethod(operationName = "CatalogListAllItems")
    @WebResult(name="listItems")
    List listAllItems();

    @WebMethod(operationName = "CatalogGetItem")
    @WebResult(name="item")
    CatalogItem getItem(@WebParam(name = "Manufacturer")String manufacturer,
                        @WebParam(name = "ID")String id);
}
