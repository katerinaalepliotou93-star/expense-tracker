package org.example.expenseserviceproject.client;

import org.example.expenseserviceproject.dto.ExchangeRateDto;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Client for retrieving foreign exchange rates from the public
 * Frankfurter REST API.
 * This client sends HTTP requests to the external API and maps
 * the responses to Java objects for use by the Expense Service.
 */

@Component
public class FrankfurterClient {

    private final RestClient restClient;

    public FrankfurterClient(RestClient.Builder builder) {
        this.restClient = builder.baseUrl("https://api.frankfurter.dev/v2")
                .build();
    }

    // Retrieves the latest available exchange rate for a currency pair.
    public ExchangeRateDto getRate(String from, String to) {
        return restClient.get().uri("/rate/{from}/{to}", from, to)
                .retrieve()
                .body(ExchangeRateDto.class);
    }

}
