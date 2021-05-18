package edu.phystech.currency.utils;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;

import java.io.Serializable;


public class CurrencyValues implements Serializable {

    private JSONObject jsonValues;

    public CurrencyValues(String xmlFile) {
        jsonValues = XML.toJSONObject(xmlFile);
    }

    public double getValue(String charCode) {
        double value = -1.;
        JSONArray values = jsonValues.getJSONObject("ValCurs").getJSONArray("Valute");
        for (int i = 0; i < values.length(); ++i) {
            JSONObject currentValute = values.getJSONObject(i);
            if (currentValute.getString("CharCode").equals(charCode)) {
                value = Double.parseDouble(currentValute.getString("Value").replace(",", "."));
            }
        }
        if (value < 0) {
            throw new IllegalArgumentException("Invalid char code");
        }
        return value;
    }

}
