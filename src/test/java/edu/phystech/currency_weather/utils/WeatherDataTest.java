package edu.phystech.currency_weather.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WeatherDataTest {
    @Test
    void testNoJson() {
        WeatherData weatherData = new WeatherData(null);
        assertNotNull(weatherData.getAvgHumidity());
    }

}