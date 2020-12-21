package edu.phystech.currency_weather.services;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CurrencyServiceTest {
    @Test
    void catchTest() {
        CurrencyService currencyService = new CurrencyService();
        currencyService.setCHAR_CODE("BBB");
        assertEquals(currencyService.getCurrencyData(7).size(), 0);
    }

}