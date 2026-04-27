package com.delgrau.expensetracker.commands;

import com.delgrau.expensetracker.repository.iExpenseRepository;

import java.util.concurrent.Callable;

public interface iCommand extends Callable<Integer> {
    void setRepository(iExpenseRepository repo);
    iExpenseRepository getRepository();
}
