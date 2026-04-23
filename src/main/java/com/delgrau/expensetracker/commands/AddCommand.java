package com.delgrau.expensetracker.commands;

import com.delgrau.expensetracker.model.Amount;
import com.delgrau.expensetracker.model.Expense;
import com.delgrau.expensetracker.converter.AmountConverter;
import picocli.CommandLine.Option;
import picocli.CommandLine.Command;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Spec;

import java.io.PrintWriter;
import java.util.List;

@Command(name = "add", description = "Adds a new expense.")
public class AddCommand extends BaseCommand {

    @Spec 
    CommandSpec spec;
    
    @Option(names = {"-d", "--description"}, required = true, description = "Describes a expense.")
    private String description;

    @Option(names = {"-a", "--amount"}, required = true, description = "How much a expense costs.", converter = AmountConverter.class)
    private Amount amount;

    @Option(names = {"-c", "--category"}, defaultValue = "", description = "Expense category.")
    private String category;

    @Override
    public Integer call() {
        List<Expense> expenses = repo.loadExpenses();

        long nextId = 1;
        if (!expenses.isEmpty()) {
            Expense lastExpense = expenses.getLast();
            nextId = lastExpense.getId() + 1;
        }

        Expense newExpense = new Expense(nextId, description, amount.getAmount(), category);
        expenses.add(newExpense);

        repo.saveExpenses(expenses);

        PrintWriter out = spec.commandLine().getOut();
        out.println("Expense added successfully (ID: " + newExpense.getId() + ")");
        return 0;
    }
}
