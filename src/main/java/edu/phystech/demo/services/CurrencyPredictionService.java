package edu.phystech.demo.services;

import edu.phystech.demo.utils.WeatherData;
import org.springframework.stereotype.Service;
import org.apache.commons.math3.stat.regression.SimpleRegression;

import java.util.List;

@Service
public class CurrencyPredictionService {

        private SimpleRegression regressionModel;
        private WeatherService weatherService;
        private CurrencyService currencyService;

        private final int PERIOD_SIZE_TO_FIT = 7;

        public void PredictCurrencyByWeatherService(WeatherService weatherService, CurrencyService currencyService) throws Exception {
            this.weatherService = weatherService;
            this.currencyService = currencyService;
            this.regressionModel = new SimpleRegression();
            fit();
        }

    public CurrencyPredictionService(WeatherService weatherService, CurrencyService currencyService) {
        this.weatherService = weatherService;
        this.currencyService = currencyService;
    }

    public  double predict(){
            WeatherData tomorrowForecast = weatherService.getForecastForTomorrow();
            return predict(tomorrowForecast);
        }

        private void fit() throws Exception {
            List<WeatherData> weatherDataList = weatherService.getWeatherDataHistory(PERIOD_SIZE_TO_FIT);
            List<Double> currencyList = currencyService.getCurrencyData(PERIOD_SIZE_TO_FIT);

            double[] xs = new double[weatherDataList.size()];
            double[] ys = new double[weatherDataList.size()];
            for (int i = 0; i < weatherDataList.size(); ++i) {
                ys[i] = currencyList.get(i);
                xs[i] = weatherDataList.get(i).getAvgTemperature();
            }

            List<double[]> dataset = List.of(xs, ys);
            for(int i = 0; i < dataset.get(0).length; ++i) {
                regressionModel.addData(dataset.get(0)[i], dataset.get(1)[i]);
            }
        }

        private double predict(WeatherData weatherData){
            return regressionModel.predict(weatherData.getAvgHumidity());
        }
}
