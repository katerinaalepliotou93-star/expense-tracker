package org.example.statisticsserviceproject.service;

import org.example.statisticsserviceproject.client.ExpenseApiClient;
import org.example.statisticsserviceproject.dto.ExpenseDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatisticsService {

    private final ExpenseApiClient expenseApiClient;

    public StatisticsService(ExpenseApiClient expenseApiClient) {
        this.expenseApiClient = expenseApiClient;
    }

    public Map<String, Object> getSummary() {
        ExpenseDto[] expensesArray = expenseApiClient.getExpenses();
        List<ExpenseDto> expenses = Arrays.asList(expensesArray);

        BigDecimal totalAmount = BigDecimal.ZERO;

        for (ExpenseDto expense : expenses) {
            if (expense.getAmount() != null) {
                totalAmount = totalAmount.add(expense.getAmount());
            }
        }

        Map<String, Object> summary = new HashMap<>();
        summary.put("expenseCount", expenses.size());
        summary.put("totalAmount", totalAmount);

        return summary;
    }

    public Map<String, BigDecimal> getCategories() {
        ExpenseDto[] expenses = expenseApiClient.getExpenses();

        Map<String, BigDecimal> totals = new HashMap<>();

        for (ExpenseDto expense : expenses) {
            String category = expense.getCategory();
            BigDecimal amount = expense.getAmount();

            if (category != null && amount != null) {
                BigDecimal currentTotal = totals.getOrDefault(category, BigDecimal.ZERO);
                totals.put(category, currentTotal.add(amount));
            }
        }
        return totals;
    }

}
