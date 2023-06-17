package core;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.assertEquals;

// Unit Test for System.out https://stackoverflow.com/questions/1119385/junit-test-for-system-out-println
public class TerminalTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void ShowMessage() {
        Terminal ui = new Terminal();
        ui.showMessage("Test Message ÄÖÜäöü");
        assertEquals("Test Message ÄÖÜäöü", outContent.toString().trim());
    }

    @Test
    public void ShowErrorMessage() {
        Terminal ui = new Terminal();
        ui.showMessage("Test Error Message ÄÖÜäöü");
        assertEquals("Test Error Message ÄÖÜäöü", outContent.toString().trim());
    }
}
