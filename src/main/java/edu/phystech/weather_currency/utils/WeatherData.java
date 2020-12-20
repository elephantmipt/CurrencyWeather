package edu.phystech.weather_currency.utils;

import org.json.JSONObject;

public class WeatherData {
    private final double maxTemperature;
    private final double minTemperature;
    private final double avgTemperature;
    private final int avgHumidity;
    private final double maxWind;

    public WeatherData(double maxTemperature, double minTemperature, double avgTemperature, int avgHumidity, double maxWind) {
        this.maxTemperature = maxTemperature;
        this.minTemperature = minTemperature;
        this.avgTemperature = avgTemperature;
        this.avgHumidity = avgHumidity;
        this.maxWind = maxWind;
    }

    public WeatherData(JSONObject json) {
        if (json.get("forecast") != null) {
            json = json.getJSONObject("forecast").getJSONArray("forecastday").getJSONObject(0).getJSONObject("day");
        }
        maxTemperature = json.getDouble("maxtemp_c");
        minTemperature = json.getDouble("mintemp_c");
        avgTemperature = json.getDouble("avgtemp_c");
        avgHumidity = json.getInt("avghumidity");
        maxWind = json.getDouble("maxwind_kph");

    }


    @Override
    public String toString() {
        return "WeatherData{" +
                "maxTemperature=" + maxTemperature +
                ", minTemperature=" + minTemperature +
                ", avgTemperature=" + avgTemperature +
                ", avgHumidity=" + avgHumidity +
                ", maxWind=" + maxWind +
                '}';
    }

    public double getMaxTemperature() {
        return maxTemperature;
    }

    public double getMinTemperature() {
        return minTemperature;
    }

    public double getAvgTemperature() {
        return avgTemperature;
    }

    public double getAvgHumidity() {
        return avgHumidity;
    }

    public double getMaxWind() {
        return maxWind;
    }
}
