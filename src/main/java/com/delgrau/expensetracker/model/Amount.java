package com.delgrau.expensetracker.model;

public class Amount {
    private final long cents;

    private Amount (long cents) {
        this.cents = cents;
    }

    public static Amount fromCents (long cents) {
        return new Amount(cents);
    }

    public static Amount fromCurrency (int value) {
        return new Amount(value * 100L);
    }

    public static Amount fromCurrency (float value) {
        return new Amount(Math.round(value * 100.0f));
    }

    public long getCents () {
        return cents;
    }

    public double getAmount () {
        return cents / 100.0;
    }
}

