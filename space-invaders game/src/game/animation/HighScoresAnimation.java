package game.animation;

import biuoop.DrawSurface;
import game.score.HighScoresTable;

import java.awt.Color;

/**
 * @author Yuval Cohen
 * Animation of high scores
 */
public class HighScoresAnimation implements Animation {
    // Member
    private HighScoresTable scores;

    /**
     * constructor.
     *
     * @param scores - Score table
     */
    public HighScoresAnimation(HighScoresTable scores) {
        this.scores = scores;
    }

    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        d.setColor(Color.BLACK);
        d.fillRectangle(0, 0, 800, 600);
        d.setColor(Color.cyan);
        d.drawText(270, 60, "High Scores", 50);
        d.drawLine(170, 140, 640, 140);
        d.drawText(500, 550, "Press space to continue", 20);
        d.setColor(Color.magenta);
        d.drawText(272, 60, "High Scores", 50);
        d.drawText(160, 120, "Player", 30);
        d.drawText(580, 120, "Score", 30);
        d.setColor(Color.white);
        for (int i = 0; i < this.scores.size(); i++) {
            d.drawText(170, 170 + (35 * i), this.scores.getHighScores().get(i).getName(), 15);
            d.drawText(600, 170 + (35 * i), Integer.toString(this.scores.getHighScores().get(i).getScore()), 15);
        }
    }

    @Override
    public boolean shouldStop() {
        return false;
    }
}