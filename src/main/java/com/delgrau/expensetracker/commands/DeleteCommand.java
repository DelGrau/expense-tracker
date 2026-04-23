package com.delgrau.expensetracker.commands;

import com.delgrau.expensetracker.model.Expense;
import picocli.CommandLine.Option;
import picocli.CommandLine.Command;
import picocli.CommandLine.Spec;
import picocli.CommandLine.Model.CommandSpec;

import java.util.List;

@Command(name = "delete",
        description = "Delete a expense by passing its ID.")
public class DeleteCommand extends BaseCommand {

    @Spec
    private CommandSpec spec;

    @Option(names = {"--id"}, required = true, description = "Expense ID to be deleted.")
    private int expenseID;

    @Override
    public Integer call() throws Exception {
        List<Expense> expenses = repo.loadExpenses();

        if (expenses.isEmpty()) {
            spec.commandLine().getErr().println("# List is empty.");
            return 1;
        }

        boolean removed = expenses.removeIf(
                e -> e.getId() == expenseID
        );

        if (removed) {
            repo.saveExpenses(expenses);
            spec.commandLine().getOut().println("# Expense deleted successfully");
            return 0;
        }

        spec.commandLine().getErr().println("# Could not delete expense with ID:" + expenseID);
        return 1;
    }
}
