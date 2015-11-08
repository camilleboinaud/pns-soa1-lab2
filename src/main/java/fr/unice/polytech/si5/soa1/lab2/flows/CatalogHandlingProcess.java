package fr.unice.polytech.si5.soa1.lab2.flows;

import fr.unice.polytech.si5.soa1.lab2.flows.processors.aggregationStrategy.CatalogListAggregationStrategy;
import fr.unice.polytech.si5.soa1.lab2.flows.processors.translator.Item2CatalogItemTranslator;
import org.apache.camel.builder.RouteBuilder;

import static fr.unice.polytech.si5.soa1.lab2.flows.utils.Endpoints.*;
import static fr.unice.polytech.si5.soa1.lab2.flows.utils.Endpoints.HANDLE_MAXIMEUBLE_CATALOG_LIST;
import static fr.unice.polytech.si5.soa1.lab2.flows.utils.Endpoints.HANDLE_MINIBO_CATALOG_LIST;

/**
 * Created by camille on 08/11/15.
 */
public class CatalogHandlingProcess extends RouteBuilder {

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
                        .process(Item2CatalogItemTranslator.miniboItem2CatalogItemTranslator)
                    .when(simple("${body.left} == ${type:fr.unice.polytech.si5.soa1.lab2.flows.business.shopping3000.Manufacturer.MAXIMEUBLE}"))
                        .to(HANDLE_MAXIMEUBLE_CATALOG_GET_ITEM)
                        .process(Item2CatalogItemTranslator.malleableItem2CatalogItemTranslator)
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
                .log("START multicast list item for each manufacturer")
                .multicast(new CatalogListAggregationStrategy())
                    .parallelProcessing()
                    .to(HANDLE_MINIBO_CATALOG_LIST)
                    .to(HANDLE_MAXIMEUBLE_CATALOG_LIST)
                .end()
                .log("END multicast list item for each manufacturer")
        ;

    }

}
