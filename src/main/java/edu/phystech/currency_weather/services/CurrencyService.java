package edu.phystech.currency_weather.services;

import edu.phystech.currency_weather.utils.CurrencyValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class CurrencyService {
    private final static String baseURL = "http://www.cbr.ru/scripts/XML_daily.asp?date_req=";
    private static String charCode = "USD";

    public void setCharCode(String charCode) {
        this.charCode = charCode;
    }


    public List<Double> getCurrencyData(int nDays){
        RestTemplate restTemplate = new RestTemplate();
        LocalDate currentDate = LocalDate.now();
        List<Double> currencyList = new ArrayList<>(nDays);
        for (int i = 0; i < nDays; ++i) {
            LocalDate day = currentDate.minusDays(i);
            String response =
                    restTemplate.getForEntity(createRequestString(day), String.class).getBody();
            CurrencyValues currencyValues = new CurrencyValues(response);
            try {
                currencyList.add(currencyValues.GetValue(charCode));
            } catch (Exception e) {
                System.out.println("Failed to load currency");
            }


        }

        return currencyList;
    }

    private String createRequestString(LocalDate date) {
        return baseURL + date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
}
