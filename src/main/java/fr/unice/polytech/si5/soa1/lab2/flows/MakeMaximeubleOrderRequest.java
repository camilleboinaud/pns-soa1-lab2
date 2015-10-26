package fr.unice.polytech.si5.soa1.lab2.flows;

import static fr.unice.polytech.si5.soa1.lab2.flows.utils.Endpoints.*;

import fr.unice.polytech.si5.soa1.lab2.flows.business.maximeuble.OrderItem;
import fr.unice.polytech.si5.soa1.lab2.flows.business.maximeuble.Product;
import fr.unice.polytech.si5.soa1.lab2.flows.processors.maximeuble.ProductListToOrderRequest;
import fr.unice.polytech.si5.soa1.lab2.flows.utils.Pair;
import fr.unice.polytech.si5.soa1.lab2.flows.utils.aggregators.ListAggregationStrategy;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

import java.util.List;

public class MakeMaximeubleOrderRequest extends RouteBuilder {

    private static Processor prodLst2ordrReq = new ProductListToOrderRequest();

    private static int nextId = 0;

    public static int getNextId() {
        return nextId++;
    }

    Processor addMeta = new Processor() {

        public void process(Exchange exchange) throws Exception {
            // retrieving the body of the exchanged message
            Product input = (Product) exchange.getIn().getBody();
            // transforming the input
            Pair<fr.unice.polytech.si5.soa1.lab2.flows.business.OrderItem, Integer> item = exchange.getIn().getHeader("item",
                    Pair.class);
            OrderItem output = builder(input, item);
            // Putting the output inside the body of the message
            exchange.getIn().setBody(output);
        }

        private OrderItem builder (Product input,  Pair<fr.unice.polytech.si5.soa1.lab2.flows.business.OrderItem, Integer> meta) {
            OrderItem orderItem = new OrderItem();

            orderItem.setProduct(input);
            orderItem.setQty(meta.getRight());
            orderItem.setOption(""); //TODO unmock

            return orderItem;
        }
    };

    @Override
    public void configure() throws Exception {
        from (MAKE_MAXIMEUBLE_ORDERREQUEST)
                .log("setting header : ${body}")
                .setHeader("nprod", simple("${body.items.size()}"))
                .setHeader("compute_id", constant(getNextId()))
                .log("make maximeuble order request")

                .split(simple("body.items"))
                .setHeader("item", body())
                .to(GET_MAXIMEUBLE_PRODUCT)
                .log("product : ${body}")
                .process(addMeta)
                .log("orderitem : ${body}")
                .aggregate(new ListAggregationStrategy()).header("compute_id")
                .completionSize(header("nprod"))
                .log("all products : ${body}")
                .process(prodLst2ordrReq)
                .log("route output : ${body}")
        ;

    }
}
