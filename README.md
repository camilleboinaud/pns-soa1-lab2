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

In order to use services, you must build projects using `mvn package` command (for each services: minibo, maximeuble and shopping 3000). Then you need to copy your .war files into `{servicemix_path}/deploy`. 
 
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

| Method        | Parameters                                                   | Returns   | Specificities                                           |  
| --------------| -------------------------------------------------------------| ----------| --------------------------------------------------------|  
| startOrder    |                                                              | `int`     | Required as first operation                             |  
| addOrderItem  | `(int) order_id`, `(OrderItem) order_item`, `(int) quantity` | `void`    |                                                         |  
| setCustomer   | `(int) order_id`, `(Customer) customer`                      | `void`    |                                                         |  
| getAmount     | `(int) order_id`                                             | `double`  | Needs setCustomer beeing called once before             |  
| validateOrder | `(int) order_id`                                             | `boolean` | Needs setCustomer beeing called once before             |  
| getOrder      | `(int) order_id`                                             | `Order`   |                                                         |  
| registerOrder | `(Order) order`                                              | `int`     | Needs to be the only method called to complete an order | 


#### 3.2.  Shopping 3000 catalogue service :

Description of catalogue service exposed by Shopping 3000 :


| Method       | Parameters                        | Returns                       |   
| -------------| ----------------------------------| ------------------------------|  
| listAllItems |                                   | `Shopping3000CatalogItemList` |   
| getItem      | `(Pair<Manufacturer,Integer>) id` |  `Shopping3000CatalogItem`    |   

