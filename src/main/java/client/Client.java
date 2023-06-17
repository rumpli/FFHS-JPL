package client;

import core.Player;
import core.Terminal;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Game client that connects to a server via a socket (IP:PORT)
 */
public class Client extends Player implements Runnable {

  private final Terminal terminal;
  private final Socket socket;
  private final BufferedReader listener;
  private final PrintWriter sender;

  /**
   * Creates a new client and connects to the server
   * @param terminal the terminal object for interaction with the user
   * @param port integer of Port to connect to
   * @throws IOException if connection fails
   */
  public Client(Terminal terminal, int port) throws IOException {
    // Ask user for server IP
    String ip = terminal.getServerIP();
    // Ask user for player name
    setName(terminal.getPlayerName());

    this.terminal = terminal;
    this.socket = new Socket(ip, port);
    this.listener = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    this.sender = new PrintWriter(socket.getOutputStream(), true);

    // Send to server clients name
    this.sender.println("SetName :" + getName());
  }

  /**
   * Run the client
   */
  public void run() {
    boolean running = true;
    String serverMessage;
    char input;

    try {
      while (running) {
        // Listen to server messages
        serverMessage = listener.readLine();
        switch (serverMessage) {
          // Server asks client for an answer
          case "GetAnswer" -> {
            input = terminal.getAnswer();
            // Send to server the answer
            sender.println(String.format("%s:%s", "Answer", input));
          }
          // Server ended the game
          case "EndGame" -> {
            running = false;
            sender.println("EndGame");
          }
          // Display server messages
          default -> {
            terminal.showMessage(serverMessage);
          }
        }
      }
    } catch (IOException e) {
      terminal.showError("Connection failed");
    } finally {
      try {
        listener.close();
        sender.close();
        socket.close();
      } catch (IOException ignored) {
      }
    }
  }
}
