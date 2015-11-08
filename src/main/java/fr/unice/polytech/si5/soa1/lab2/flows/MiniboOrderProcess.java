package fr.unice.polytech.si5.soa1.lab2.flows;

import fr.unice.polytech.si5.soa1.lab2.flows.processors.minibo.ItemTranslationProcessor;
import fr.unice.polytech.si5.soa1.lab2.flows.processors.common.OrderIdHandlingProcessor;
import fr.unice.polytech.si5.soa1.lab2.flows.utils.RequestBuilder;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

import static fr.unice.polytech.si5.soa1.lab2.flows.utils.Endpoints.*;
import static fr.unice.polytech.si5.soa1.lab2.flows.utils.Endpoints.MINIBO_ORDER_SERVICE;
import static fr.unice.polytech.si5.soa1.lab2.flows.utils.Endpoints.START_MINIBO_ORDER;

/**
 * Created by camille on 08/11/15.
 */
public class MiniboOrderProcess extends RouteBuilder {

    private static Processor result2item = new ItemTranslationProcessor();
    private static Processor res2id = new OrderIdHandlingProcessor();

    @Override
    public void configure() throws Exception {

        /**
         * Flow used to get an item through Minibo services and
         * transform result into POJO.
         */
        from(HANDLE_MINIBO_CATALOG_GET_ITEM)
                .log("get minibo item")
                .bean(RequestBuilder.class, "buildCatalogGetItemRequest(${body.left},${body.right})")
                .to(MINIBO_CATALOG_SERVICE)
                .log("result get item from minibo")
                .process(result2item)
        ;

        /**
         * Flow used to handle an order from Minibo's services.
         */
        from(HANDLE_MINIBO_ORDER)
                .log("minibo order handler...")
                .setProperty("order", body())
                .to(START_MINIBO_ORDER)
                .setProperty("order_id", body())
                        // mock minibo order result
                .setBody(property("order_id"))
                .log("minibo order made with id : ${body}")
        ;

        /**
         * Initializes order into Minibo's services and stores id returned.
         */
        from(START_MINIBO_ORDER)
                .bean(RequestBuilder.class, "buildStartMiniboOrder()")
                .to(MINIBO_ORDER_SERVICE)
                .process(res2id)
                .log("id: ${body}")
        ;

    }
}
