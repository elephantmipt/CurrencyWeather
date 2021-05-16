package edu.phystech.predict;

import edu.phystech.predict.PredictApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CurrencyPredictionController {
    private final PredictApplication predictionApplication;


    public CurrencyPredictionController(PredictApplication predictApplication) {
        this.predictionApplication = predictApplication;
    }

    @GetMapping("/predict")
    public String makePredictForTomorrow(){
        return "Predicted currency for tomorrow: " + predictionApplication.predict();
    }
}
