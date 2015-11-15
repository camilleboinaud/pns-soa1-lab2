package fr.unice.polytech.si5.soa1.lab2.flows.webservice;

import fr.unice.polytech.si5.soa1.lab2.flows.processors.bank.ValidCreditCard;
import org.apache.camel.builder.RouteBuilder;

/**
 * Created by sth on 11/9/15.
 */
public class Shopping3000BankRoute extends RouteBuilder {

    private static ValidCreditCard validCreditCard = new ValidCreditCard();
    @Override
    public void configure() throws Exception {

        from("direct:payByCreditCard")
            .log("Order payed with credit card : ${body[0]}")
            .process(validCreditCard)
        ;

        from("cxf:/Shopping3000BankService?serviceClass=fr.unice.polytech.si5.soa1.lab2.flows.webservice.Shopping3000BankService")
                .filter(simple("${in.headers.operationName} == 'PayByCreditCard'"))
                .to("direct:payByCreditCard")
        ;
    }
}
