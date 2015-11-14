package fr.unice.polytech.si5.soa1.lab2.flows.utils;

import fr.unice.polytech.si5.soa1.lab2.flows.business.Address;
import fr.unice.polytech.si5.soa1.lab2.flows.business.CreditCard;

/**
 * Created by camille on 14/11/15.
 */
public class Shopping3000Info {

    public static Address SHOPPING3000_ADDRESS = new Address(
            "shopping_3000_address",
            "shopping_3000_zipCode",
            "shopping_3000_city"
    );

    public static CreditCard SHOPPING3000_CREDIT_CARD = new CreditCard(
            "01234567890",
            "01/01/18",
            "123"
    );

}
