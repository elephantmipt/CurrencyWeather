package edu.phystech.demo.services;

import edu.phystech.demo.utils.CurrencyValues;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CurrencyService {
    private final String baseURL = "http://www.cbr.ru/scripts/XML_daily.asp?date_req=";

    public List<Double> getCurrencyData(int nDays) {
        RestTemplate restTemplate = new RestTemplate();
        LocalDate currentDate = LocalDate.now();
        List<Double> currencyList = new ArrayList<>(nDays);
        for (int i = 0; i < nDays; ++i) {
            LocalDate day = currentDate.minusDays(i);
            ResponseEntity<CurrencyValues> response =
                    restTemplate.getForEntity(createRequestString(day), CurrencyValues.class);
            System.out.println(Objects.requireNonNull(response.getBody()).getCurrencyItemList().get(0).getValue());
            if (!response.hasBody()) {
                currencyList.add(null);
            } else {

                currencyList.add(response.getBody().getItem("Доллар США").getValue());
            }
        }

        return currencyList;
    }

    private String createRequestString(LocalDate date) {
        return baseURL + date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
}
