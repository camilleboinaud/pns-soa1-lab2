package fr.unice.polytech.si5.soa1.lab2.flows.webservice;

import fr.unice.polytech.si5.soa1.lab2.flows.utils.Pair;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

/**
 * Created by sth on 11/9/15.
 */
@WebService(serviceName = "Shopping3000DeliveryService")
public interface Shopping3000DeliveryService {

    @WebMethod(operationName = "CalculateDeliveryPriceByAddresses")
    @WebResult(name="deliveryPrice")
    Double calculateDeliveryPriceByAddresses(@WebParam(name = "addresses")Pair<String,String> addresses);

}
