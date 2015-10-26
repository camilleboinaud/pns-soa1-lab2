package fr.unice.polytech.si5.soa1.lab2.flows;

import static fr.unice.polytech.si5.soa1.lab2.flows.utils.Endpoints.*;

import fr.unice.polytech.si5.soa1.lab2.flows.processors.Csv2Order;
import fr.unice.polytech.si5.soa1.lab2.flows.processors.FilterCsvByAddress;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.CsvDataFormat;


public class HandleCSVFile extends RouteBuilder {

    private static Processor groupCsv = new FilterCsvByAddress();
    private static Processor csv2order = new Csv2Order();

    @Override
    public void configure() throws Exception {

        from(CSV_INPUT_DIRECTORY)
                .log("Processing ${file:name}")
                .log("  Loading the file as a CSV document")
                .unmarshal(buildCsvFormat())  // Body is now a List<Map <csvcolumn, value> >
                .log("  Grouping entries with same address")
                .process(groupCsv)
                .split(body())
                .log("  Transforming a CSV lines into Order")
                .process(csv2order)
                .log("Got order of ${body}")
                .to(HANDLE_FULL_ORDER)
        ;
    }

    // transform a CSV file delimited by commas, skipping the headers and producing a Map as output
    private static CsvDataFormat buildCsvFormat() {
        CsvDataFormat format = new CsvDataFormat();
        format.setDelimiter(",");
        format.setSkipHeaderRecord(true);
        format.setUseMaps(true);

        return format;
    }


}
