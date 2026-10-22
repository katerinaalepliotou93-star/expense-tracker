package org.example.expenseserviceproject.repository;

import org.example.expenseserviceproject.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
}
