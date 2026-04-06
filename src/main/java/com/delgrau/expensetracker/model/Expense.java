package com.delgrau.expensetracker.model;

public class Expense {
    private long id;
    private String description;
    private Amount value;

    public Expense (String d, Double v) {
        this.description = d;
        this.value = Amount.fromCurrency(v);
    }

    public long getId() {
        return 0L;
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
}
