package edu.phystech.currency;

import edu.phystech.currency.CurrencyApplication;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CurrencyController {
    private final CurrencyApplication currencyApplication;

    public CurrencyController(CurrencyApplication currencyApplication) {
        this.currencyApplication = currencyApplication;
    }

    @GetMapping("/currency-service")
    public List<Double> getCurrencyHistory(@RequestParam @NonNull int n) {
        return currencyApplication.getCurrencyData(n);
    }
}
