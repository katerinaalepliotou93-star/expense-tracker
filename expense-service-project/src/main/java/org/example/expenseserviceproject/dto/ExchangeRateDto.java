package org.example.expenseserviceproject.dto;

import java.time.LocalDate;
import java.math.BigDecimal;

public class ExchangeRateDto {

    private LocalDate date;
    private String base;
    private String quote;
    private BigDecimal rate;

    public ExchangeRateDto() {
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }
}
