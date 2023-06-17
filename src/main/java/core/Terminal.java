package core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Contains methods to interact with a user over the console
 */
public class Terminal {

  private final Scanner scanner = new Scanner(System.in);

  /**
   * Shows an introduction to the game and asks the user, what action he wants to take. Input is handled case-insensitive
   * @return q to quit, n to create a new game, j to join an existing game
   */
  public char showStartScreen() {
    String userInput;
    ArrayList<String> options = new ArrayList<>(Arrays.asList("n", "new", "j", "join", "q", "quit"));

    System.out.println("Lets play a quiz game!");
    System.out.println("Each question has 3 answers to choose from. Type 'a', 'b' or 'c' to select the answer.");
    System.out.println("You'll get a point if you answer faster than your fellow players.");
    System.out.println("Winner is the one with the most points at the end of the game!\n");
    System.out.println("Start new game: 'n(ew)'");
    System.out.println("Join an existing game: 'j(oin)'");
    System.out.println("Quit and leave with nothing: 'q(uit)'\n");

      do {
        System.out.print("Select 'n', 'j', 'q': ");
        // make everything lower case (Q = q)
        userInput = scanner.nextLine().toLowerCase();
      } while (!options.contains(userInput));
    return userInput.charAt(0);
  }

  /**
   * Ask the user for player name (min of 4 characters, max of 50 characters)
   * @return the player name
   */
  public String getPlayerName() {
    String name;

    do {
      System.out.print("Enter your name (min 4 characters): ");
      name = scanner.nextLine();
      if (name.length() < 4 || name.length() > 50) {
        System.out.println("Name to short or to long!");
      }
    } while (name.length() < 4 || name.length() > 50);
    return name;
  }

  /**
   * Ask the user for the answer 'a', 'b' or 'c'. Input is handled case-insensitive
   * @return char of the chosen answer
   */
  public char getAnswer() {
    String userInput;
    ArrayList<String> options = new ArrayList<>(Arrays.asList("a", "b", "c"));

    do {
      System.out.println("\nWhich answer is correct?\n");
      System.out.print("Choose 'a', 'b' or 'c': ");
      // make everything lower case (A = a)
      userInput = scanner.nextLine().toLowerCase();
    } while (!options.contains(userInput));
    return userInput.charAt(0);
  }

  /**
   * Ask the user for the amount of players (2-10)
   * @return number of players
   */
  public int getPlayerCount() {
    int players;
    int maxPlayerCount = 12;
    int minPlayerCount = 2;

    do {
      System.out.println("Enter the number of players (game starts when amount of players is reached)");
      System.out.printf("Amount (%d-%d): ", minPlayerCount, maxPlayerCount);
      players = scanner.nextInt();
    } while (players < minPlayerCount || players > maxPlayerCount);
    return players;
  }

  /**
   * Ask the user for the server ip to join
   * @return the ip or an empty string for localhost
   */
  public String getServerIP() {
    String ip;

    do {
      System.out.print("Please enter the server ip (leave empty for localhost): ");
      ip = scanner.nextLine();
    } while (!Validator.isValidIP(ip));
    return ip;
  }

  /**
   * Ask the user for how many questions to play up to the max amount
   * @param maxQuestions amount of max questions
   * @return number of questions
   */
  public int getNumberOfQuestions(int maxQuestions) {
    int numberOfQuestions;

    do {
      System.out.printf("How many questions shall be asked? (max. %d): ", maxQuestions);
      numberOfQuestions = scanner.nextInt();
    } while (numberOfQuestions < 1 || numberOfQuestions > maxQuestions);
    return numberOfQuestions;
  }

  /**
   * Output the given message to the console
   * @param message the message to display
   */
  public void showMessage(String message) {
    System.out.println(message);
  }

  /**
   * prints an error message to the output
   * @param message the message to display as an error
   */
  public void showError(String message) {
    System.err.println(message);
  }
}
