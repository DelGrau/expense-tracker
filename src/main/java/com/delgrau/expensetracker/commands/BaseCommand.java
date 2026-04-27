package com.delgrau.expensetracker.commands;

import com.delgrau.expensetracker.repository.JsonExpenseRepository;
import com.delgrau.expensetracker.repository.iExpenseRepository;

import java.nio.file.Path;

public abstract class BaseCommand implements iCommand {
    protected iExpenseRepository repo;

    @Override
    public void setRepository(iExpenseRepository repo) {
        this.repo = repo;
    }

    @Override
    public iExpenseRepository getRepository() {
        return this.repo;
    }

    public void setupTest(iCommand cmd, iExpenseRepository testRepo) {
        cmd.setRepository(testRepo);
    }
}
