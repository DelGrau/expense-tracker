package commands;

import com.delgrau.expensetracker.model.Expense;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import picocli.CommandLine;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import com.delgrau.expensetracker.commands.ListCommand;

public class ListCommandTest {
    @TempDir
    private Path tempDir;

    private ListCommand app;
    private CommandLine cmd;
    private StringWriter sw;
    private PrintWriter pw;

    @BeforeEach
    public void setup() {
        sw = new StringWriter();
        pw = new PrintWriter(sw);

        app = new ListCommand();
        Path tempFile = tempDir.resolve("test-list.json");

        app.setupTest(app, tempFile);

        cmd = new CommandLine(app);
        cmd.setOut(pw);
        cmd.setErr(pw);
    }

    @Test
    public void testListCommand() {
        List<Expense> data = new ArrayList<>();
        data.add(new Expense(1, "Lunch", 15.50d));
        data.add(new Expense(2, "Dinner", 20.00d));
        app.getRepository().saveExpenses(data);

        int exitCode = cmd.execute();
        pw.flush();

        String output = sw.toString();

        assertEquals(0, exitCode, "Program should finish with success (0)");
        assertTrue(output.contains("Lunch"));
        assertTrue(output.contains("R$20,00"));
        assertTrue(output.contains("Category"));
    }
}
