package com.delgrau.expensetracker.model;

import picocli.CommandLine;

@CommandLine.Command(name = "expense",
        mixinStandardHelpOptions = true,
        description = "Add a new expense to the user repo.")
public class Expense implements Runnable {
    @CommandLine.Option(names = {"-d", "--description"}, description = "What is the expense")
    private final String description;
    @CommandLine.Option(names = {"-v", "--value"}, description = "How much was spent")
    private final Amount value;

    public Expense(String description, Amount value) {
        this.description = description;
        this.value = value;
    }

    public double getValue() {
        return value.getAmount();
    }

    public double getValueInCents() {
        return value.getCents();
    }

    public String getDescription() {
        return description;
    }

    @Override
    public void run() {
        System.out.println("Isso veio do Expense");
    }

    /**
     * Application should run from the command line and should have the following features:
     * - Users can add an expense with a description and amount.
     * - Users can update an expense.
     * - Users can delete an expense.
     * - Users can view all expenses.
     * - Users can view a summary of all expenses.
     * - Users can view a summary of expenses for a specific month (of current year).
     * Here are some additional features that you can add to the application:
     * - Add expense categories and allow users to filter expenses by category.
     * - Allow users to set a budget for each month and show a warning when the user exceeds the budget.
     * - Allow users to export expenses to a CSV file.
     * - The list of commands and their expected output is shown below:
     */
}
