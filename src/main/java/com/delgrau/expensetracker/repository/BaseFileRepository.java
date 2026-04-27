package com.delgrau.expensetracker.repository;

import com.delgrau.expensetracker.model.Expense;

import java.util.List;

public abstract class BaseFileRepository implements iExpenseRepository{
    protected final String filePath;

    public BaseFileRepository(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }
}
