package edu.phystech.weather;

import edu.phystech.weather.utils.WeatherData;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@EnableDiscoveryClient
@SpringBootApplication
public class WeatherApplication {

    private static final String API_KEY = "0e12b633a49844c2a53133128211605";
    private static final String BASE_URL = "http://api.weatherapi.com/v1";
    private static final String API_HISTORY_METHOD = "/history.json";
    private static final String API_FORECAST_METHOD = "/forecast.json";

    private static final String DEFAULT_CITY = "Moscow";

    private final RestTemplate restTemplate;

    public WeatherApplication(@Autowired RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    public List<Double> getWeatherDataHistory(int n) {
        return getWeatherData(n, DEFAULT_CITY);
    }

    public List<Double> getWeatherDataHistory(int n, String city) {
        return getWeatherData(n, city);
    }

    private List<Double> getWeatherData(int n, String city) {
        LocalDate currentDate = LocalDate.now();
        List<Double> weatherList = new ArrayList<>(n);
        for (int i = 0; i < n; ++i) {
            LocalDate day = currentDate.minusDays(i);
            ResponseEntity<String> response = restTemplate.getForEntity(createRequestString(day, city), String.class);
            WeatherData weather = parseWeatherData(response);
            weatherList.add(weather.getAvgTemperature());
        }
        return weatherList;
    }

    private WeatherData parseWeatherData(ResponseEntity<String> response) {
        JSONObject json = new JSONObject(response.getBody());
        return new WeatherData(json);
    }

    private String createRequestString(LocalDate date, String city) {
        return BASE_URL + API_HISTORY_METHOD + "?key=" + API_KEY + "&q=" + city + "&dt=" +
                date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    private String createForecastRequestString(String city) {
        return BASE_URL + API_FORECAST_METHOD + "?key=" + API_KEY + "&q=" + city + "&days=1";
    }

    public WeatherData getForecastForTomorrow() {
        return getForecastData(DEFAULT_CITY);
    }

    public WeatherData getForecastForTomorrow(String city) {
        return getForecastData(city);
    }

    public WeatherData getForecastData(String city) {
        ResponseEntity<String> response = restTemplate.getForEntity(createForecastRequestString(city), String.class);
        return parseWeatherData(response);
    }



    public static void main(String[] args) {
        SpringApplication.run(WeatherApplication.class, args);
    }

}
