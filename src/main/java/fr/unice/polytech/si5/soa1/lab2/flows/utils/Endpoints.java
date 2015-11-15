package fr.unice.polytech.si5.soa1.lab2.flows.utils;


public class Endpoints {

    public static final String GEN_SERVICE = "http://localhost:8181/";

    public static final String CSV_INPUT_DIRECTORY = "file:camel/input";

    public static final String SHOPPING_3000_PAYMENT = "activemq:shopping3000Payment?transferExchange=true";

    public static final String HANDLE_FULL_ORDER = "activemq:handleOrder?transferExchange=true";
    public static final String HANDLE_MINIBO_ORDER = "direct:handleMiniboOrder";
    public static final String HANDLE_MAXIMEUBLE_ORDER = "direct:handleMaxiMeubleOrder";

    public static final String HANDLE_FULL_CATALOG_LIST = "direct:handleCatalogList";
    public static final String HANDLE_MINIBO_CATALOG_LIST = "direct:handleMiniboCatalogList";
    public static final String HANDLE_MAXIMEUBLE_CATALOG_LIST = "direct:handleMaxiMeubleCatalogList";

    public static final String HANDLE_FULL_CATALOG_GET_ITEM = "direct:handleCatalogGetItem";
    public static final String HANDLE_MINIBO_CATALOG_GET_ITEM = "direct:handleMiniboCatalogGetItem";
    public static final String HANDLE_MAXIMEUBLE_CATALOG_GET_ITEM = "direct:handleMaxiMeubleCatalogGetItem";

    public static final String MINIBO_CATALOG_SERVICE = "spring-ws://" + GEN_SERVICE + "cxf/BusinessManagementService";
    public static final String MINIBO_ORDER_SERVICE = "spring-ws://" + GEN_SERVICE + "cxf/OrderService";
    public static final String MINIBO_DELIVERY_SERVICE = "spring-ws://" + GEN_SERVICE + "cxf/DeliveryService";
    public static final String MINIBO_PAYMENT_SERVICE = "spring-ws://" + GEN_SERVICE + "cxf/PaymentService";

    public static final String MAXIMEUBLE_CATALOG_SERVICE = "spring-ws://" + GEN_SERVICE + "cxf/CatalogServiceImpl";
    public static final String MAXIMEUBLE_ORDER_SERVICE = "spring-ws://" + GEN_SERVICE + "cxf/OrderServiceImpl";
    public static final String MAXIMEUBLE_BILLING_SERVICE = "spring-ws://" + GEN_SERVICE + "cxf/BillingServiceImpl";

    public static final String MAKE_MAXIMEUBLE_ORDERREQUEST = "direct:makeMaximeubleOrderRequest";
    public static final String MAKE_MAXIMEUBLE_CLIENT = "direct:makeMaximeubleClient";
    public static final String GET_MAXIMEUBLE_PRODUCT = "direct:getMaximeubleProduct";
    public static final String MAKE_MAXIMEUBLE_ORDER = "direct:makeMaximeubleOrder";
    public static final String MAXIMEUBLE_ORDER_PAYMENT = "activemq:maximeubleOrderPayment?transferExchange=true";

    public static final String START_MINIBO_ORDER = "direct:startMiniboOrder";
    public static final String MINIBO_ADD_ITEM_TO_ORDER = "activemq:miniboAddItemToOrder?transferExchange=true";
    public static final String MINIBO_ORDER_PAYMENT = "activemq:miniboOrderPayment?transferExchange=true";

    public static final String PAY_ORDER_TO_MANUFACTURER = "activemq:payOrderToManufacturer?transferExchange=true";

    public static final String SHOPPING_3000_EMAIL_SERVICE = "smtp://smtp.googlemail.com:587?username=soa.shopping3000@gmail.com&password=polytech&mail.smtp.auth=true&mail.smtp.starttls.enable=true";
    public static final String SHOPPING_3000_BANK_SERVICE = "spring-ws://" + GEN_SERVICE + "cxf/Shopping3000BankService";
    public static final String SHOPPING_3000_DELIVERY_SERVICE = "spring-ws://" + GEN_SERVICE + "cxf/Shopping3000DeliveryService";

    public static final String SEND_CONFIRMATION_EMAIL = "activemq:send_confirmation_email?transferExchange=true";

    public static final String ORDER_SERVICE_INTERCEPTOR = "cxf:/Shopping3000OrderService?serviceClass=fr.unice.polytech.si5.soa1.lab2.flows.webservice.Shopping3000OrderService";

    public static final String START_ORDER = "direct:start_order";
    public static final String ADD_ORDER_ITEM = "direct:add_order_item";
    public static final String SET_CUSTOMER = "direct:set_customer";
    public static final String VALIDATE_ORDER = "direct:validate_order";
    public static final String GET_AMOUNT = "direct:get_amount";
    public static final String GET_ORDER = "direct:get_order";
    public static final String REGISTER_ORDER = "direct:register_order";

}
