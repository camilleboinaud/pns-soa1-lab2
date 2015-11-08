package fr.unice.polytech.si5.soa1.lab2.flows.processors.utils;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.*;

/**
 * Created by sth on 11/2/15.
 */
public class Translater {
    public static List<Object> table2List(Collection<Object> table){
        return new ArrayList<Object>(table);
    }

    public static Processor tranlaterTable2List = new Processor() {
        public void process(Exchange exchange) throws Exception {
            List<Object> table = exchange.getIn().getBody(List.class);
            System.out.println(table.size());
            exchange.getIn().setBody(table2List(table));
        }
    };
}
