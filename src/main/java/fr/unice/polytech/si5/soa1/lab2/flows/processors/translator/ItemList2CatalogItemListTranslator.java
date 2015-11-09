package fr.unice.polytech.si5.soa1.lab2.flows.processors.translator;

import fr.unice.polytech.si5.soa1.lab2.flows.business.shopping3000.Manufacturer;
import fr.unice.polytech.si5.soa1.lab2.flows.business.shopping3000.Shopping3000CatalogItem;
import fr.unice.polytech.si5.soa1.lab2.flows.utils.Pair;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.transform.Source;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sth on 11/8/15.
 */
public class ItemList2CatalogItemListTranslator {

    /**
     * translate the list of miniboItem into shopping3000 catalogItem
     */
    public static Processor miniboItemList2CatalogListItem = new Processor() {

        private XPath xpath = XPathFactory.newInstance().newXPath();    // feature:install camel-saxon

        public void process(Exchange exchange) throws Exception {
            Source response = (Source) exchange.getIn().getBody();

            List<Shopping3000CatalogItem> listItems = new ArrayList<Shopping3000CatalogItem>();
            NodeList contentsNodeList = (NodeList) xpath.evaluate("//BusinessManagementService_list_contents", response, XPathConstants.NODESET);
            for (int i = 0; i < contentsNodeList.getLength(); i++) {
                Node node = contentsNodeList.item(i);
                Shopping3000CatalogItem item = new Shopping3000CatalogItem();
                Integer id = Integer.parseInt(xpath.evaluate("id/text()", node));
                item.setId(new Pair<Manufacturer, Integer>(Manufacturer.MINIBO,id));
                item.setName(xpath.evaluate("name/text()", node));
                item.setDescription(xpath.evaluate("description/text()", node));
                item.setPrice(Double.parseDouble(xpath.evaluate("price/text()", node)));
                listItems.add(item);
            }
            exchange.getIn().setBody(listItems);
        }
    };

    /**
     * translate the list of maximeubleItem into shopping3000 catalogItem
     */
    public static Processor maximeubleItemList2CatalogListItem = new Processor() {

        private XPath xpath = XPathFactory.newInstance().newXPath();    // feature:install camel-saxon

        public void process(Exchange exchange) throws Exception {
            Source response = (Source) exchange.getIn().getBody();
            List<Shopping3000CatalogItem> listItems = new ArrayList<Shopping3000CatalogItem>();
            NodeList contentsNodeList = (NodeList) xpath.evaluate("//product", response, XPathConstants.NODESET);
            for (int i = 0; i < contentsNodeList.getLength(); i++) {
                Node node = contentsNodeList.item(i);
                Shopping3000CatalogItem item = new Shopping3000CatalogItem();
                Integer id = Integer.parseInt(xpath.evaluate("id/text()", node));
                item.setId(new Pair<Manufacturer, Integer>(Manufacturer.MAXIMEUBLE,id));
                item.setName(xpath.evaluate("name/text()", node));
                item.setDescription(xpath.evaluate("description/text()", node));
                item.setPrice(Double.parseDouble(xpath.evaluate("price/text()", node)));
                listItems.add(item);
            }
            exchange.getIn().setBody(listItems);
        }
    };
}
