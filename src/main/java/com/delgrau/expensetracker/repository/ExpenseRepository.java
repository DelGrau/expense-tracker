package com.delgrau.expensetracker.repository;

import com.delgrau.expensetracker.model.Amount;
import com.delgrau.expensetracker.model.Category;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.delgrau.expensetracker.model.Expense;

import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ExpenseRepository {
    private static final String FILE_PATH = "expenses.json";
    private Gson gson = new GsonBuilder()
            .registerTypeAdapter(Amount.class, (JsonSerializer<Amount>) (src, typeOfSrc, context) -> {
                return new JsonPrimitive(src.getCents());
            })
            .registerTypeAdapter(Amount.class, (JsonDeserializer<Amount>) (json, typeOfT, context) -> {
                int cents = json.getAsInt();
                return Amount.fromCurrency(cents / 100.0);
            })
            .registerTypeAdapter(Category.class, (JsonSerializer<Category>) (src, typpeOfSrc, context) -> {
                return new JsonPrimitive(src.getDescription());
            })
            .registerTypeAdapter(Category.class, (JsonDeserializer<Category>) (json, typeOfT, context) -> {
                return Category.fromString(json.getAsString());
            })
            .setPrettyPrinting()
            .create();

    public List<Expense> loadExpenses() {
        try {
            Path path = Paths.get(FILE_PATH);
            if (!Files.exists(path)) {
                return new ArrayList<>();
            }

            String json = Files.readString(path);

            Type listType = new TypeToken<ArrayList<Expense>>(){}.getType();
            return gson.fromJson(json, listType);

        } catch (Exception e) {
            System.err.println("Error reading the file: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void saveExpenses(List<Expense> expenses) {
        try {
            String json = gson.toJson(expenses);
            Files.writeString(Paths.get(FILE_PATH), json);
        } catch (Exception e) {
            System.err.println("Error saving the JSON file: " + e.getMessage());
        }
    }
}