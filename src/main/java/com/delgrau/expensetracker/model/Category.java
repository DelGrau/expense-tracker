package com.delgrau.expensetracker.model;

public class Category {
    private final String description;

    private Category(String description) {
        this.description = description;
    }

    public static Category fromString (String categoryDescription) {
        if (categoryDescription.isBlank()) return new Category("-");

        return new Category(categoryDescription.toUpperCase().trim());
    }

    public String getDescription() {
        return description;
    }
}
