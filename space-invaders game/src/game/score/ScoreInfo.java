package game.score;

import java.io.Serializable;

/**
 * @author yuval Cohen
 * this class that holds name and score represents a score information in this game.
 */
public class ScoreInfo implements Serializable {
    private String playerName;
    private int playerScore;
    /**
     * @param name  the name of the player.
     * @param score the score of the player.
     */
    public ScoreInfo(String name, int score) {
        this.playerName = name;
        this.playerScore = score;
    }
    /**
     * @return the name of the player.
     */
    public String getName() {
        return this.playerName;
    }
    /**
     * @return the score of the player.
     */
    public int getScore() { return this.playerScore; }
}