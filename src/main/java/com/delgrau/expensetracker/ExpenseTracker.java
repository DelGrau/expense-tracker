package com.delgrau.expensetracker;

import com.delgrau.expensetracker.commands.AddCommand;
import com.delgrau.expensetracker.commands.SummaryCommand;
import com.delgrau.expensetracker.model.Amount;
import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(
    name = "expense-tracker",
    mixinStandardHelpOptions = true,
    version = "1.2",
    description = "Personal CLI app for tracking your expenses.",
    subcommands = { AddCommand.class, SummaryCommand.class })
public class ExpenseTracker implements Runnable {
    @Override
    public void run() {
        System.out.println("Type 'expense-tracker add --help' to get some help adding a new expense.");
    }

    public static void main(String[] args) {
        CommandLine cmd = new CommandLine(new ExpenseTracker());

        cmd.registerConverter(Amount.class, s -> {
            double val = Double.parseDouble(s.replace(",", "."));
            return Amount.fromCurrency(val);
        });

        var exitCode = cmd.execute(args);
        System.exit(exitCode);
    }
}