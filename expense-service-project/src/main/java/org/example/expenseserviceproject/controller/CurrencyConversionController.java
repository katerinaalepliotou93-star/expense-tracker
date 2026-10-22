package org.example.expenseserviceproject.controller;

import org.example.expenseserviceproject.dto.ConversionResultDto;
import org.example.expenseserviceproject.service.CurrencyConversionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/exchange-rate")
public class CurrencyConversionController {

    private final CurrencyConversionService service;

    public CurrencyConversionController(
            CurrencyConversionService service) {
        this.service = service;
    }

    @GetMapping
    public ConversionResultDto convert(@RequestParam String from, @RequestParam String to,
                                       @RequestParam BigDecimal amount) {
        return service.convert(from, to, amount);
    }
}
