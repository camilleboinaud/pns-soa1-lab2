package fr.unice.polytech.si5.soa1.lab2.flows.processors;

import fr.unice.polytech.si5.soa1.lab2.flows.business.Order;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.*;


public class GroupCsv implements Processor {
    public void process(Exchange exchange) throws Exception {
        // retrieving the body of the exchanged message
        List <Map<String, String> > input = (List <Map<String, String> >) exchange.getIn().getBody();
        // transforming the input into a person
        Collection <List <Map<String, String>>> output =  builder(input);
        // Putting the person inside the body of the message
        exchange.getIn().setBody(output);
    }

    private Collection <List <Map<String, String>>> builder(List <Map<String, String> > data) {

        Map <String, List <Map<String, String>>> orders = new HashMap<String, List<Map<String, String>>>();

        for (Map <String, String> item : data) {
            String key = item.get("address");

            if (! orders.containsKey(key)) {
                orders.put(key, new ArrayList<Map <String, String> >());
            }

            orders.get(key).add(item);

        }

        return orders.values();

            /* Keep example until data classes are implemented

            // name
            String name =  (String) data.get("Navn");
            o.setFirstName((name.split(",")[1].trim()));
            o.setLastName((name.split(",")[0].trim()));
            // zip code
            o.setZipCode(Integer.parseInt((String) data.get("Postnummer")));
            // address
            o.setAddress((String) data.get("Postaddressen"));
            // email
            o.setEmail((String) data.get("Epost"));
            // Unique identifier
            o.setUid((String) data.get("Fodselsnummer"));
            // Money
            o.setIncome(getMoneyValue(data, "Inntekt"));
            o.setAssets(getMoneyValue(data, "Formue"));
            */

        //return o;
    }
}
