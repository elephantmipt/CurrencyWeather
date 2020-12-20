package edu.phystech.currency_weather.utils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.springframework.web.client.RestTemplate;

class CurrencyValuesTest {
    @Test
    void TestParsing() throws Exception {
        String baseURL = "http://www.cbr.ru/scripts/XML_daily.asp?date_req=";
        String reqString =  baseURL + LocalDate.now().minusDays(1).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForEntity(reqString, String.class).getBody();
        CurrencyValues CV = new CurrencyValues(response);
        assertTrue(CV.GetValue("USD") > 0.);
    }
}