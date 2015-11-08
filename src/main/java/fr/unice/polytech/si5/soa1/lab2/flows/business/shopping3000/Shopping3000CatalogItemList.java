package fr.unice.polytech.si5.soa1.lab2.flows.business.shopping3000;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.List;

/**
 * Created by sth on 11/8/15.
 */
public class Shopping3000CatalogItemList{

    private List<Shopping3000CatalogItem> catalogItemList;

//    @XmlElementWrapper(name = "listCatalogItem")
    @XmlElement(name = "item")
    public List<Shopping3000CatalogItem> getCatalogItemList(){
        return catalogItemList;
    }

    public void setCatalogItemList(List<Shopping3000CatalogItem> list){
        this.catalogItemList = list;
    }

}
