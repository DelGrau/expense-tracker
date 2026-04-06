package com.delgrau.expensetracker.commands;

import com.delgrau.expensetracker.model.Amount;
import com.delgrau.expensetracker.model.Expense;
import picocli.CommandLine.Option;
import picocli.CommandLine.Command;

import java.util.concurrent.Callable;

@Command(name = "add", description = "Adds a new expense.")
public class AddCommand implements Callable<Integer> {

    @Option(names = {"-d", "--description"}, required = true, description = "Describes a expense.")
    private String description;

    @Option(names = {"-a", "--amount"}, required = true, description = "How much a expense costs.")
    private Amount amount;

    @Override
    public Integer call() {
        var e = new Expense(description, amount.getAmount());

        System.out.printf("Expense added successfully (ID: %d)\n", e.getId());
        System.out.printf("Description: %s\n", e.getDescription());
        System.out.printf("Amount: %.2f (%d cents)\n", e.getValue(), e.getCents());

        return 0;
    }
}
