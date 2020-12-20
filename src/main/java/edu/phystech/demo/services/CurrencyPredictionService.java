package edu.phystech.demo.services;

import edu.phystech.demo.utils.WeatherData;
import org.springframework.stereotype.Service;
import org.apache.commons.math3.stat.regression.SimpleRegression;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
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

            List<double[]> dataset = createDataset(weatherDataList, currencyList);
            for(int i = 0; i < dataset.get(0).length; ++i) {
                regressionModel.addData(dataset.get(0)[i], dataset.get(1)[i]);
            }
        }



        private List<double[]> createDataset(List<WeatherData> weatherDataList, List<Double> currencyList) {
            List<Integer> indexes = IntStream
                    .range(0, weatherDataList.size())
                    .boxed()
                    .filter(i -> weatherDataList.get(i) != null && currencyList.get(i) != null)
                    .collect(Collectors.toList());
            double[] xs = new double[indexes.size()];
            double[] ys = new double[indexes.size()];
            for (int i = 0; i < indexes.size(); ++i) {
                ys[i] = currencyList.get(indexes.get(i));
                xs[i] = weatherDataList.get(indexes.get(i)).getAvgTemperature();
            }
            return List.of(xs, ys);
        }

        private double predict(WeatherData weatherData){
            return regressionModel.predict(weatherData.getAvgHumidity());
        }
}
