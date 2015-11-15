package fr.unice.polytech.si5.soa1.lab2.flows;

import fr.unice.polytech.si5.soa1.lab2.flows.processors.common.OrderIdHandlingProcessor;
import fr.unice.polytech.si5.soa1.lab2.flows.processors.minibo.ExchangePairToItemIdListProcessor;
import fr.unice.polytech.si5.soa1.lab2.flows.request.MiniboOrderRequestBuilder;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.processor.aggregate.GroupedExchangeAggregationStrategy;

import static fr.unice.polytech.si5.soa1.lab2.flows.utils.Endpoints.*;
import static fr.unice.polytech.si5.soa1.lab2.flows.utils.Endpoints.MINIBO_ORDER_SERVICE;
import static fr.unice.polytech.si5.soa1.lab2.flows.utils.Endpoints.START_MINIBO_ORDER;

/**
 * Created by camille on 08/11/15.
 */
public class MiniboOrderProcess extends RouteBuilder {

    private static Processor res2id = new OrderIdHandlingProcessor();
    private static Processor exclst2ordritmlst = new ExchangePairToItemIdListProcessor();

    @Override
    public void configure() throws Exception {

        /**
         * Flow used to handle an order from Minibo's services.
         */
        from(HANDLE_MINIBO_ORDER)
                .log("[ORDER n°${exchangeProperty.shop3000_order_id}] Handling minibo order")
                .setProperty("order", body())
                .to(START_MINIBO_ORDER)
                .setProperty("order_id", body())
                .setBody(property("order"))
                .split(simple("body.items"))
                    .setHeader("item", body())
                    .setHeader("order_id", property("order_id"))
                    .to(MINIBO_ADD_ITEM_TO_ORDER)
                .end()
                .bean(MiniboOrderRequestBuilder.class, "buildSetCustomerMiniboOrder("
                        + "${header.order_id},"
                        + "${exchangeProperty.order.customer})"
                )
                .to(MINIBO_DELIVERY_SERVICE)
                .process(res2id)
                .log("[ORDER n°${exchangeProperty.shop3000_order_id}] Customer added to order n°${body}")
        ;


        /**
         * Flow used to add an item to order using Minibo's services
         */
        from(MINIBO_ADD_ITEM_TO_ORDER)
                .setProperty("item", body())
                .bean(MiniboOrderRequestBuilder.class, "buildAddItemMiniboOrder("
                                + "${header[order_id]},"
                                + "${body.left.manufacturerId},"
                                + "${body.right})"
                )
                .to(MINIBO_ORDER_SERVICE)
                .process(res2id)
                .log("[ORDER n°${exchangeProperty.shop3000_order_id}] Item n°${exchangeProperty.item.left.manufacturerId} has been added to order n°${body}")
        ;


        /**
         * Initializes order into Minibo's services and stores id returned.
         */
        from(START_MINIBO_ORDER)
                .bean(MiniboOrderRequestBuilder.class, "buildStartMiniboOrder()")
                .to(MINIBO_ORDER_SERVICE)
                .process(res2id)
                .log("[ORDER n°${exchangeProperty.shop3000_order_id}] Order n°id: ${body} has been created successfully")
        ;

    }
}
