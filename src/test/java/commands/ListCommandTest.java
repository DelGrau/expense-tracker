package commands;

import org.junit.jupiter.api.Test;
import picocli.CommandLine;
import java.io.PrintWriter;
import java.io.StringWriter;
import static org.junit.jupiter.api.Assertions.*;

import com.delgrau.expensetracker.commands.ListCommand;

public class ListCommandTest {
    final ListCommand app = new ListCommand();
    final CommandLine cmd = new CommandLine(app);
    final StringWriter sw = new StringWriter();
    final PrintWriter pw = new PrintWriter(sw);

    @Test
    public void testListCommand() {
        cmd.setOut(pw);
        cmd.setErr(pw);

        int exitCode = cmd.execute();
        pw.flush();

        String output = sw.toString();

        if (exitCode != 0) {
            System.out.println("ERROR: " + output);
        }

        System.out.println(output);
        assertEquals(0, exitCode, "Program should finish with success (0)");
        assertTrue(output.contains("#"));
    }
}
