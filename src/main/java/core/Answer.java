package core;

/**
 * Record holding the answer and the player itself
 * @param answer
 * @param player
 */

// IntelliJ rewrote previous class to record
// What is a record? https://www.developer.com/java/java-record-class/#:~:text=Record%20is%20a%20special%20purpose,added%20into%20the%20Java%20language.
public record Answer(char answer, Player player) {
}