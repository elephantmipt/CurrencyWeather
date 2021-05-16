package edu.phystech.currency_weather;

import edu.phystech.currency_weather.services.WeatherService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WeatherCurrencyApplicationTests {
    @Autowired
    private WeatherService weatherService;

    @Test
    void contextLoads() {
    }

}
