package fr.unice.polytech.si5.soa1.lab2.flows.utils;


public class Endpoints {

    public static final String CSV_INPUT_DIRECTORY = "file:camel/input";

    public static final String CSV_OUTPUT_DIRECTORY = "file:camel/output";

    public static final String HANDLE_FULL_ORDER = "activemq:handleOrder";

    public static final String HANDLE_MINIBO_ORDER = "activemq:handleMiniboOrder";
    public static final String HANDLE_MAXIMEUBLE_ORDER = "activemq:handleMaxiMeubleOrder";

    public static final String GEN_SERVICE = "http://localhost:8181";


}
