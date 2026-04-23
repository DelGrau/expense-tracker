package com.delgrau.expensetracker.commands;

import com.delgrau.expensetracker.repository.ExpenseRepository;

import java.nio.file.Path;

public abstract class BaseCommand implements iCommand {
    protected ExpenseRepository repo = new ExpenseRepository();

    @Override
    public void setRepository(ExpenseRepository repo) {
        this.repo = repo;
    }

    @Override
    public ExpenseRepository getRepository() {
        return this.repo;
    }

    public void setupTest(iCommand cmd, Path tempFile) {
        ExpenseRepository testRepo = new ExpenseRepository(tempFile.toString());
        cmd.setRepository(testRepo);
    }
}
