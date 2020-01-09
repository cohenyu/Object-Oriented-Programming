package game.animation;

import biuoop.DrawSurface;

import java.awt.Color;

/**
 * @author Yuval Cohen
 * pause the game and display a screen.
 */
public class PauseScreen implements Animation {

    /**
     * constructor.
     */
    public PauseScreen() {
    }

    /**
     * What to do with each iteration of the game.animation.
     *
     * @param d  a draw surface
     * @param dt - the amount of seconds passed since the last call.
     */
    public void doOneFrame(DrawSurface d, double dt) {
        d.setColor(Color.black);
        d.drawText(10, d.getHeight() / 2, "paused -- press space to continue", 32);
    }

    /**
     * This method indicates when to stop the current game.animation.
     *
     * @return true/ false
     */
    public boolean shouldStop() {
        return false;
    }
}