package fr.unice.polytech.si5.soa1.lab2.flows;

import fr.unice.polytech.si5.soa1.lab2.flows.business.maximeuble.OrderItem;
import fr.unice.polytech.si5.soa1.lab2.flows.business.shopping3000.Manufacturer;
import fr.unice.polytech.si5.soa1.lab2.flows.processors.maximeuble.*;
import fr.unice.polytech.si5.soa1.lab2.flows.processors.utils.ExchangeListToTemplateListProcessor;
import fr.unice.polytech.si5.soa1.lab2.flows.processors.common.IDEnhancementProcessor;
import fr.unice.polytech.si5.soa1.lab2.flows.processors.common.OrderIdHandlingProcessor;
import fr.unice.polytech.si5.soa1.lab2.flows.request.MaximeulbleOrderRequestBuilder;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.processor.aggregate.GroupedExchangeAggregationStrategy;

import static fr.unice.polytech.si5.soa1.lab2.flows.utils.Endpoints.*;
import static fr.unice.polytech.si5.soa1.lab2.flows.utils.Endpoints.MAKE_MAXIMEUBLE_ORDER;
import static fr.unice.polytech.si5.soa1.lab2.flows.utils.Endpoints.MAKE_MAXIMEUBLE_ORDERREQUEST;

/**
 * Created by camille on 08/11/15.
 */
public class MaximeubleOrderProcess extends RouteBuilder {

    private static Processor res2product = new ProductTranslationProcessor();
    private static Processor joinParams = new OrderParameterBuildingProcessor();
    private static Processor data2client = new CustomerBuildingProcessor();
    private static Processor res2id = new OrderIdHandlingProcessor();
    private static Processor addMeta = new IDEnhancementProcessor();
    private static Processor product2orderitem = new ProductToOrderItemProcessor();
    private static Processor exclst2ordritmlst =
            new ExchangeListToTemplateListProcessor<OrderItem>();
    private static Processor prodLst2ordrReq = new ProductListToOrderRequest();

    @Override
    public void configure() throws Exception {

        /**
         * Gets a product from Maximeuble's services and transform
         * it into POJO.
         */
        from(GET_MAXIMEUBLE_PRODUCT)
                .log("building product request for id : ${body}")
                .bean(MaximeulbleOrderRequestBuilder.class, "buildMaximeubleProductRequest(${body})")
                .to(MAXIMEUBLE_CATALOG_SERVICE)
                .process(res2product)
                .log("got product : ${body}")
        ;

        /**
         * Orchestrates order processing for Maximeuble customer
         */
        from(HANDLE_MAXIMEUBLE_ORDER)
                .log("maximeuble order handler...")
                .setHeader("input_order", body())
                .multicast(new GroupedExchangeAggregationStrategy())
                    .parallelProcessing()
                    .to(MAKE_MAXIMEUBLE_CLIENT)
                    .to(MAKE_MAXIMEUBLE_ORDERREQUEST)
                .end()
                .process(joinParams)
                .log("multicast output : ${body}")
                .to(MAKE_MAXIMEUBLE_ORDER)
                .log("Maximeuble order made with id : ${body}")
        ;

        /**
         * Flow used to build a customer for Maximeuble services
         */
        from (MAKE_MAXIMEUBLE_CLIENT)
                .process(data2client)
                .log("route output : ${body}")
        ;

        /**
         * Build an order
         */
        from (MAKE_MAXIMEUBLE_ORDER)
                .log("make order : ${body}")
                .bean(MaximeulbleOrderRequestBuilder.class, "buildMaximeubleMakeOrderRequest(${body})")
                .log("request : ${body}")
                .to(MAXIMEUBLE_ORDER_SERVICE)
                .log("order service result : ${body}")
                .process(res2id)
        ;

        /**
         * Flaw used to build an order request
         */
        from(MAKE_MAXIMEUBLE_ORDERREQUEST)
                .log("make maximeuble order request")
                .split(simple("body.items"))
                    .aggregationStrategy(new GroupedExchangeAggregationStrategy())
                    .setHeader("item", body())
                    .setBody(simple("${body.left.manufacturerId}"))
                    .to(GET_MAXIMEUBLE_PRODUCT)
                    .log("product : ${body}")
                    .process(product2orderitem)
                    .log("orderitem : ${body}")
                .end()
                .log("split output : ${body}")
                .setHeader("manufacturer", constant(Manufacturer.MAXIMEUBLE))
                .process(exclst2ordritmlst)
                .log("all products : ${body}")
                .process(prodLst2ordrReq)
                .log("route output : ${body}")
        ;

    }

}
