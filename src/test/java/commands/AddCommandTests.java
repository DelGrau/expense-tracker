import org.junit.jupiter.api.Test;
import picocli.CommandLine;
import java.io.PrintWriter;
import java.io.StringWriter;
import static org.junit.jupiter.api.Assertions.*;

public class AddCommandTest {

  @Test
  public void testPassingValidArgs() {
    AddCommand app = new AddCommand();
    CommandLine cmd = new CommandLine(app);

    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);
    cmd.setOut(new PrintWriter(sw));
    cmd.setErr(new PrintWriter(sw));

    int exitCode = cmd.execute("-d", "String de Teste", "-a", "10");
    pw.flush();
    
    String output = sw.toString();
    
    assertEquals(0, exitCode, "O comando deve terminar com sucesso (0)");
    assertTrue(output.contains("Expense added successfully (ID: 1)"));
  }
}
