package fr.unice.polytech.si5.soa1.lab2.flows;

import fr.unice.polytech.si5.soa1.lab2.flows.processors.common.AddMetaIdProcessor;
import fr.unice.polytech.si5.soa1.lab2.flows.processors.csv.Csv2Order;
import fr.unice.polytech.si5.soa1.lab2.flows.processors.csv.FilterCsvByAddress;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.CsvDataFormat;

import static fr.unice.polytech.si5.soa1.lab2.flows.utils.Endpoints.CSV_INPUT_DIRECTORY;
import static fr.unice.polytech.si5.soa1.lab2.flows.utils.Endpoints.REGISTER_ORDER;
import static fr.unice.polytech.si5.soa1.lab2.flows.utils.Endpoints.SHOPPING_3000_PAYMENT;

/**
 * Created by camille on 08/11/15.
 */
public class CSVHandlingProcess extends RouteBuilder{

    private static Processor groupCsv = new FilterCsvByAddress();
    private static Processor csv2order = new Csv2Order();
    private static Processor addmetaid = new AddMetaIdProcessor();

    @Override
    public void configure() throws Exception {

        /**
         * This route is used to handle a csv file and tranform each
         * line into orders.
         */
        from(CSV_INPUT_DIRECTORY)
                .log("Processing ${file:name} as a CSV document")
                .unmarshal(buildCsvFormat())  // Body is now a List<Map <csvcolumn, value> >
                .log("Grouping entries with same address")
                .process(groupCsv)
                .split(body())
                .process(csv2order)
                .setProperty("order", simple("${body}"))
                .to(REGISTER_ORDER)
                .setHeader("shop3000_order_id", body())
                .setProperty("shop3000_order_id", body())
                .setBody(property("order"))
                .process(addmetaid)
                .log("registered order with id : ${headers.shop3000_order_id}")
                .to(SHOPPING_3000_PAYMENT)
        ;

    }

    /**
     * Transforms a CSV file delimited by commas, skipping headers and
     * producing a Map as output.
     */
    private static CsvDataFormat buildCsvFormat() {
        CsvDataFormat format = new CsvDataFormat();
        format.setDelimiter(",");
        format.setSkipHeaderRecord(true);
        format.setUseMaps(true);

        return format;
    }


}
