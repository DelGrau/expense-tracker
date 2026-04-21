package com.delgrau.expensetracker.commands;

import com.delgrau.expensetracker.model.Expense;
import com.delgrau.expensetracker.repository.ExpenseRepository;
import picocli.CommandLine.Command;
import picocli.CommandLine.Spec;
import picocli.CommandLine.Model.CommandSpec;

import java.io.PrintWriter;
import java.util.List;
import java.util.concurrent.Callable;

@Command(name = "list",
        description = "Show a detailed list of expenses.")
public class ListCommand implements Callable<Integer> {

    @Spec
    CommandSpec spec;

    @Override
    public Integer call() throws Exception {
        ExpenseRepository repo = new ExpenseRepository();
        List<Expense> expenses = repo.loadExpenses();

        if (expenses == null || expenses.isEmpty()) {
            PrintWriter err = spec.commandLine().getErr();
            err.println("Cannot list expenses: List is empty");
            return 1;
        }

        int maxDesc = expenses.stream()
                .mapToInt(e -> e.getDescription().length())
                .max()
                .orElse(11);
        maxDesc = Math.max(maxDesc, 11);

        String cyan = "\u001B[36m";
        String green = "\u001B[32m";
        String reset = "\u001B[0m";
        String yellow = "\u001B[33m";

        PrintWriter out = spec.commandLine().getOut();

        String headerFormat = "# %-3s %-" + maxDesc + "s  %-10s %s%n";
        out.printf(cyan + headerFormat + reset, "ID", "Description", "Amount", "Category");
        String rowFormat = "# %-3d  %-" + maxDesc + "s " + green + " R$%-8.2f " + reset + yellow + "%s" + reset + "%n";
        for (Expense expense : expenses) {
            out.printf(rowFormat,
                    expense.getId(),
                    expense.getDescription(),
                    expense.getValue(),
                    expense.getCategory());
        }

        return 0;
    }
}
