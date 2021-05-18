package edu.phystech.currency;

import edu.phystech.currency.utils.CurrencyValues;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.web.client.RestTemplate;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@EnableDiscoveryClient
@SpringBootApplication
public class CurrencyApplication {

    private static final String BASE_URL = "http://www.cbr.ru/scripts/XML_daily.asp?date_req=";
    private String charCode = "USD";
    private static final Logger CURRENCY_LOGGER = Logger.getLogger("Currency");

    public void setCharCode(String charCode) {
        this.charCode = charCode;
    }


    public List<Double> getCurrencyData(int nDays){
        RestTemplate restTemplate = new RestTemplate();
        LocalDate currentDate = LocalDate.now();
        List<Double> currencyList = new ArrayList<>(nDays);
        for (int i = 0; i < nDays; ++i) {
            LocalDate day = currentDate.minusDays(i);
            String response =
                    restTemplate.getForEntity(createRequestString(day), String.class).getBody();
            CurrencyValues currencyValues = new CurrencyValues(response);
            try {
                currencyList.add(currencyValues.getValue(charCode));
            } catch (Exception e) {
                CURRENCY_LOGGER.log(Level.SEVERE, "Exception occur", e);
            }


        }

        return currencyList;
    }

    private String createRequestString(LocalDate date) {
        return BASE_URL + date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public static void main(String[] args) {
        SpringApplication.run(CurrencyApplication.class, args);
    }

}
