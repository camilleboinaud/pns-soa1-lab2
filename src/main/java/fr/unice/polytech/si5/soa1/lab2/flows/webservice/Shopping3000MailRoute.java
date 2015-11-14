package fr.unice.polytech.si5.soa1.lab2.flows.webservice;

import org.apache.camel.builder.RouteBuilder;
import static fr.unice.polytech.si5.soa1.lab2.flows.utils.Endpoints.*;
/**
 * Created by sth on 11/14/15.
 */
public class Shopping3000MailRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        from("activemq:sendEmail")
                .log("address:${body[0]}  message:${body[1]}")
                .setHeader("subject", constant("Shopping3000 Order"))
                .setHeader("to", simple("${body[0]}"))
                .setBody(simple("${body[1]}"))
                .to(SHOPPING3000_EMAIL_SERVICE)
                .log("after sending email")
                .setBody(simple("true", Boolean.class))
        ;




        from("cxf:/Shopping3000MailService?serviceClass=fr.unice.polytech.si5.soa1.lab2.flows.webservice.Shopping3000MailService")
                .filter(simple("${in.headers.operationName} == 'SendEmail'"))
                .to("activemq:sendEmail")
        ;
    }


}
