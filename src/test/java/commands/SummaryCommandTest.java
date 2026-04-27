package commands;

import static org.junit.jupiter.api.Assertions.*;

import com.delgrau.expensetracker.commands.SummaryCommand;
import com.delgrau.expensetracker.model.Expense;
import com.delgrau.expensetracker.repository.JsonExpenseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import picocli.CommandLine;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class SummaryCommandTest {
    @TempDir
    private Path tempDir;

    private SummaryCommand app;
    private CommandLine cmd;
    private StringWriter sw;
    private PrintWriter pw;

    @BeforeEach
    public void setup() {
        sw = new StringWriter();
        pw = new PrintWriter(sw);

        app = new SummaryCommand();
        Path tempFile = tempDir.resolve("test-summary.json");

        var repo = new JsonExpenseRepository(tempFile.toString());
        app.setupTest(app, repo);

        List<Expense> initialData = new ArrayList<>();
        initialData.add(new Expense(
                1,
                "Lunch",
                10d
        ));
        initialData.add(new Expense(
                2,
                "Dinner",
                20d
        ));

        app.getRepository().saveExpenses(initialData);

        cmd = new CommandLine(app);
        cmd.setOut(pw);
        cmd.setErr(pw);
    }

    @Test
    public void testSummaryValidExpenses() {
        int exitCode = cmd.execute();
        pw.flush();

        String output = sw.toString();

        assertEquals(0, exitCode);
        assertTrue(output.contains("R$30,00"));
    }

    @Test
    public void testSummaryWithEmptyFile() {
        List<Expense> emptyList = new ArrayList<>();
        app.getRepository().saveExpenses(emptyList);

        int exitCode = cmd.execute();
        pw.flush();

        String output = sw.toString();

        assertEquals(1, exitCode, "Should not summarize a list with no expenses (0)");
        assertTrue(output.contains("There are no expenses"));
    }
}
