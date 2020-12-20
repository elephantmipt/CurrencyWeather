package edu.phystech.weather_currency.services;

import edu.phystech.weather_currency.utils.CurrencyValues;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class CurrencyService {
    private final static String baseURL = "http://www.cbr.ru/scripts/XML_daily.asp?date_req=";
    private final static String charCode = "USD";

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
                currencyList.add(currencyValues.GetValue("USD"));
            } catch (Exception e) {
                System.out.println("Failed");
            }


        }

        return currencyList;
    }

    private String createRequestString(LocalDate date) {
        return baseURL + date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
}
