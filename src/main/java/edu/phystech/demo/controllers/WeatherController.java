package edu.phystech.demo.controllers;

import edu.phystech.demo.services.WeatherService;
import edu.phystech.demo.utils.WeatherData;
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
    public List<WeatherData> getWeatherHistory(@RequestParam int nDays, @RequestParam @Nullable String city) {
        if (city == null) {
            return weatherService.getWeatherDataHistory(nDays);
        }
        return weatherService.getWeatherDataHistory(nDays, city);
    }
}
