package commands;

import static org.junit.jupiter.api.Assertions.*;

import com.delgrau.expensetracker.commands.DeleteCommand;
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


public class DeleteCommandTest {
    @TempDir
    private Path tempDir;

    private DeleteCommand app;
    private CommandLine cmd;
    private StringWriter sw;
    private PrintWriter pw;

    @BeforeEach
    public void setup() {
        sw = new StringWriter();
        pw = new PrintWriter(sw);

        app = new DeleteCommand();
        Path tempFile = tempDir.resolve("test-delete.json");

        app.setupTest(app, tempFile);

        List<Expense> initialData = new ArrayList<>();
        initialData.add(new Expense(
                        1,
                        "Netflix",
                        55.90d,
                        "Streaming"
                )
        );
        initialData.add(new Expense(
                        2,
                        "YouTube",
                        25.10d,
                        "Streaming"
                )
        );

        app.getRepository().saveExpenses(initialData);

        cmd = new CommandLine(app);
        cmd.setOut(pw);
        cmd.setErr(pw);
    }

    @Test
    public void testDeleteExistingExpense() {
        int exitCode = cmd.execute("--id", "1");
        pw.flush();

        String output = sw.toString();

        List<Expense> afterDelete = app.getRepository().loadExpenses();

        assertEquals(0, exitCode, "Should be able to delete the expense. Expected code (0)");
        assertTrue(output.contains("deleted successfully"));
        assertEquals(1, afterDelete.size());
        assertEquals(2, afterDelete.getFirst().getId(), "We are deleting expense with id (1)");
    }

    @Test
    public void testDeletingInvalidExpense() {
        int exitCode = cmd.execute("--id", "3");
        pw.flush();

        String output = sw.toString();

        assertEquals(1, exitCode, "Should not be able to delete said expense (ID:3)");
        assertTrue(output.contains("Could not delete"));
    }
}
