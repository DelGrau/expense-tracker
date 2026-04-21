package commands;

import org.junit.jupiter.api.Test;
import picocli.CommandLine;
import java.io.PrintWriter;
import java.io.StringWriter;
import static org.junit.jupiter.api.Assertions.*;

import com.delgrau.expensetracker.commands.AddCommand;

public class AddCommandTest {
  final AddCommand app = new AddCommand();
  final CommandLine cmd = new CommandLine(app);
  final StringWriter sw = new StringWriter();
  final PrintWriter pw = new PrintWriter(sw);

  @Test
  public void testPassingValidArgs() {
    cmd.setOut(pw);
    cmd.setErr(pw);

    int exitCode = cmd.execute("-d", "Valid Expense", "-a", "10", "-c", "Valid");
    pw.flush();
    
    String output = sw.toString();
    
    assertEquals(0, exitCode, "Command should finish with a success (0)");
    assertTrue(output.contains("Expense added successfully"));
  }

  @Test
  public void testPassingAmountWithComma() {
    cmd.setOut(pw);
    cmd.setErr(pw);

    int exitCode = cmd.execute("-d", "Amount w/ Comma", "-a", "10,50");
    pw.flush();

    String output = sw.toString();

    assertEquals(0, exitCode, "Command should finish with a success (0)");
    assertTrue(output.contains("Expense added successfully"));
  }

  @Test
  public void testMissingAmountArgs() {
    cmd.setOut(pw);
    cmd.setErr(pw);

    int exitCode = cmd.execute("-d", "Amountless");
    pw.flush();

    String output = sw.toString();

    assertEquals(2, exitCode, "Command should not finish with a success (2)");
    assertTrue(output.contains("Missing required option: '--amount"));
  }

  @Test
  public void testMissingDescriptionArgs() {
    cmd.setOut(pw);
    cmd.setErr(pw);

    int exitCode = cmd.execute("-a", "20.5", "-c", "Descriptionless");
    pw.flush();

    String output = sw.toString();

    assertEquals(2, exitCode, "Command should not finish with a success (2)");
    assertTrue(output.contains("Missing required option: '--description"));
  }

  @Test
  public void testPassingStringAsAmount() {
    cmd.setOut(pw);
    cmd.setErr(pw);

    int exitCode = cmd.execute("-a", "Test", "-d", "String as Amount");

    String output = sw.toString();

    assertEquals(2, exitCode, "Command should not finish with a success (2)");
    assertTrue(output.contains("Invalid value for option '--amount'"));
  }
}
