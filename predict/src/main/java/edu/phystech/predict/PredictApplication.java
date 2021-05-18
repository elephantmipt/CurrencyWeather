package edu.phystech.predict;

import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import java.util.Arrays;
import java.util.List;

@EnableDiscoveryClient
@SpringBootApplication
public class PredictApplication {
    private static final String currencyURL = "https://weatherservice:8082";
    private static final String weatherURL = "https://weatherservice:8081";

    private SimpleRegression regressionModel;

    private final RestTemplate restTemplate;


    private static final int PERIOD_SIZE_TO_FIT = 7;


    public PredictApplication(@Autowired RestTemplateBuilder restTemplateBuilder) {
        this.regressionModel = new SimpleRegression();
        this.restTemplate = restTemplateBuilder.build();
        fit();
    }

    public double predict(){
        var tomorrowForecastResponse = restTemplate.getForEntity(weatherURL+"/forecast", double.class);
        var tomorrowForecast = tomorrowForecastResponse.getBody();
        return regressionModel.predict(tomorrowForecast);
    }

    private void fit(){
        var weatherDataListResponse = restTemplate.getForEntity(weatherURL+"/weather-service", double[].class);
        var weatherDataList = weatherDataListResponse.getBody();
        var currencyListResponse = restTemplate.getForEntity(currencyURL+"currency-service?n=7", double[].class);
        var currencyList = currencyListResponse.getBody();


        List<double[]> dataset = Arrays.asList(weatherDataList, currencyList);

        for (int i = 0; i < dataset.get(0).length; ++i) {
            regressionModel.addData(dataset.get(0)[i], dataset.get(1)[i]);
        }

    }
    public static void main(String[] args) {
        SpringApplication.run(PredictApplication.class, args);
    }

}
