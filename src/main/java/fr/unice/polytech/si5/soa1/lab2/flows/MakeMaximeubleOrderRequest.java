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
import org.apache.camel.component.jms.JmsMessage;
import org.apache.camel.processor.aggregate.GroupedExchangeAggregationStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MakeMaximeubleOrderRequest extends RouteBuilder {

    private static Processor prodLst2ordrReq = new ProductListToOrderRequest();

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

    private static final Processor exclst2ordritmlst = new Processor() {
        public void process(Exchange exchange) throws Exception {
            List<Exchange> in = exchange.getIn().getBody(List.class);

            List<OrderItem> itms = new ArrayList<OrderItem>();

            for (Exchange exc : in) {
                itms.add(exc.getIn(JmsMessage.class).getBody(OrderItem.class));
            }

            exchange.getIn().setBody(itms);
        }
    };

    @Override
    public void configure() throws Exception {
        from(MAKE_MAXIMEUBLE_ORDERREQUEST)
                .log("make maximeuble order request")
                .split(simple("body.items"))
                .aggregationStrategy(new GroupedExchangeAggregationStrategy())
                    .setHeader("item", body())
                    .to(GET_MAXIMEUBLE_PRODUCT)
                    .log("product : ${body}")
                    .process(addMeta)
                    .log("orderitem : ${body}")
                .end()
                .process(exclst2ordritmlst)
                .log("all products : ${body}")
                .process(prodLst2ordrReq)
                .log("route output : ${body}")
        ;

    }
}
