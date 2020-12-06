package edu.phystech.demo.utils;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "ValCurs")
public class CurrencyValues implements Serializable {
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Valute")
    private final List<CurrencyItem> currencyItemList;

    public List<CurrencyItem> getCurrencyItemList() {
        return currencyItemList;
    }

    public CurrencyValues(List<CurrencyItem> valueList) {
        this.currencyItemList = valueList;
    }
    public CurrencyValues(){
        currencyItemList = new ArrayList<>();
    }

    public CurrencyItem getItem(String currencyItemName){
        for(CurrencyItem currencyItem : currencyItemList){
            if(currencyItem.getName().equals(currencyItemName)){
                return currencyItem;
            }
        }
        return null;
    }
}
