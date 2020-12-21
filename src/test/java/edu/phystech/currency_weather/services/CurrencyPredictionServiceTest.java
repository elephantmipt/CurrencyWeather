package edu.phystech.currency_weather.services;

import edu.phystech.currency_weather.utils.WeatherData;
import org.junit.jupiter.api.Test;
import org.springframework.boot.web.client.RestTemplateBuilder;

import static org.junit.jupiter.api.Assertions.*;

class CurrencyPredictionServiceTest {
    @Test
    public void TestField() {
        WeatherData weatherData = new WeatherData(11., 12., 13., 12, 12.);
        String strData = weatherData.toString();
        assertEquals(weatherData.getMaxTemperature(), 11.);
        assertEquals(weatherData.getMinTemperature(), 12.);
        assertEquals(weatherData.getAvgHumidity(), 12);
        assertEquals(weatherData.getAvgTemperature(), 13.);
        assertEquals(weatherData.getMaxWind(), 12.);
    }

    @Test
    public void predictTest() {
        CurrencyService currencyService = new CurrencyService();
        WeatherService weatherService = new WeatherService(new RestTemplateBuilder());
        CurrencyPredictionService currencyPredictionService = new CurrencyPredictionService(weatherService, currencyService);
        assertTrue(currencyPredictionService.predict() > 0);
    }

}