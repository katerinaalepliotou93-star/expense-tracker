package org.example.expenseserviceproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.expenseserviceproject.entity.Expense;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ExpenseControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateReadUpdateAndDeleteExpense() throws Exception {
        Expense expense = new Expense();
        expense.setDescription("JUnit test expense");
        expense.setAmount(new BigDecimal("25.50"));
        expense.setCategory("Testing");
        expense.setExpenseDate(LocalDate.of(2026, 10, 22));
        expense.setCurrency("EUR");

        String response = mockMvc.perform(post("/api/expenses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(expense)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andReturn()
                .getResponse()
                .getContentAsString();

        long id = objectMapper.readTree(response).get("id").asLong();

        mockMvc.perform(get("/api/expenses/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description")
                        .value("JUnit test expense"));

        expense.setDescription("Updated JUnit expense");
        expense.setAmount(new BigDecimal("30.00"));

        mockMvc.perform(put("/api/expenses/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(expense)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description")
                        .value("Updated JUnit expense"))
                .andExpect(jsonPath("$.amount").value(30.00));

        mockMvc.perform(delete("/api/expenses/{id}", id))
                .andExpect(status().isNoContent());

        // Verify it no longer exists
        mockMvc.perform(get("/api/expenses/{id}", id))
                .andExpect(status().isNotFound());
    }
}
