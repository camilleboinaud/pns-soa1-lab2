package fr.unice.polytech.si5.soa1.lab2.flows;

import fr.unice.polytech.si5.soa1.lab2.flows.business.OrderItem;
import fr.unice.polytech.si5.soa1.lab2.flows.business.shopping3000.Manufacturer;
import fr.unice.polytech.si5.soa1.lab2.flows.business.shopping3000.Shopping3000ID;
import fr.unice.polytech.si5.soa1.lab2.flows.processors.aggregationStrategy.CatalogListAggregationStrategy;
import fr.unice.polytech.si5.soa1.lab2.flows.processors.catalogue.ResultToListItemProcessor;
import fr.unice.polytech.si5.soa1.lab2.flows.processors.minibo.ItemTranslationProcessor;
import fr.unice.polytech.si5.soa1.lab2.flows.processors.translator.Item2CatalogItemTranslator;
import fr.unice.polytech.si5.soa1.lab2.flows.processors.translator.ItemList2CatalogItemListTranslator;
import fr.unice.polytech.si5.soa1.lab2.flows.request.CatalogueRequestBuilder;
import fr.unice.polytech.si5.soa1.lab2.flows.utils.Pair;
import fr.unice.polytech.si5.soa1.lab2.flows.utils.RequestBuilder;
import org.apache.camel.Exchange;
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

        from(HANDLE_MINIBO_CATALOG_LIST)
                .log("list all catalog in minibo")
                .setHeader("manufacturer", constant("MINIBO"))
                .bean(RequestBuilder.class, "buildCatalogListRequest(${header.manufacturer})")
                .to(MINIBO_CATALOG_SERVICE)
                .log("result get from minibo")
                .process(ItemList2CatalogItemListTranslator.miniboItemList2CatalogListItem)
        ;

        from(HANDLE_MAXIMEUBLE_CATALOG_LIST)
                .log("list all catalog in maximeuble")
                .setHeader("manufacturer", constant("MAXIMEUBLE"))
                .bean(RequestBuilder.class, "buildCatalogListRequest(${header.manufacturer})")
                .to(MAXIMEUBLE_CATALOG_SERVICE)
                .log("result get from maximeuble")
                .process(ItemList2CatalogItemListTranslator.maximeubleItemList2CatalogListItem)
        ;

        from(HANDLE_MAXIMEUBLE_CATALOG_GET_ITEM)
                .log("start getting maximeuble item")
                .process(createOrderItemByshopping3000ID)
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

    /**
     * create a orderItem by shopping 3000ID in order to get maximeuble product
     */
    private static Processor createOrderItemByshopping3000ID = new Processor() {

        public void process(Exchange exchange) throws Exception {
            Shopping3000ID id = exchange.getIn().getBody(Shopping3000ID.class);
            System.out.println(id.toString());
            OrderItem orderItem = new OrderItem(Manufacturer.MAXIMEUBLE,id.getId());
            exchange.getIn().setBody(new Pair<OrderItem,Integer>(orderItem,0));
        }
    };

}
