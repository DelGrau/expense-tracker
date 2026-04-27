package com.delgrau.expensetracker.repository;

import com.delgrau.expensetracker.model.Expense;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.CSVWriterBuilder;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class CsvExpenseRepository extends BaseFileRepository{

    public CsvExpenseRepository() {
        this("expenses.csv");
    }

    public CsvExpenseRepository(String filePath) {
        super(filePath);

    }

    @Override
    public List<Expense> loadExpenses() {
        List<Expense> expenses = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader(this.filePath))) {
            String[] line;
            boolean isFirstLine = true;

            while ((line = reader.readNext()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                long id = Integer.parseInt(line[0]);
                String description = line[1];
                double value = Double.parseDouble(line[2]);
                String category = line[3];

                Expense expense = new Expense(id, description, value, category);
                expenses.add(expense);
            }
        } catch (Exception e) {
            System.err.println("Error loading expenses from CSV file: " + e.getMessage());
        }

        return expenses;
    }

    @Override
    public void saveExpenses(List<Expense> expenses) {
        try (FileWriter fileWriter = new FileWriter(this.filePath)) {
            CSVWriter writer = (CSVWriter) new CSVWriterBuilder(fileWriter)
                    .withQuoteChar(CSVWriter.NO_QUOTE_CHARACTER)
                    .build();

            String[] header = {"ID", "Description", "Value", "Category"};
            writer.writeNext(header);

            for (Expense e : expenses) {
                String idStr = String.valueOf(e.getId());
                String descStr = e.getDescription();
                String valueStr = String.valueOf(e.getValue());
                String catStr = e.getCategory();

                String[] row = {idStr, descStr, valueStr, catStr};
                writer.writeNext(row);
            }

            writer.close();
        } catch (Exception e) {
            System.err.println("Error saving expenses to CSV file: " + e.getMessage());
        }

    }
}
