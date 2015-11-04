package fr.unice.polytech.si5.soa1.lab2.flows;

import org.apache.camel.builder.RouteBuilder;
import static fr.unice.polytech.si5.soa1.lab2.flows.utils.Endpoints.*;
/**
 * Created by sth on 11/1/15.
 */
public class HandleFullCatalogGetItem extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from(HANDLE_FULL_CATALOG_GET_ITEM)
                .log("choise item ${body}")
                .choice()
                    .when(simple("${body.left} == ${type:fr.unice.polytech.si5.soa1.lab2.flows.business.shopping3000.Manufacturer.MINIBO}"))
                        .to(HANDLE_MINIBO_CATALOG_GET_ITEM)
                    .when(simple("${body.left} == ${type:fr.unice.polytech.si5.soa1.lab2.flows.business.shopping3000.Manufacturer.MAXIMEUBLE}"))
                        .to(HANDLE_MAXIMEUBLE_CATALOG_GET_ITEM)
                    .otherwise()
                        .to("direct:badId").stop()
                .endChoice()
        ;
        // bad information about a given citizen
        from("direct:badId")
                .log("    Bad ID for catalog item ${body.left}, ending here.")
        ;
    }
}
