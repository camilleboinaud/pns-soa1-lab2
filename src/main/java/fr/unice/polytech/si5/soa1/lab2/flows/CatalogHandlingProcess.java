package fr.unice.polytech.si5.soa1.lab2.flows;

import fr.unice.polytech.si5.soa1.lab2.flows.processors.catalogue.ResultToListItemProcessor;
import fr.unice.polytech.si5.soa1.lab2.flows.processors.minibo.ItemTranslationProcessor;
import fr.unice.polytech.si5.soa1.lab2.flows.request.CatalogueRequestBuilder;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

import static fr.unice.polytech.si5.soa1.lab2.flows.utils.Endpoints.*;
import static fr.unice.polytech.si5.soa1.lab2.flows.utils.Endpoints.HANDLE_MAXIMEUBLE_CATALOG_LIST;
import static fr.unice.polytech.si5.soa1.lab2.flows.utils.Endpoints.HANDLE_MINIBO_CATALOG_LIST;

/**
 * Created by camille on 08/11/15.
 */
public class CatalogHandlingProcess extends RouteBuilder {

    private static Processor result2listItem = new ResultToListItemProcessor();
    private static Processor result2item = new ItemTranslationProcessor();

    @Override
    public void configure() throws Exception {

        /**
         * Flow used to get full catalog item from the specified
         * customer's services.
         */
        from(HANDLE_FULL_CATALOG_GET_ITEM)
                .log("choice item ${body}")
                .choice()
                    .when(simple("${body.left} == ${type:fr.unice.polytech.si5.soa1.lab2.flows.business.shopping3000.Manufacturer.MINIBO}"))
                        .to(HANDLE_MINIBO_CATALOG_GET_ITEM)
                    .when(simple("${body.left} == ${type:fr.unice.polytech.si5.soa1.lab2.flows.business.shopping3000.Manufacturer.MAXIMEUBLE}"))
                        .to(HANDLE_MAXIMEUBLE_CATALOG_GET_ITEM)
                    .otherwise()
                        .to("direct:badId")
                        .stop()
                .endChoice()
        ;

        /**
         * Flow used to end process if a bad ID were provided
         * to customer's services.
         */
        from("direct:badId")
                .log("    Bad ID for catalog item ${body.left}, ending here.")
        ;

        /**
         * Handle full catalog lists from all customers' services
         */
        from(HANDLE_FULL_CATALOG_LIST)
                .log("multicast list for each manufacturer")
                .multicast()
                .parallelProcessing()
                .to(HANDLE_MINIBO_CATALOG_LIST)
                .to(HANDLE_MAXIMEUBLE_CATALOG_LIST)
        ;

        from(HANDLE_MINIBO_CATALOG_LIST)
                .log("list all catalog in minibo")
                .bean(CatalogueRequestBuilder.class, "buildCatalogListRequest()")
                .to(MINIBO_CATALOG_SERVICE)
                .log("result get from minibo")
                .process(result2listItem)
        ;

        from(HANDLE_MAXIMEUBLE_CATALOG_LIST)
                .log("list all catalog in maximeuble")
                .bean(CatalogueRequestBuilder.class, "buildCatalogListRequest()")
                .to(MINIBO_CATALOG_SERVICE)
                .log("result get from maximeuble")
                .process(result2listItem)
        ;

        from(HANDLE_MAXIMEUBLE_CATALOG_GET_ITEM)
                .log("get maximeuble item")
                .to(GET_MAXIMEUBLE_PRODUCT)
        ;

        /**
         * Flow used to get an item through Minibo services and
         * transform result into POJO.
         */
        from(HANDLE_MINIBO_CATALOG_GET_ITEM)
                .log("get minibo item")
                .bean(CatalogueRequestBuilder.class, "buildCatalogGetItemRequest(${body.left},${body.right})")
                .to(MINIBO_CATALOG_SERVICE)
                .log("result get item from minibo")
                .process(result2item)
        ;

    }

}
