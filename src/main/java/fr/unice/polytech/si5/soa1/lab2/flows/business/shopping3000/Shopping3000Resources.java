package fr.unice.polytech.si5.soa1.lab2.flows.business.shopping3000;

import fr.unice.polytech.si5.soa1.lab2.flows.business.Address;
import fr.unice.polytech.si5.soa1.lab2.flows.business.CreditCard;
import fr.unice.polytech.si5.soa1.lab2.flows.business.Customer;

/**
 * Created by camille on 09/11/15.
 */
public class Shopping3000Resources {

    public static Customer shopping3000Data = new Customer(
            "SHOPPING_3000",
            new Address("super_address", "super_zip_code", "super_city"),
            "super@email.com",
            new CreditCard("01234567890", "31/12/2015", "123")
    );

}
