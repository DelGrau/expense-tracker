package com.delgrau.expensetracker.repository;

import com.delgrau.expensetracker.model.Expense;

import java.util.List;

public interface iExpenseRepository {
    List<Expense> loadExpenses();
    void saveExpenses(List<Expense> expenses);
}
