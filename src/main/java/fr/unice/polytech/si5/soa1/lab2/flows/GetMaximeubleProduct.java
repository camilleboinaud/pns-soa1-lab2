package fr.unice.polytech.si5.soa1.lab2.flows;

import static fr.unice.polytech.si5.soa1.lab2.flows.utils.Endpoints.*;

import fr.unice.polytech.si5.soa1.lab2.flows.processors.maximeuble.Result2Product;
import org.apache.camel.builder.RouteBuilder;

/**
 * Created by lecpie on 10/26/15.
 */
public class GetMaximeubleProduct extends RouteBuilder {

    private static Result2Product res2product = new Result2Product();

    @Override
    public void configure() throws Exception {
        from(GET_MAXIMEUBLE_PRODUCT)
                //TODO call webservice to get data
                .process(res2product)
                ;
    }
}
