package com.delgrau.expensetracker.model;

public class Expense {
    private long id = 0;
    private String description;
    private Amount value;

    public Expense (String d, Double v) {
        id++;
        this.description = d;
        this.value = Amount.fromCurrency(v);
    }

    public Expense (long i, String d, Double v) {
        this.id = i;
        this.description = d;
        this.value = Amount.fromCurrency(v);
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

    public String toString() {
        /*
         * # ID  Date       Description  Amount
         * # 1   2026-04-13  Test         R$20
         */

        return String.format("# ID  Description  Amount\n# %-3d  %-11s  R$%s", getId(), getDescription(), value.toString());
    }
}
