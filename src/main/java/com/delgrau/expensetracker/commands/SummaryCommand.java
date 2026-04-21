package com.delgrau.expensetracker.commands;

import com.delgrau.expensetracker.model.Amount;
import com.delgrau.expensetracker.model.Expense;
import com.delgrau.expensetracker.repository.ExpenseRepository;
import picocli.CommandLine.Spec;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Command;

import java.io.PrintWriter;
import java.util.List;
import java.util.concurrent.Callable;

@Command(name = "summary",
        description = "Summarize all expenses to a total.")
public class SummaryCommand implements Callable<Integer> {

    @Spec
    CommandSpec spec;

    @Override
    public Integer call() throws Exception {
        ExpenseRepository repo = new ExpenseRepository();

        List<Expense> expenses = repo.loadExpenses();

        long totalExpenses = 0;
        if (!expenses.isEmpty()){
            for (Expense e : expenses) {
                totalExpenses += e.getCents();
            }
        }

        PrintWriter out = spec.commandLine().getOut();
        out.printf("# Total expenses: R$%s", Amount.fromCents(totalExpenses));
        return 0;
    }
}
