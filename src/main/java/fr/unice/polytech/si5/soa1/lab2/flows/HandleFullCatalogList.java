package fr.unice.polytech.si5.soa1.lab2.flows;

import org.apache.camel.builder.RouteBuilder;

import static fr.unice.polytech.si5.soa1.lab2.flows.utils.Endpoints.*;

/**
 * Created by Tianhao on 10/25/2015.
 */
public class HandleFullCatalogList extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from(HANDLE_FULL_CATALOG_LIST)
                .log("multicast list for each manufacturer")
                .multicast()
                    .parallelProcessing()
                    .to(HANDLE_MINIBO_CATALOG_LIST)
//                    .to("mock:maximeuble")
                    .to(HANDLE_MAXIMEUBLE_CATALOG_LIST)
        ;
    }
}
