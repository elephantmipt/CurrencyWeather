package edu.phystech.currency_weather.controllers;

import edu.phystech.currency_weather.services.CurrencyPredictionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CurrencyPredictionController {
    private final CurrencyPredictionService predictionService;


    public CurrencyPredictionController(CurrencyPredictionService predictionService) {
        this.predictionService = predictionService;
    }

    @GetMapping("/predict")
    public String makePredictForTomorrow(){
        return "Predicted currency for tomorrow: " + predictionService.predict();
    }
}
