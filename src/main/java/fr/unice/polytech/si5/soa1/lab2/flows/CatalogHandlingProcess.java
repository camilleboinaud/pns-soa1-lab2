package fr.unice.polytech.si5.soa1.lab2.flows;

import fr.unice.polytech.si5.soa1.lab2.flows.processors.aggregationStrategy.CatalogListAggregationStrategy;
import fr.unice.polytech.si5.soa1.lab2.flows.processors.catalogue.ResultToListItemProcessor;
import fr.unice.polytech.si5.soa1.lab2.flows.processors.minibo.ItemTranslationProcessor;
import fr.unice.polytech.si5.soa1.lab2.flows.processors.translator.Item2CatalogItemTranslator;
import fr.unice.polytech.si5.soa1.lab2.flows.processors.translator.ItemList2CatalogItemListTranslator;
import fr.unice.polytech.si5.soa1.lab2.flows.request.CatalogueRequestBuilder;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

import static fr.unice.polytech.si5.soa1.lab2.flows.utils.Endpoints.*;

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
                .log("Bad ID for catalog item ${body.left}, ending here.")
        ;

        /**
         * Handle full catalog lists from all customers' services
         */
        from(HANDLE_FULL_CATALOG_LIST)
                .multicast()
                    .aggregationStrategy(new CatalogListAggregationStrategy())
                    .parallelProcessing()
                    .to(HANDLE_MINIBO_CATALOG_LIST)
                    .to(HANDLE_MAXIMEUBLE_CATALOG_LIST)
                .end()
        ;

        from(HANDLE_MINIBO_CATALOG_LIST)
                .setHeader("manufacturer", constant("MINIBO"))
                .bean(CatalogueRequestBuilder.class, "buildCatalogListRequest(${header.manufacturer})")
                .to(MINIBO_CATALOG_SERVICE)
                .process(ItemList2CatalogItemListTranslator.miniboItemList2CatalogListItem)
        ;

        from(HANDLE_MAXIMEUBLE_CATALOG_LIST)
                .setHeader("manufacturer", constant("MAXIMEUBLE"))
                .bean(CatalogueRequestBuilder.class, "buildCatalogListRequest(${header.manufacturer})")
                .to(MAXIMEUBLE_CATALOG_SERVICE)
                .process(ItemList2CatalogItemListTranslator.maximeubleItemList2CatalogListItem)
        ;

        from(HANDLE_MAXIMEUBLE_CATALOG_GET_ITEM)
                .setBody(simple("${body.right}"))
                .to(GET_MAXIMEUBLE_PRODUCT)
        ;

        /**
         * Flow used to get an item through Minibo services and
         * transform result into POJO.
         */
        from(HANDLE_MINIBO_CATALOG_GET_ITEM)
                .bean(CatalogueRequestBuilder.class, "buildCatalogGetItemRequest(${body.left},${body.right})")
                .to(MINIBO_CATALOG_SERVICE)
                .process(result2item)
        ;

    }

}
