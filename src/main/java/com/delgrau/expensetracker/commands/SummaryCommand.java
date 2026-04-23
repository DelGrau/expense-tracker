package com.delgrau.expensetracker.commands;

import com.delgrau.expensetracker.model.Amount;
import com.delgrau.expensetracker.model.Expense;
import picocli.CommandLine.Spec;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Command;

import java.io.PrintWriter;
import java.util.List;

@Command(name = "summary",
        description = "Summarize all expenses to a total.")
public class SummaryCommand extends BaseCommand {

    @Spec
    CommandSpec spec;

    @Override
    public Integer call() throws Exception {
        List<Expense> expenses = repo.loadExpenses();
        PrintWriter out = spec.commandLine().getOut();

        if (expenses.isEmpty()){
            out.println("There are no expenses");
            return 1;
        }

        long totalExpenses = 0;
        for (Expense e : expenses) {
            totalExpenses += e.getCents();
        }

        out.printf("# Total expenses: R$%s%n", Amount.fromCents(totalExpenses));
        return 0;
    }
}
