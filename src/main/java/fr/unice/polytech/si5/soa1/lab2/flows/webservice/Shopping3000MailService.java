package fr.unice.polytech.si5.soa1.lab2.flows.webservice;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

/**
 * Created by sth on 11/14/15.
 */
@WebService(serviceName = "Shopping3000MailService")
public interface Shopping3000MailService {
    @WebMethod(operationName = "SendEmail")
    @WebResult(name="emailStatus")
    Boolean sendEmail(@WebParam(name = "address")String address,
                      @WebParam(name = "message")String message);
}
