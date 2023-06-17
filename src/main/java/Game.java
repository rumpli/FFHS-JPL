import core.QuestionImporter;
import core.Question;
import core.Terminal;
import client.Client;
import server.Server;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * Main Class to run the game, can be used as server or client
 */
public class Game implements Runnable {

  // use 60'000 as default port
  private static final int PORT = 60000;
  private final Terminal terminal = new Terminal();

  // Main method to run
  public static void main(String[] args) {
    new Game().run();
  }

  /**
   * Displays the start screen
   * User can decide to start a new game as server or to join a game as a client
   */
  @Override
  public void run() {
    switch (terminal.showStartScreen()) {
      // Starts the server
      case 'n' -> createGame();
      // Starts the client
      case 'j' -> joinGame();
      default -> terminal.showMessage("Exiting game...");
    }
  }

  /**
   * Start a new game as server
   */
  private void createGame() {
    int players, numberOfQuestions;
    List<Question> questions;
    QuestionImporter questionsFile;
    Server server;

    try {
      questionsFile = new QuestionImporter("src/main/resources/questions.txt");
      questions = questionsFile.getQuestions();
      terminal.showMessage(questions.size() + " questions loaded");
    } catch (FileNotFoundException e) {
      terminal.showError("Questions file not found");
      return;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    // Ask user how many players and how many questions
    players = terminal.getPlayerCount();
    numberOfQuestions = terminal.getNumberOfQuestions(questions.size());

    try {
      server = new Server(terminal, PORT, players, randomQuestions(questions, numberOfQuestions));
      terminal.showMessage("New game created");
      terminal.showMessage("Settings:");
      terminal.showMessage(" - " + players + " players");
      terminal.showMessage(" - " + numberOfQuestions + " questions");
      // Start server thread
      server.run();
    } catch (IOException e) {
      terminal.showError("Error while creating game: " + e.getMessage());
    }
  }

  /**
   * Start a game as a client and join a server
   */
  private void joinGame() {
    Client client;

    try {
      client = new Client(terminal, PORT);
      // Start client thread
      client.run();
    } catch (IOException e) {
      terminal.showError("Failed to join: " + e.getMessage());
    }
  }

  /**
   * Get random questions back
   * https://stackoverflow.com/questions/7191325/get-a-random-subset-from-a-result-set-in-java
   * @param questions list of questions to be shuffled randomly
   * @param numberOfQuestions number of questions to be returned
   * @return random shuffled set of questions
   */
  public static Set<Question> randomQuestions(List<Question> questions, int numberOfQuestions) {
    List<Question> list = new LinkedList<>(questions);
    Collections.shuffle(list);
    return new HashSet<>(list.subList(0, numberOfQuestions));
  }
}
