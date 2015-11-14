package fr.unice.polytech.si5.soa1.lab2.flows.webservice;

import fr.unice.polytech.si5.soa1.lab2.flows.business.Customer;
import fr.unice.polytech.si5.soa1.lab2.flows.business.Order;
import fr.unice.polytech.si5.soa1.lab2.flows.business.OrderItem;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

/**
 * Created by lecpie on 11/9/15.
 */
@WebService(serviceName = "Shopping3000OrderService")
public interface Shopping3000OrderService {

    @WebMethod(operationName = "start_order")
    @WebResult(name="order_id")
    int startOrder();

    @WebMethod(operationName = "add_order_item")
    void addOrderItem (@WebParam(name = "order_id")   int orderId,
                       @WebParam(name = "order_item") OrderItem orderItem,
                       @WebParam(name = "qty")        int qty);

    @WebMethod(operationName = "set_customer")
    void setCustomer (@WebParam(name = "order_id") int orderId,
                      @WebParam(name = "customer") Customer customer);

    @WebMethod(operationName = "get_amount")
    @WebResult(name = "amount")
    double getAmount (@WebParam(name = "order_id") int orderId);

    @WebMethod(operationName = "validate_order")
    @WebResult(name="success")
    boolean validateOrder (@WebParam(name = "order_id") int orderId);

    @WebMethod(operationName = "get_order")
    @WebResult(name="order")
    Order getOrder(@WebParam(name = "order_id") int orderId);

    @WebMethod(operationName = "register_order")
    @WebResult(name = "order_id")
    int registerOrder(@WebParam(name = "order") Order order);
}
