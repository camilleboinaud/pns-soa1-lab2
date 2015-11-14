package fr.unice.polytech.si5.soa1.lab2.flows.webservice;

import fr.unice.polytech.si5.soa1.lab2.flows.business.CreditCard;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

/**
 * Created by sth on 11/9/15.
 */
@WebService(serviceName = "Shopping3000BankService")
public interface Shopping3000BankService {
    @WebMethod(operationName = "PayByCreditCard")
    @WebResult(name="paymentResult")
    boolean payByCreditCard(@WebParam(name = "CreditCard")CreditCard creditCard,
                            @WebParam(name = "Amount")int amount);
}
