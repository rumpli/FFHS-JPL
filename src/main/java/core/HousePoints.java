package core;

/**
 * Abstract class representing all house points
 */
public abstract class HousePoints {
    private int gryffindorPoints, slytherinPoints, hufflepuffPoints, ravenclawPoints;

    /**
     * Create a house object holding all house points
     */
    protected HousePoints() {
        this.gryffindorPoints = 0;
        this.slytherinPoints = 0;
        this.hufflepuffPoints = 0;
        this.ravenclawPoints = 0;
    }

    /**
     * Gets the house points of Gryffindor
     * @return the house points as integer
     */
    public int getGryffindorPoints() {
        return gryffindorPoints;
    }

    /**
     * Awards points to the house!
     * @param points to award
     */
    public void addGryffindorPoints(int points) {
        this.gryffindorPoints += points;
    }

    /**
     * Gets the house points of Slytherin
     * @return the house points as integer
     */
    public int getSlytherinPoints() {
        return slytherinPoints;
    }

    /**
     * Awards points to the house!
     * @param points to award
     */
    public void addSlytherinPoints(int points) {
        this.slytherinPoints += points;
    }

    /**
     * Gets the house points of Hufflepuff
     * @return the house points as integer
     */
    public int getHufflepuffPoints() {
        return hufflepuffPoints;
    }

    /**
     * Awards points to the house!
     * @param points to award
     */
    public void addHufflepuffPoints(int points) {
        this.hufflepuffPoints += points;
    }

    /**
     * Gets the house points of Ravenclaw
     * @return the house points as integer
     */
    public int getRavenclawPoints() {
        return ravenclawPoints;
    }

    /**
     * Awards points to the house!
     * @param points to award
     */
    public void addRavenclawPoints(int points) {
        this.ravenclawPoints += points;
    }
}
