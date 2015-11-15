package fr.unice.polytech.si5.soa1.lab2.flows.processors.bank;

import fr.unice.polytech.si5.soa1.lab2.flows.business.CreditCard;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 * Created by sth on 11/14/15.
 */
public class ValidCreditCard implements Processor {

    public void process(Exchange exchange) throws Exception {
        CreditCard creditCard = exchange.getIn().getBody(CreditCard.class);

        int number = Integer.parseInt(creditCard.getNumber());
        int csc = Integer.parseInt(creditCard.getCsc());

        if (creditCard.getValidity()!=null){
            if ((number%2)==0 && ((csc%2) == 0)){
                exchange.getIn().setBody(true);
                return;
            }
        }
        exchange.getIn().setBody(false);
    }
}
