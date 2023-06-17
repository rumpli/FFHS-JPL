package core;

/**
 * Object holding a question with 3 answers
 */
public class Question {

  private final String question;
  private final String answerA;
  private final String answerB;
  private final String answerC;
  public char correctAnswer;

  /**
   * Creates a new question object holding the question, answers and the correct answer
   * @param question the question as string
   * @param answerA the a answer as string
   * @param answerB the b answer as string
   * @param answerC the c answer as string
   * @param correctAnswer the correct answer as char (a, b or c)
   */
  public Question(String question, String answerA, String answerB, String answerC, char correctAnswer) {
    this.question = question;
    this.answerA = answerA;
    this.answerB = answerB;
    this.answerC = answerC;
    this.correctAnswer = Character.toLowerCase(correctAnswer);
  }

  /**
   * Returns the correct answer from the question object
   * @return the text of the correct answer or none if not found
   */
  public String getCorrectAnswerText() {
    return switch (correctAnswer) {
      case 'a' -> answerA;
      case 'b' -> answerB;
      case 'c' -> answerC;
      default -> "No correct answer found";
    };
  }

  /**
   * Returns the question from the question object
   * @return the text of the question
   */
  public String getQuestion() {
    return question;
  }

  /**
   * Returns the answer of a from the question object
   * @return the text of answer a
   */
  public String getAnswerA() {
    return answerA;
  }

  /**
   * Returns the answer of b from the question object
   * @return the text of answer b
   */
  public String getAnswerB() {
    return answerB;
  }

  /**
   * Returns the answer of c from the question object
   * @return the text of answer c
   */
  public String getAnswerC() {
    return answerC;
  }

  /**
   * Returns the correct answer from the question object
   * @return the correct answer a, b or c
   */
  public char getCorrectAnswer() {
    return correctAnswer;
  }
}
