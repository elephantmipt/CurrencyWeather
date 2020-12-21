package edu.phystech.currency_weather.services;

import edu.phystech.currency_weather.utils.WeatherData;
import org.junit.jupiter.api.Test;
import org.springframework.boot.web.client.RestTemplateBuilder;

import static org.junit.jupiter.api.Assertions.*;

class WeatherServiceTest {
    @Test
    void TestWeather() {
        WeatherService weatherService = new WeatherService(new RestTemplateBuilder());
        WeatherData result = weatherService.getForecastData("Moscow");
        assertNotNull(result);

    }
}