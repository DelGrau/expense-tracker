package com.delgrau.expensetracker.commands;

import com.delgrau.expensetracker.model.Expense;
import picocli.CommandLine.Command;
import picocli.CommandLine.Spec;
import picocli.CommandLine.Model.CommandSpec;

import java.io.PrintWriter;
import java.util.List;
import java.util.function.Function;

@Command(name = "list",
        description = "Show a detailed list of expenses.")
public class ListCommand extends BaseCommand {

    @Spec
    CommandSpec spec;

    @Override
    public Integer call() throws Exception {
        List<Expense> expenses = repo.loadExpenses();

        if (!isListValid(expenses)) {
            return 1;
        }

        int minSize = "Description".length();
        int maxDesc = calculateColumnWidth(
                expenses, Expense::getDescription, minSize);

        minSize = "Amount".length();
        int maxAmount = calculateColumnWidth(
                expenses, e -> String.format("R$%.2f", e.getValue()), minSize);

        String cyan = "\u001B[36m";
        String green = "\u001B[32m";
        String reset = "\u001B[0m";
        String yellow = "\u001B[33m";

        PrintWriter out = spec.commandLine().getOut();

        String headerFormat = "# %-3s %-" + maxDesc + "s  %-" + maxAmount + "s  %s%n";
        out.printf(cyan + headerFormat + reset, "ID", "Description", "Amount", "Category");
        String rowFormat = "# %-3d  %-" + maxDesc + "s  " + green + "%-" + maxAmount +"s " + reset + yellow + " %s" + reset + "%n";

        for (Expense expense : expenses) {
            String formattedAmount = String.format("R$%.2f", expense.getValue());

            out.printf(rowFormat,
                    expense.getId(),
                    expense.getDescription(),
                    formattedAmount,
                    expense.getCategory());
        }

        return 0;
    }

    /**
     * Validates if given list is valid.
     *
     * @param list  List to be verified
     * @return      Returns true if the list is valid, otherwise, returns false
     */
    private <T> boolean isListValid(List<T> list) {
        PrintWriter err = spec.commandLine().getErr();
        if (list == null) {
            err.println("Could not open file");
            return false;
        }

        if (list.isEmpty()) {
            err.println("List is empty");
            return false;
        }

        return true;
    }

    /**
     * Calculates the maximum width of a column based on the data content
     * and a minimum value (usually the header's length).
     *
     * @param <T>      The type of the object in the list (e.g., Expense)
     * @param list     The list of data
     * @param mapper   Function that transforms the object into the String to be displayed
     * @param minWidth Minimum width (the length of the column title)
     * @return The ideal width for the column
     */
    private <T> int calculateColumnWidth(
            List<T> list,
            Function<T, String> mapper,
            int minWidth)
    {
        int maxContentWidth = list.stream()
                .map(mapper)
                .mapToInt(String::length)
                .max()
                .orElse(minWidth);

        return Math.max(maxContentWidth, minWidth);
    }
}
