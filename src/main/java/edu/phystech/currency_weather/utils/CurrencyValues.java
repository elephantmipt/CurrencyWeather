package edu.phystech.currency_weather.utils;

import java.io.Serializable;


import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;


public class CurrencyValues implements Serializable {

    private JSONObject jsonValues;

    public CurrencyValues(String xmlFile) {
        jsonValues = XML.toJSONObject(xmlFile);
    }

    public double GetValue(String charCode) {
        double value= -1.;
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
