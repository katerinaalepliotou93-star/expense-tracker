package org.example.statisticsserviceproject.client;

import org.example.statisticsserviceproject.dto.ExpenseDto;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class ExpenseApiClient {

    private final RestClient restClient;

    public ExpenseApiClient() {
        this.restClient = RestClient.builder()
                .baseUrl("http://localhost:8081")
                .build();
    }

    public ExpenseDto[] getExpenses() {
        return restClient.get()
                .uri("/api/expenses")
                .retrieve()
                .body(ExpenseDto[].class);
    }
}
