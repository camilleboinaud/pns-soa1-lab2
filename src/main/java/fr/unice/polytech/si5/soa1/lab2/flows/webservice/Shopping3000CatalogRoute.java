package fr.unice.polytech.si5.soa1.lab2.flows.webservice;

import fr.unice.polytech.si5.soa1.lab2.flows.processors.catalogue.ReadPairIdProcessor;
import fr.unice.polytech.si5.soa1.lab2.flows.processors.translator.ListItem2Shopping3000CatalogItemListTranslator;
import org.apache.camel.builder.RouteBuilder;

import static fr.unice.polytech.si5.soa1.lab2.flows.utils.Endpoints.*;

/**
 * Created by Tianhao on 10/25/2015.
 */
public class Shopping3000CatalogRoute extends RouteBuilder {

    static ReadPairIdProcessor readPairIdProcessor = new ReadPairIdProcessor();

    @Override
    public void configure() throws Exception {

        // Retrieve a list
        from("direct:listItems")
                .to(HANDLE_FULL_CATALOG_LIST)
                .process(ListItem2Shopping3000CatalogItemListTranslator.list2CatalogListTranslator)
        ;

        from("direct:getItem")
                .setBody(simple("${body[0]}")) // get first parameter
                .process(readPairIdProcessor)
                .to(HANDLE_FULL_CATALOG_GET_ITEM)
                .setBody(simple("${body}"))
        ;

        from("direct:badCatalogRequest")
                .log("This is a bad request!")
        ;


        from("cxf:/Shopping3000CatalogService?serviceClass=fr.unice.polytech.si5.soa1.lab2.flows.webservice.Shopping3000CatalogService")
                .choice()
                .when(simple("${in.headers.operationName} == 'CatalogListAllItems'"))
                .to("direct:listItems")
                .when(simple("${in.headers.operationName} == 'CatalogGetItem'"))
                .to("direct:getItem")
                .otherwise()
                .to("direct:badCatalogRequest").stop()
                .endChoice()
        ;
    }


}
