package edu.phystech.weather_currency.controllers;

import edu.phystech.weather_currency.services.WeatherService;
import edu.phystech.weather_currency.utils.WeatherData;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
public class WeatherController {

    private final WeatherService weatherService;


    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/weather-service")
    public List<WeatherData> getWeatherHistory(@RequestParam int nDays, @RequestParam(required = false) @Nullable String city) {
        if (city == null) {
            return weatherService.getWeatherDataHistory(nDays);
        }
        return weatherService.getWeatherDataHistory(nDays, city);
    }
}
