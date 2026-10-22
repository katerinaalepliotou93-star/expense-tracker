package org.example.statisticsserviceproject.controller;

import org.example.statisticsserviceproject.service.StatisticsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {

    private final StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping("/summary")
    public Map<String, Object> getSummary() {
        return statisticsService.getSummary();
    }

    @GetMapping("/categories")
    public Map<String, java.math.BigDecimal> getCategories() {
        return statisticsService.getCategories();
    }

}
