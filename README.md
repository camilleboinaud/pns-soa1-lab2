# Integration Service Lab

> This _README_ file aims to introduce functionnalities offered and the way to use them (especially for B2B usage).

<br>

This project is about integrating two virtual shopping web services to design a new virtual shopping mal called Shopping 3000. This virtual mall needs to take care about two types of customers : 

* __individuals :__ which makes orders using a specific web service
* __brands :__ which would like to order a large quantity of products using a CSV file.   

<br>

## 1.  Prerequisite using servicemix

Service mix needs some specific features to be installed (due to Camel usage) in order to run Shopping 3000 services :

* activemq-camel
* xml-specs-api
* camel
* camel-core
* camel-spring
* camel-blueprint
* camel-csv
* camel-cxf
* camel-http
* camel-jms
* camel-saxon
* camel-servlet
* camel-spring-ws
* camel-mail

In order to use services, you must build projects using `mvn package` command (for each services: minibo, maximeuble and shopping 3000). Then you need to copy your .jar files into `{servicemix_path}/deploy`. 
 
<br> 

## 2.  B2B user guide : 

In order to use B2B Shopping 3000 functionnalities, you need to build a CSV file matching the following exemple : 

```
name,email,address,zipcode,city,creditcard,expiration,csc,manufacturer,itemid,quantity
homer simpson,homer@springfield.com,742 Evergreen Terrace,89444,Springfield,5784365343410708,12/16,120,MINIBO,1,25 
homer simpson,homer@springfield.com,742 Evergreen Terrace,89444,Springfield,5784365343410708,12/16,120,MAXIMEUBLE,2,1 
```
You need then to move this file into service mix: `{servicemix_path}/camel/input`

> Filling a known email address in CSV file will allow you to get a feedback status concerning your order in case of success.

<br>

## 3.  B2C user guide : 

This part will sum up features offered through public Shopping 3000 web service. This services has been developped following a SOAP design.

#### 3.1.  Shopping 3000 order service :

Description of ordering service exposed by Shopping 3000 :

| Method        | Parameters                                                   | Return    | Specificity                                           |  
| --------------| -------------------------------------------------------------| ----------| --------------------------------------------------------|  
| startOrder    |                                                              | `int`     | Required as first operation                             |  
| addOrderItem  | `(int) order_id`, `(OrderItem) order_item`, `(int) quantity` | `void`    |                                                         |  
| setCustomer   | `(int) order_id`, `(Customer) customer`                      | `void`    |                                                         |  
| getAmount     | `(int) order_id`                                             | `double`  | Needs setCustomer being called once before              |  
| validateOrder | `(int) order_id`                                             | `boolean` | Needs setCustomer being called once before              |  
| getOrder      | `(int) order_id`                                             | `Order`   |                                                         |  
| registerOrder | `(Order) order`                                              | `int`     | Register a full order but validate is still needed      | 


#### 3.2.  Shopping 3000 catalogue service :

Description of catalog service exposed by Shopping 3000 :


| Method       | Parameters                        | Returns                       |   
| -------------| ----------------------------------| ------------------------------|  
| listAllItems |                                   | `Shopping3000CatalogItemList` |   
| getItem      | `(Pair<Manufacturer,Integer>) id` |  `Shopping3000CatalogItem`    |   

<br>

## 4.  Provided SoapUI scenario as test case : 

The provided SoapUI project is connected to the catalog and the order services from Shopping 3000. This scenario shows how to make an order using cart's methods :

* an empty order is created and the order id is stored in a property.
* some items will be add into cart by calling the addOrderItem three times (2 items from Minibo are added and 1 from Maximeuble).
* each order item added has a quantity and an id stored in the test case property (this is a pretty large order).
* once all item has been add, we registered customer in the order.
* a customer may wants to check the amount of cart so he will call getAmount to get the order price (this is not possible before because the transport fees may only be calculated when the customer address is known).

After that, the order is processed and a confirmation email is sent to the email specified in the test case email property.