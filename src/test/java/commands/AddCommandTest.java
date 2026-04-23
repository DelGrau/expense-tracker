package commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import picocli.CommandLine;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

import com.delgrau.expensetracker.commands.AddCommand;

public class AddCommandTest {
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

    app = new AddCommand();
    Path tempFile = tempDir.resolve("test-list.json");

    app.setupTest(app, tempFile);

    cmd = new CommandLine(app);
    cmd.setOut(pw);
    cmd.setErr(pw);
  }

  @Test
  public void testPassingValidArgs() {
    int exitCode = cmd.execute("-d", "Lunch", "-a", "10", "-c", "meals");
    pw.flush();
    
    String output = sw.toString();

    assertEquals(0, exitCode, "Command should finish with a success (0)");
    assertTrue(output.contains("Expense added successfully"));
    assertEquals(1, app.getRepository().loadExpenses().size());
  }

  @Test
  public void testPassingAmountWithComma() {
    int exitCode = cmd.execute("-d", "Dinner", "-a", "10,50");
    pw.flush();

    String output = sw.toString();

    assertEquals(0, exitCode, "Command should finish with a success (0)");
    assertTrue(output.contains("Expense added successfully"));
    assertEquals(10.5d, app.getRepository().loadExpenses().getFirst().getValue());
  }

  @Test
  public void testMissingAmountArgs() {
    int exitCode = cmd.execute("-d", "Amountless");
    pw.flush();

    String output = sw.toString();

    assertEquals(2, exitCode, "Command should not finish with a success (2)");
    assertTrue(output.contains("Missing required option: '--amount"));
    assertTrue(app.getRepository().loadExpenses().isEmpty());
  }

  @Test
  public void testMissingDescriptionArgs() {
    int exitCode = cmd.execute("-a", "20.5", "-c", "Descriptionless");
    pw.flush();

    String output = sw.toString();

    assertEquals(2, exitCode, "Command should not finish with a success (2)");
    assertTrue(output.contains("Missing required option: '--description"));
    assertTrue(app.getRepository().loadExpenses().isEmpty());
  }

  @Test
  public void testPassingStringAsAmount() {
    int exitCode = cmd.execute("-a", "Test", "-d", "String as Amount");

    String output = sw.toString();

    assertEquals(2, exitCode, "Command should not finish with a success (2)");
    assertTrue(output.contains("Invalid value for option '--amount'"));
    assertTrue(app.getRepository().loadExpenses().isEmpty());
  }
}
