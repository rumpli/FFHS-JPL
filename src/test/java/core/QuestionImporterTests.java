package core;

import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class QuestionImporterTests {
  @Test
  public void TestImport() {
    QuestionImporter questionImporter = new QuestionImporter("src/test/resources/testquestions.txt");
    try {
      List<Question> questions = questionImporter.getQuestions();
      Assertions.assertEquals(3, questions.size());

      // First question
      Question first = questions.get(0);
      Assertions.assertEquals("Im Gegensatz zum «Derby» wird der «Oxford» in der Regel ...?", first.getQuestion());
      Assertions.assertEquals("parallel- und nicht kreuzgeschnürt", first.getAnswerA());
      Assertions.assertEquals("nur alle drei Tage gestutzt", first.getAnswerB());
      Assertions.assertEquals("nur mit Eingriff von links angeboten", first.getAnswerC());
      Assertions.assertEquals('a', first.getCorrectAnswer());

      // Second question
      Question second = questions.get(1);
      Assertions.assertEquals("Wie wollten französische Hofdamen im 18. Jahrhundert zeigen, dass sie sich vom einfachen Volk unterscheiden?", second.getQuestion());
      Assertions.assertEquals("Sie trugen keine Strümpfe, sodass ihre Knöchel sichtbar waren.", second.getAnswerA());
      Assertions.assertEquals("Sie zeichneten sich blaue Adern auf ihr Dekolleté.", second.getAnswerB());
      Assertions.assertEquals("Sie vermieden das Blinzeln, wenn sie mit Männern redeten.", second.getAnswerC());
      Assertions.assertEquals('b', second.getCorrectAnswer());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void TestImportWithNonExistingFile() {
    QuestionImporter questionImporter = new QuestionImporter("src/test/resources/doesnotexist");
    Assertions.assertThrows(java.lang.RuntimeException.class, questionImporter::getQuestions);
  }
}
