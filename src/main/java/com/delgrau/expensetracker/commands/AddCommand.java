package com.delgrau.expensetracker.commands;

import com.delgrau.expensetracker.model.Amount;
import com.delgrau.expensetracker.model.Expense;
import com.delgrau.expensetracker.repository.ExpenseRepository;
import com.delgrau.expensetracker.converter.AmountConverter;
import picocli.CommandLine.Option;
import picocli.CommandLine.Command;
import picocli.CommandLine.Model.CommandSpec;

import java.util.List;
import java.util.concurrent.Callable;

@Command(name = "add", description = "Adds a new expense.")
public class AddCommand implements Callable<Integer> {

    @Spec 
    CommandSpec spec;
    
    @Option(names = {"-d", "--description"}, required = true, description = "Describes a expense.")
    private String description;

    @Option(names = {"-a", "--amount"}, required = true, description = "How much a expense costs.", converter = AmountConverter.class)
    private Amount amount;

    @Override
    public Integer call() {
        ExpenseRepository repo = new ExpenseRepository();

        List<Expense> expenses = repo.loadExpenses();

        long nextId = 1;
        if (!expenses.isEmpty()) {
            Expense lastExpense = expenses.getLast();
            nextId = lastExpense.getId() + 1;
        }

        Expense newExpense = new Expense(nextId, description, amount.getAmount());
        expenses.add(newExpense);

        repo.saveExpenses(expenses);

        PrintWriter out = spec.commandLine().getOut();
        out.println("Expense added successfully (ID: " + newExpense.getId() + ")");
        return 0;
    }
}
