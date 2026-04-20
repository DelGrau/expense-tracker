package com.delgrau.expensetracker.converter;

import picocli.CommandLine.ITypeConverter;
import com.delgrau.expensetracker.model.Amount;

public class AmountConverter implements ITypeConverter<Amount> {
    @Override
    public Amount convert(String value) throws Exception {
        double val = Double.parseDouble(value.replace(",", "."));
        return Amount.fromCurrency(val);
    }
}
