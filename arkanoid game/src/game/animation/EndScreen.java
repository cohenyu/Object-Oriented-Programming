package game.animation;

import biuoop.DrawSurface;

import java.awt.Color;

/**
 * @author yuval.Yuval Cohen
 * This method represents the end of the game in a loss or victory,
 * announces it and displays the score.
 */
public class EndScreen implements Animation {
    // Members
    private int score;
    private boolean win;

    /**
     * constructor.
     *
     * @param score - The score at the end of the game
     * @param win   - Marks if the game is over with a loss or a win.
     */
    public EndScreen(int score, boolean win) {
        this.score = score;
        this.win = win;
    }

    /**
     * This method draws an game.animation in which it announces the outcome
     * of the game and the scoring mode.
     *
     * @param d  a draw surface
     * @param dt dt specifies the amount of seconds passed since the last call.
     */
    public void doOneFrame(DrawSurface d, double dt) {
        if (win) {
            d.setColor(Color.BLACK);
            d.fillRectangle(0, 0, d.getWidth(), d.getHeight());

            d.setColor(Color.white);
            d.drawText(205, 200, "You Win!", 80);
            d.setColor(Color.magenta);
            d.drawText(207, 200, "You Win!", 80);
            d.setColor(Color.white);
            d.drawText(209, 200, "You Win!", 80);
            d.setColor(Color.cyan);
            d.drawText(290, 270, "Your score is ", 30);
            d.setColor(Color.magenta);
            if (this.score < 100) {
                d.drawText(337, 400, Integer.toString(this.score), 100);
                d.drawText(340, 400, Integer.toString(this.score), 100);
            } else {
                d.drawText(310, 400, Integer.toString(this.score), 100);
                d.drawText(313, 400, Integer.toString(this.score), 100);
            }
            d.setColor(Color.darkGray);
            d.drawText(232, 550, "Press space to continue", 30);

        } else {
            d.setColor(Color.BLACK);
            d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
            d.setColor(Color.white);
            d.drawText(185, 200, "Game Over", 80);
            d.setColor(Color.magenta);
            d.drawText(187, 200, "Game Over", 80);
            d.setColor(Color.white);
            d.drawText(189, 200, "Game Over", 80);
            d.setColor(Color.cyan);
            d.drawText(290, 270, "Your score is ", 35);
            d.setColor(Color.magenta);
            if (this.score < 100) {
                d.drawText(337, 400, Integer.toString(this.score), 100);
                d.drawText(340, 400, Integer.toString(this.score), 100);
            } else {
                d.drawText(310, 400, Integer.toString(this.score), 100);
                d.drawText(313, 400, Integer.toString(this.score), 100);
            }
            d.setColor(Color.darkGray);
            d.drawText(232, 550, "Press space to continue", 30);
        }
    }

    /**
     * This method indicates when to stop the current game.animation.
     *
     * @return true / false
     */
    public boolean shouldStop() {
        return false;
    }
}