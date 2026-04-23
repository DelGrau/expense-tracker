package com.delgrau.expensetracker.commands;

import com.delgrau.expensetracker.repository.ExpenseRepository;

import java.util.concurrent.Callable;

public interface iCommand extends Callable<Integer> {
    void setRepository(ExpenseRepository repo);
    ExpenseRepository getRepository();
}
