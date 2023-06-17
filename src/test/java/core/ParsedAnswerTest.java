package core;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ParsedAnswerTest {

    ParsedAnswer answer = new ParsedAnswer(true, "Test Answer");

    @Test
    void getAnswer() {
        assertEquals("Test Answer", answer.getAnswer());
    }

    @Test
    void isCorrect() {
        assertTrue(answer.isCorrect());
    }
}