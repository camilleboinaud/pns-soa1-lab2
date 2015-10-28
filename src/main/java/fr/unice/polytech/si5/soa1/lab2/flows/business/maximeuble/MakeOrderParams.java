package fr.unice.polytech.si5.soa1.lab2.flows.business.maximeuble;

import fr.unice.polytech.si5.soa1.lab2.flows.utils.Pair;

import java.io.Serializable;

/**
 * Created by lecpie on 10/26/15.
 */
public class MakeOrderParams extends Pair<Client, OrderRequest> implements Serializable {

    public Client       getClient()       { return getLeft();  }
    public OrderRequest getOrderRequest() { return getRight(); }

    public void setClient       (Client client)             { setLeft  (client);       }
    public void setOrderRequest (OrderRequest orderRequest) { setRight (orderRequest); }
}
