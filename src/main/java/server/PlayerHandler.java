package server;

import core.Player;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Class on the server side, handling a player and its input
 */
public class PlayerHandler extends Player implements Runnable {

  private final BufferedReader listener;
  private final PrintWriter sender;
  private final Server server;
  private final House house;
  private final int playerID;
  private boolean running = true;

  /**
   * Create a new player handler, handling the client/player on the server itself
   * @param client client to handle
   * @param server parent server class running the game
   * @param playerID the player ID
   * @param house the players house
   * @throws IOException if establishing the connections fails
   */
  public PlayerHandler(Socket client, Server server, int playerID, House house) throws IOException {
    this.listener = new BufferedReader(new InputStreamReader(client.getInputStream()));
    this.sender = new PrintWriter(client.getOutputStream(), true);
    this.server = server;
    this.playerID = playerID;
    this.house = house;
  }

  /**
   * End the player handler
   */
  public void end() {
    // Send to clients/players
    sendMessage("EndGame");
    this.running = false;
  }

  /**
   * Run the player handler
   */
  @Override
  public void run() {
    String input;

    try {
      while (running) {
        input = listener.readLine();
        server.handlePlayerInput(input, this);
      }
    } catch (IOException e) {
      server.getTerminal().showError(e.getMessage());
    } finally {
      try {
        listener.close();
      } catch (IOException ignored) {
      }
    }
  }

  /**
   * Send a message to the client over the socket
   * @param message string message to send
   */
  public void sendMessage(String message) {
    sender.println(message);
  }

  /**
   * Get playerID of player
   * @return playerID as integer
   */
  public int getPlayerID() {
    return playerID;
  }

  /**
   * Get the house of player
   * @return house of player
   */
  public String getHouse() {
    return this.house.toString();
  }

  /**
   * There are only 4 houses
   */
  public enum House {
    Gryffindor {
      @Override
      public String toString() {
        return "Gryffindor";
      }
    },
    Slytherin {
      @Override
      public String toString() {
        return "Slytherin";
      }
    },
    Ravenclaw {
      @Override
      public String toString() {
        return "Ravenclaw";
      }
    },
    Hufflepuff {
      @Override
      public String toString() {
        return "Hufflepuff";
      }
    }
  }
}
