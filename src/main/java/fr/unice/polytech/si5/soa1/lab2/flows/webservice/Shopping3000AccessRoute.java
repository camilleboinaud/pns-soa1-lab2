package fr.unice.polytech.si5.soa1.lab2.flows.webservice;

import fr.unice.polytech.si5.soa1.lab2.flows.business.CatalogItem;
import org.apache.camel.builder.RouteBuilder;

import static fr.unice.polytech.si5.soa1.lab2.flows.utils.Endpoints.*;

/**
 * Created by Tianhao on 10/25/2015.
 */
public class Shopping3000AccessRoute  extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        // Retrieve a list
        from("direct:listItems")
                .log("listItem with ${body}")
                .to(HANDLE_FULL_CATALOG_LIST)
                .log("all list done ${body}")
//                .split(body(CatalogItem.class))
//                .log("split ${body}")
                .setBody(simple("${body}"))
        ;

        from("direct:getItem")
                .log("getItem with ${body}")
                .to(HANDLE_FULL_CATALOG_GET_ITEM)
                .setBody(simple("${body}"))
        ;

        from("direct:badRequest")
                .log("This is a bad request!")
        ;


        from("cxf:/Shopping3000AccessService?serviceClass=fr.unice.polytech.si5.soa1.lab2.flows.webservice.Shopping3000AccessService")
                .choice()
                    .when(simple("${in.headers.operationName} == 'CatalogListAllItems'"))
                        .log("CatalogListAllItems")
                        .to("direct:listItems")
                    .when(simple("${in.headers.operationName} == 'CatalogGetItem'"))
                        .log("CatalogGetItem")
                        .to("direct:getItem")
                    .otherwise()
                        .to("direct:badRequest").stop()
                .endChoice()
        ;
    }
}
