import core.Question;
import core.QuestionImporter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.List;
import java.util.Set;


class GameTest {
    QuestionImporter questionImporter = new QuestionImporter("src/test/resources/testquestions_full.txt");
    @Test
    void RandomizedQuestions() {
        try {
            List<Question> questions = questionImporter.getQuestions();
            Set<Question> shuffledQuestions = Game.randomQuestions(questions, 39);

            Assertions.assertEquals(39, questions.size());
            Assertions.assertNotEquals(questions, shuffledQuestions);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}