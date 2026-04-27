package repository;

import com.delgrau.expensetracker.commands.AddCommand;
import com.delgrau.expensetracker.model.Expense;
import com.delgrau.expensetracker.repository.CsvExpenseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import picocli.CommandLine;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CsvExpenseRepositoryTest {
    @TempDir
    private Path tempDir;

    private AddCommand app;
    private CommandLine cmd;
    private StringWriter sw;
    private PrintWriter pw;

    @BeforeEach
    public void setup() {
        sw = new StringWriter();
        pw = new PrintWriter(sw);

        List<Expense> expenses = new ArrayList<>();
        expenses.add(new Expense(1, "Chocolatey", 2.50d));
        expenses.add(new Expense(2, "Mufasa", 350.89d, "Disney"));

        app = new AddCommand();
        Path tempFile = tempDir.resolve("test-expenses.csv");

        var repo = new CsvExpenseRepository(tempFile.toString());
        repo.saveExpenses(expenses);
        app.setupTest(app, repo);

        cmd = new CommandLine(app);
        cmd.setOut(pw);
        cmd.setErr(pw);
    }

    @Test
    public void testSavingToCsvFile() {
        int exitCode = cmd.execute("-d", "Test", "-a", "10", "-c", "testing");
        pw.flush();

        String output = sw.toString();
        List<Expense> e = app.getRepository().loadExpenses();

        for (Expense expense : e) {
            System.out.println(expense);
        }
        assertEquals(0, exitCode, "Command should finish with a success (0)");
        assertTrue(output.contains("Expense added successfully"));
        assertEquals(3, e.size());
    }
}
