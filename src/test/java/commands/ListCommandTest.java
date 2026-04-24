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
        data.add(new Expense(1, "Lunch", 123.45677d, "food"));
        data.add(new Expense(2, "Dinner", 20.00d, "food"));
        data.add(new Expense(3, "Orangutan - Bob Zovudo", 1000.00d, "pet"));
        data.add(new Expense(4, "Sofa", 5.5d, "house"));
        data.add(new Expense(5, "Sorbet", 999999d));

        app.getRepository().saveExpenses(data);

        int exitCode = cmd.execute();
        pw.flush();

        String output = sw.toString();
        System.out.println(output);

        assertEquals(0, exitCode, "Program should finish with success (0)");
        assertTrue(output.contains("Lunch"), "Expected `Lunch` at line `# 1`");
        assertEquals(20d, data.get(1).getValue(), "Expected `R$20,00` at line '# 2'");
        assertTrue(output.contains("Category"), "Expected `Category` at the header");
    }

    @Test
    public void testInvalidExpenseList() {
        List<Expense> data = new ArrayList<>();

        app.getRepository().saveExpenses(data);

        int exitCode = cmd.execute();
        pw.flush();

        String output = sw.toString();
        System.out.println("Passing an empty list:\n" + output);

        assertEquals(1, exitCode, "Program should finish with error (1)");
        assertTrue(output.contains("List is empty"));
    }

    @Test
    public void testNullExpenseList() {
        app.getRepository().saveExpenses(null);

        int exitCode = cmd.execute();
        pw.flush();

        String output = sw.toString();
        System.out.println("Passing null:\n" + output);

        assertEquals(1, exitCode, "Program should finish with error (1)");
        assertTrue(output.contains("Could not open file"));
    }
}
