package fr.unice.polytech.si5.soa1.lab2.flows.processors.utils;

import fr.unice.polytech.si5.soa1.lab2.flows.business.maximeuble.OrderItem;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.jms.JmsMessage;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by camille on 08/11/15.
 */
public class ExchangeListToTemplateListProcessor<T> implements Processor {

    final Class<T> parameterType;

    public ExchangeListToTemplateListProcessor(){
        parameterType = (Class<T>) ((T) getClass().getGenericSuperclass());
    }

    public void process(Exchange exchange) throws Exception {
        List<Exchange> in = exchange.getIn().getBody(List.class);

        List<T> itms = new ArrayList<T>();

        for (Exchange exc : in) {
            itms.add(exc.getIn().getBody(parameterType));
        }

        exchange.getIn().setBody(itms);
    }
}
