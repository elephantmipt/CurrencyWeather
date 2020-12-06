package edu.phystech.demo.services;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.phystech.demo.Secrets;
import edu.phystech.demo.utils.WeatherData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Service
public class WeatherService {
    private final String apiKey = Secrets.WEATHER_API_KEY;
    private static final String baseURL = "http://api.weatherapi.com/v1";
    private static final String apiHistoryMethod = "/history.json";
    private static final String apiForecastMethod = "/forecast.json";

    private static final String DEFAULT_CITY = "Moscow";

    private final RestTemplate restTemplate;
    private final ObjectMapper mapper;

    @Autowired
    public WeatherService(RestTemplateBuilder builder, @Autowired ObjectMapper objectMapper){
        restTemplate = builder.build();
        mapper = objectMapper;
    }

    public List<WeatherData> getWeatherDataHistory(int n) {
        return getWeatherData(n, DEFAULT_CITY);
    }

    public List<WeatherData> getWeatherDataHistory(int n, String city) {
        return getWeatherData(n, city);
    }

    private List<WeatherData> getWeatherData(int n, String city) {
        LocalDate currentDate = LocalDate.now();
        List<WeatherData> weatherList = new ArrayList<>(n);
        for (int i = 0; i < n; ++i) {
            LocalDate day = currentDate.minusDays(i);
            ResponseEntity<String> response = restTemplate.getForEntity(createRequestString(day, city), String.class);
            WeatherData weather = parseWeatherData(response);
            weatherList.add(weather);
        }
        return weatherList;
    }

    private WeatherData parseWeatherData(ResponseEntity<String> response) {
        JsonNode json;
        try {
            json = mapper.readTree(response.getBody());
        } catch (JsonProcessingException e) {
            return null;
        }
        return new WeatherData(json);
    }

    private String createRequestString(LocalDate date, String city) {
        return baseURL + apiHistoryMethod + "?key=" + apiKey + "&q=" + city + "&dt=" +
                date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    private String createForecastRequestString(String city) {
        return baseURL + apiForecastMethod + "?key=" + apiKey + "&q=" + city + "&days=1";
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
}
