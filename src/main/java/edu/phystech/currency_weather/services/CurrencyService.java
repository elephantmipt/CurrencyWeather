package edu.phystech.currency_weather.services;

import edu.phystech.currency_weather.utils.CurrencyValues;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class CurrencyService {
    private static final String BASE_URL = "http://www.cbr.ru/scripts/XML_daily.asp?date_req=";
    private String CHAR_CODE = "USD";

    public void setCHAR_CODE(String charCode) {
        this.CHAR_CODE = charCode;
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
                currencyList.add(currencyValues.GetValue(CHAR_CODE));
            } catch (Exception e) {
                System.err.println("Failed to load currency");
            }


        }

        return currencyList;
    }

    private String createRequestString(LocalDate date) {
        return BASE_URL + date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
}
