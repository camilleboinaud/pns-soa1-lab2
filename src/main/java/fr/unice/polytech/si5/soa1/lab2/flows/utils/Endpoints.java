package fr.unice.polytech.si5.soa1.lab2.flows.utils;


public class Endpoints {

    public static final String GEN_SERVICE = "http://localhost:8181/";

    public static final String CSV_INPUT_DIRECTORY = "file:camel/input";

    public static final String CSV_OUTPUT_DIRECTORY = "file:camel/output";

    public static final String HANDLE_FULL_ORDER = "activemq:handleOrder";
    public static final String HANDLE_MINIBO_ORDER = "direct:handleMiniboOrder";
    public static final String HANDLE_MAXIMEUBLE_ORDER = "direct:handleMaxiMeubleOrder";
    public static final String HANDLE_MANUFACTURING_PROCESS_ACHIEVED = "activemq:handleManufacturingProcessAchieved";

    public static final String HANDLE_FULL_CATALOG_LIST = "direct:handleCatalogList";
    public static final String HANDLE_MINIBO_CATALOG_LIST = "direct:handleMiniboCatalogList";
    public static final String HANDLE_MAXIMEUBLE_CATALOG_LIST = "direct:handleMaxiMeubleCatalogList";

    public static final String HANDLE_FULL_CATALOG_GET_ITEM = "direct:handleCatalogGetItem";
    public static final String HANDLE_MINIBO_CATALOG_GET_ITEM = "direct:handleMiniboCatalogGetItem";
    public static final String HANDLE_MAXIMEUBLE_CATALOG_GET_ITEM = "direct:handleMaxiMeubleCatalogGetItem";

    public static final String MINIBO_CATALOG_SERVICE = "spring-ws://" + GEN_SERVICE + "cxf/BusinessManagementService";
    public static final String MAXIMEUBLE_CATALOG_SERVICE = "spring-ws://" + GEN_SERVICE + "cxf/CatalogServiceImpl";

    public static final String MAKE_MAXIMEUBLE_ORDERREQUEST = "direct:makeMaximeubleOrderRequest";
    public static final String MAKE_MAXIMEUBLE_CLIENT = "direct:makeMaximeubleClient";
    public static final String GET_MAXIMEUBLE_PRODUCT = "direct:getMaximeubleProduct";
    public static final String MAKE_MAXIMEUBLE_ORDER = "direct:makeMaximeubleOrder";

    public static final String MAXIMEUBLE_CATALOG_SERVICE = GEN_SERVICE + "cxf/CatalogServiceImpl";
}
