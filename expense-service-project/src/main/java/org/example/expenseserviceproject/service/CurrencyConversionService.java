package org.example.expenseserviceproject.service;

import org.example.expenseserviceproject.client.FrankfurterClient;
import org.example.expenseserviceproject.dto.ConversionResultDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class CurrencyConversionService {

    private final FrankfurterClient client;

    public CurrencyConversionService(FrankfurterClient client) {
        this.client = client;
    }

    public ConversionResultDto convert(String from, String to, BigDecimal amount) {

        if (amount == null || amount.signum() <= 0) {
            throw new IllegalArgumentException(
                    "Amount must be greater than zero");
        }

        from = from.toUpperCase();
        to = to.toUpperCase();

        BigDecimal rate;

        if (from.equals(to)) {
            rate = BigDecimal.ONE;
        } else {
            rate = client.getRate(from, to).getRate();
        }

        BigDecimal convertedAmount = amount.multiply(rate).setScale(2, RoundingMode.HALF_UP);

        return new ConversionResultDto(from, to, amount, rate, convertedAmount);
    }

}
