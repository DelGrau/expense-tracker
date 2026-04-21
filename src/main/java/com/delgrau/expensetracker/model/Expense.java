package com.delgrau.expensetracker.model;

public class Expense {
    private long id = 0;
    private final String description;
    private final Amount value;
    private final Category category;

    public Expense (String d, Double v) {
        id++;
        this.description = d;
        this.value = Amount.fromCurrency(v);
        this.category = Category.fromString("");
    }

    public Expense (long i, String d, Double v) {
        this.id = i;
        this.description = d;
        this.value = Amount.fromCurrency(v);
        this.category = Category.fromString("");
    }

    public Expense (long i, String d, Double v, String c) {
        this.id = i;
        this.description = d;
        this.value = Amount.fromCurrency(v);
        this.category = Category.fromString(c);
    }

    public long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public double getValue() {
        return value.getAmount();
    }

    public long getCents() {
        return value.getCents();
    }

    public String getCategory() {
        return category.getDescription();
    }

    public String toString() {
        /*
         * # ID  Date       Description  Amount  Category
         * # 1   2026-04-13  Test         R$20    Testing
         */

        return String.format("# ID  Description  Amount\n# %-3d  %-11s  R$%-5s %s",
                getId(), getDescription(), value.toString(), getCategory());
    }
}
