package fr.unice.polytech.si5.soa1.lab2.flowcatalog;

import org.apache.camel.builder.RouteBuilder;

/**
 * Created by Tianhao on 10/25/2015.
 */
public class Shopping3000AccessRoute  extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("cxf:/Shopping3000AccessService?serviceClass=fr.unice.polytech.soa1.cookbook.flows.soap.TaxFormAccessService")
                .filter(simple("${in.headers.operationName} == 'CatalogListAllItem'"))
                .to("direct:listItem")
        ;
    }
}