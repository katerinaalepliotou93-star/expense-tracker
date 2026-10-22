
package org.example.statisticsserviceproject.controller;

import org.example.statisticsserviceproject.client.ExpenseApiClient;
import org.example.statisticsserviceproject.dto.ExpenseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.math.BigDecimal;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class StatisticsControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ExpenseApiClient expenseApiClient;

    @Test
    void shouldReturnStatisticsSummary() throws Exception {
        ExpenseDto first = new ExpenseDto();
        first.setAmount(new BigDecimal("20.00"));

        ExpenseDto second = new ExpenseDto();
        second.setAmount(new BigDecimal("30.50"));

        when(expenseApiClient.getExpenses())
                .thenReturn(new ExpenseDto[]{first, second});

        mockMvc.perform(get("/api/statistics/summary")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.expenseCount").value(2))
                .andExpect(jsonPath("$.totalAmount").value(50.50));
    }
}
