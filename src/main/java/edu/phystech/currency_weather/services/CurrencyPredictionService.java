package edu.phystech.currency_weather.services;

import edu.phystech.currency_weather.utils.WeatherData;
import org.springframework.stereotype.Service;
import org.apache.commons.math3.stat.regression.SimpleRegression;

import java.util.Arrays;
import java.util.List;

@Service
public class CurrencyPredictionService {

        private SimpleRegression regressionModel;
        private WeatherService weatherService;
        private CurrencyService currencyService;

        private final static int PERIOD_SIZE_TO_FIT = 7;


    public CurrencyPredictionService(WeatherService weatherService, CurrencyService currencyService) {
        this.weatherService = weatherService;
        this.currencyService = currencyService;
        fit();
    }

    public  double predict(){
            WeatherData tomorrowForecast = weatherService.getForecastForTomorrow();
            return predict(tomorrowForecast);
    }
    private void fit(){
        List<WeatherData> weatherDataList = weatherService.getWeatherDataHistory(PERIOD_SIZE_TO_FIT);
        List<Double> currencyList = currencyService.getCurrencyData(PERIOD_SIZE_TO_FIT);

        double[] xs = new double[weatherDataList.size()];
        double[] ys = new double[weatherDataList.size()];
        for (int i = 0; i < weatherDataList.size(); ++i) {
            ys[i] = currencyList.get(i);
            xs[i] = weatherDataList.get(i).getAvgTemperature();
        }

        List<double[]> dataset = Arrays.asList(xs, ys);
        for(int i = 0; i < dataset.get(0).length; ++i) {
            regressionModel.addData(dataset.get(0)[i], dataset.get(1)[i]);
        }
    }



    private double predict(WeatherData weatherData){
        return regressionModel.predict(weatherData.getAvgHumidity());
    }
}
