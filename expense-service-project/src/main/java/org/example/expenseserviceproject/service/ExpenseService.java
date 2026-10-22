package org.example.expenseserviceproject.service;

import org.example.expenseserviceproject.entity.Expense;
import org.example.expenseserviceproject.repository.ExpenseRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ExpenseService {

    private final ExpenseRepository repository;

    public ExpenseService(ExpenseRepository repository) {
        this.repository = repository;
    }

    public List<Expense> findAll() {
        return repository.findAll();
    }

    public Expense findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Expense not found"));
    }

    public Expense create(Expense expense) {
        expense.setId(null);
        return repository.save(expense);
    }

    public Expense update(Long id, Expense updatedExpense) {
        Expense existing = findById(id);

        existing.setDescription(updatedExpense.getDescription());
        existing.setAmount(updatedExpense.getAmount());
        existing.setCategory(updatedExpense.getCategory());
        existing.setExpenseDate(updatedExpense.getExpenseDate());
        existing.setCurrency(updatedExpense.getCurrency());

        return repository.save(existing);
    }

    public void delete(Long id) {
        Expense existing = findById(id);
        repository.delete(existing);
    }
}
