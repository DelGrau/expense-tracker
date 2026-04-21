package model;

import com.delgrau.expensetracker.model.Amount;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AmountTest {
    @Test
    public void testWhenCreatingWithInteger () {
        var value = Amount.fromCurrency(10);

        assertEquals(10.00, value.getAmount());
        assertEquals(1000L, value.getCents());
    }

    @Test
    public void testWhenCreatingWithFloat () {
        var value =Amount.fromCurrency(10.50f);

        assertEquals(10.50, value.getAmount());
        assertEquals(1050L, value.getCents());
    }

    @Test
    public void testWhenCreatingWithLong () {
        var value = Amount.fromCents(1005);

        assertEquals(10.05, value.getAmount());
        assertEquals(1005L, value.getCents());
    }
}

