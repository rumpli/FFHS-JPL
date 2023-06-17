package server;

import core.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Game server that opens and provides a game over a socket (IP:PORT)
 */
public class Server {

  private final ExecutorService threadPool;
  private final int numberOfPlayers;
  private final ServerSocket serverSocket;
  private final PlayerHandler[] players;
  private final Set<Question> questions;
  private final Terminal terminal;
  private final HousePoints housePoints;
  private final ConcurrentLinkedQueue<Answer> answers;
  private CountDownLatch countDownLatch;

  /**
   * Creates a new game server
   * @param terminal the terminal object for interaction with the user
   * @param port integer of Port server opens and listens to for clients
   * @param numberOfPlayers how many players are needed to start the game
   * @param questions questions for the game
   * @throws IOException if the socket cannot be opened
   */
  public Server(Terminal terminal, int port, int numberOfPlayers, Set<Question> questions) throws IOException {
    this.housePoints = new HousePoints() {};
    this.terminal = terminal;
    this.serverSocket = new ServerSocket(port);
    this.numberOfPlayers = numberOfPlayers;
    this.questions = questions;
    // Set amount of threads to the amount of players for the game round
    this.threadPool = Executors.newFixedThreadPool(numberOfPlayers);
    this.players = new PlayerHandler[numberOfPlayers];
    this.answers = new ConcurrentLinkedQueue<>();
  }

  /**
   * Run the game server
   */
  public void run() {

    try {
      // Wait for all the players to open a connection to the server (aka joins)
      for (int index = 0; index < players.length; index++) {
        // Easter egg
        PlayerHandler.House house;
        if (index % 4 == 0) {
          house = PlayerHandler.House.Gryffindor;
        } else if (index % 4 == 1) {
          house = PlayerHandler.House.Hufflepuff;
        } else if (index % 4 == 2) {
          house = PlayerHandler.House.Ravenclaw;
        } else {
          house = PlayerHandler.House.Slytherin;
        }
        players[index] = new PlayerHandler(serverSocket.accept(), this, index, house);
        // Starts the player handler
        threadPool.execute(players[index]);
        Thread.sleep(1000);
        sendMessage("Players joined: " + (index + 1) + "/" + players.length);
      }

      // Start the countdown to the game
      initialCountdown();

      for (Question question : questions) {
        // Create a new count-down latch
        countDownLatch = new CountDownLatch(numberOfPlayers);
        sendQuestion(question);
        countDownLatch.await();
        analyseResult(question);
        answers.clear();
        questionCountdown();
      }

      sendLeaderBoard(players);
      sendMessage("\nThe game ended. Server shutdown...");
      // Ends all player handler
      endPlayerHandler();
    } catch (IOException e) {
      terminal.showError("Could not establish connections: " + e.getMessage());
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }

  /**
   * Sends the initial start countdown to all the players
   * @throws InterruptedException if thread would be interrupted
   */
  private void initialCountdown() throws InterruptedException {
    sendMessage(dashedLine(50));
    Thread.sleep(1000);
    sendMessage("All " + numberOfPlayers + " players have joined");
    sendMessage("The game will start in 5 seconds, get ready!");
    sendMessage(dashedLine(50));
    Thread.sleep(1000);
    sendMessage("5.....");
    Thread.sleep(1000);
    sendMessage("4....");
    Thread.sleep(1000);
    sendMessage("3...");
    Thread.sleep(1000);
    sendMessage("2..");
    Thread.sleep(1000);
    sendMessage("1!\n");
    Thread.sleep(1000);
  }

  /**
   * Sends an in between questions countdown to all players
   * @throws InterruptedException if thread would be interrupted
   */
  private void questionCountdown() throws InterruptedException {
    sendMessage(dashedLine(50));
    Thread.sleep(1000);
    sendMessage("Next question in 3 seconds!");
    Thread.sleep(1000);
    sendMessage("3...");
    Thread.sleep(1000);
    sendMessage("2..");
    Thread.sleep(1000);
    sendMessage("1!\n");
    Thread.sleep(1000);
  }

  /**
   * Send a question with its answer possibilities
   * @param question the question to show
   */
  private void sendQuestion(Question question) {
    sendMessage(dashedLine(50));
    sendMessage(question.getQuestion());
    printAnswer('a', question.getAnswerA());
    printAnswer('b', question.getAnswerB());
    printAnswer('c', question.getAnswerC());
    // Request from client to get an answer
    sendMessage("GetAnswer");
  }

  /**
   * Send the message string to all players
   * @param message to be sent
   */
  private void sendMessage(String message) {
    for (PlayerHandler player : players) {
      if (player == null) {
        continue;
      }
      player.sendMessage(message);
    }
    terminal.showMessage(message);
  }

  /**
   * Sends the answers and prints them
   * @param prefix identification of the answer (a, b or c)
   * @param answer string of the answer itself
   */
  private void printAnswer(char prefix, String answer) {
    // Correct answers have leading * therefor it shouldn't be sent
    if (answer.startsWith("*")) {
      answer = answer.substring(1);
    }
    sendMessage(String.format("%s) %s\n", prefix, answer).trim());
  }

  /**
   * After all players have responded, analyse the answers and print a result
   * @param question the current question
   */
  private void analyseResult(Question question) {
    List<Answer> correctGuesses = answers.stream().filter(x -> x.answer() == question.correctAnswer).toList();
    Optional<Answer> winner = correctGuesses.stream().findFirst();
    if (winner.isPresent()) {
      winner.get().player().addPoints(1);
      sendResult(question, correctGuesses.size(), winner.get().player());
    } else {
      sendResult(question, 0, null);
    }
  }

  /**
   * Prints the correct answer
   * @param question the question that was asked
   * @param correctGuesses amount of players that guessed correctly
   * @param winner the player with the fastest correct answer if existence
   */
  private void sendResult(Question question, int correctGuesses, Player winner) {
    sendMessage(String.format("The correct answer was '%s' (%s)", question.correctAnswer, question.getCorrectAnswerText()));
    sendMessage(String.format("Correctly guessed by %d players.\n", correctGuesses));
    if (winner != null) {
      sendMessage(String.format("%s guessed correctly and was the fastest player, he got one point!", winner.getName()));
      // Easter egg
      for (PlayerHandler player : players) {
        if (player.getName().equals(winner.getName())) {
          if (player.getHouse().equals("Gryffindor")) {
            housePoints.addGryffindorPoints(1);
            sendMessage("One extra point for Gryffindor!\n");
          } else if (player.getHouse().equals("Hufflepuff")) {
            housePoints.addHufflepuffPoints(1);
            sendMessage("One extra point for Hufflepuff!\n");
          } else if (player.getHouse().equals("Racenclaw")) {
            housePoints.addRavenclawPoints(1);
            sendMessage("One extra point for Ravenclaw!\n");
          } else {
            housePoints.addSlytherinPoints(1);
            sendMessage("One extra point for Slytherin!\n");
          }
        }
      }
    }
  }

  /**
   * Send the leader board to all players
   * @param players the players who played and should be in the ranking
   * @throws InterruptedException if the thread would be interrupted
   */
  private void sendLeaderBoard(Player[] players) throws InterruptedException {
    int previousPoints = -1;
    int previousRank = 0;

    sendMessage(dashedLine(50));
    sendMessage("And this were all questions!\n");
    Thread.sleep(1000);
    sendMessage("Let's see how you all did.\n");
    Thread.sleep(1000);
    sendMessage("Calculating results...\n");
    Thread.sleep(2000);
    sendMessage("The results are in: ");

    List<Player> playersSorted = Arrays.stream(players).sorted(Comparator.comparingInt(Player::getPoints).reversed()).toList();
    for (int index = 0; index < players.length; index++) {
      Thread.sleep(2000);
      Player player = playersSorted.get(index);
      // Player with the same points share the same rank
      if (player.getPoints() != previousPoints) {
        previousRank = index + 1;
        previousPoints = player.getPoints();
      }
      sendMessage(String.format("%d. player %s (%d points)", previousRank, player.getName(), player.getPoints()));
    }

    // Easter egg
    Thread.sleep(1000);
    sendMessage(dashedLine(50));
    sendMessage("And the results for the houses: ");
    Thread.sleep(2000);
    sendMessage("Gryffindor: " + housePoints.getGryffindorPoints());
    Thread.sleep(1000);
    sendMessage("Hufflepuff: " + housePoints.getHufflepuffPoints());
    Thread.sleep(1000);
    sendMessage("Racenclaw: " + housePoints.getRavenclawPoints());
    Thread.sleep(1000);
    sendMessage("Slytherin: " + housePoints.getSlytherinPoints());
    Thread.sleep(2000);
  }

  /**
   * Handle input from a player (or client)
   * @param command command to process for the player
   * @param player the origin of the message
   */
  public void handlePlayerInput(String command, PlayerHandler player) {
    if (command == null) {
      return;
    }
    // Split the message and then trim it
    // https://stackoverflow.com/questions/41953388/java-split-and-trim-in-one-shot
    String[] parts = Arrays.stream(command.split(":")).map(String::trim).toArray(String[]::new);
    switch (parts[0]) {
      case "SetName" -> {
        // Set player name
        player.setName(parts[1]);
        sendMessage(String.format("Player %s joined", player.getName()));
        // Easter egg
        sendMessage(String.format("Player %s has joined the %s house!", player.getName(), player.getHouse()));
      }
      case "Answer" -> {
        // Add the answer and set countDownLatch
        answers.add(new Answer(parts[1].charAt(0), player));
        countDownLatch.countDown();
      }
      default -> {
        System.out.println("Something went wrong...");
      }
    }
  }

  /**
   * Stops all player handler
   */
  private void endPlayerHandler() {
    // End player handler threads
    for (PlayerHandler playerHandler : players) {
      if (playerHandler != null) {
        playerHandler.end();
      }
    }
  }

  /**
   * Helper function to create a dashed line for messages
   * @param countOfDashes amount of dashed line to be produced
   * @return the dashed line
   */
  public String dashedLine(int countOfDashes) {
    return "-".repeat(countOfDashes);
  }

  /**
   * Get terminal
   * @return terminal
   */
  public Terminal getTerminal() {
    return terminal;
  }
}