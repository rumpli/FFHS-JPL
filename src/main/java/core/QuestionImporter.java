package core;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to import and parse a question file
 */
public class QuestionImporter {

  private final String filePathString;

  /**
   * Takes a file path as string
   * @param filePathString path to file as a string
   */
  public QuestionImporter(String filePathString) {
    this.filePathString = filePathString;
  }

  /**
   * Read all questions for the given file
   * @return List of Questions
   * @throws IOException if the file cannot be read
   */
  public List<Question> getQuestions() throws IOException {

    List<String> questionList;
    String question, answerA, answerB, answerC;
    char correctAnswer = '0';
    ArrayList<Question> questions = new ArrayList<>();
    Path filePath = Paths.get(filePathString);

    // Read all lines
    try {
      questionList = Files.readAllLines(filePath);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    for (int index = 0; index < questionList.size(); index++) {
      // Match the question
      if (questionList.get(index).matches("^\\#\\sFrage\\s\\d+$")) {
        // if found/match set the next line as game question
        question = questionList.get(++index);

        ParsedAnswer answer = getAnswer(questionList.get(++index));
        answerA = answer.getAnswer();
        if (answer.isCorrect()) {
          correctAnswer = 'A';
        }

        answer = getAnswer(questionList.get(++index));
        answerB = answer.getAnswer();
        if (answer.isCorrect()) {
          correctAnswer = 'B';
        }

        answer = getAnswer(questionList.get(++index));
        answerC = answer.getAnswer();
        if (answer.isCorrect()) {
          correctAnswer = 'C';
        }

        questions.add(new Question(question, answerA, answerB, answerC, correctAnswer));
      }
    }
    return questions;
  }

  /**
   * Parses an input as an answer
   * @param input string input to parse
   * @return the Answer and whether if it's correct
   */
  private ParsedAnswer getAnswer(String input) {
    boolean correct = false;
    int index = 2;
    // Find correct answer marked with an *
    if (input.matches("^[ABC]\\*.+")) {
      correct = true;
      index = 3;
    }
    return new ParsedAnswer(correct, input.substring(index));
  }
}
