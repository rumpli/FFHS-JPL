package core;

/**
 * Abstract class representing a player with a name and a number of points
 */
public abstract class Player {
    private String name;
    private int points = 0;

    /**
     * Awards points to the player
     * @param pointsToAdd points to add to the player
     */
    public void addPoints(int pointsToAdd) {
        this.points += pointsToAdd;
    }

    /**
     * Get points from the player
     * @return the amount of points
     */
    public int getPoints() {
        return points;
    }

    /**
     * Get the name of the player
     * @return the name of the player
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the player
     * @param name the name of the player
     */
    public void setName(String name) {
        this.name = name;
    }
}
