package edu.phystech.weather;

import edu.phystech.weather.utils.WeatherData;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
public class WeatherController {

    private final WeatherApplication weatherApplication;


    public WeatherController(WeatherApplication weatherApplication) {
        this.weatherApplication = weatherApplication;
    }

    @GetMapping("/weather-service")
    public List<Double> getWeatherHistory(@RequestParam int nDays, @RequestParam(required = false) @Nullable String city) {
        if (city == null) {
            return weatherApplication.getWeatherDataHistory(nDays);
        }
        return weatherApplication.getWeatherDataHistory(nDays, city);
    }

    @GetMapping("/forecast")
    public double getForecast(){
        return weatherApplication.getForecastForTomorrow().getAvgTemperature();
    }
}
