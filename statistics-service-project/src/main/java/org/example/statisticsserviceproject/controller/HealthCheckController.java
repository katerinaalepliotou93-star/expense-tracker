package org.example.statisticsserviceproject.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Map;

@RestController
public class HealthCheckController {

    @GetMapping("/api/health")
    public Map<String, Object> health() {
        return Map.of(
                "service", "statistics-service",
                "status", "UP",
                "timestamp", Instant.now().toString()
        );
    }
}
