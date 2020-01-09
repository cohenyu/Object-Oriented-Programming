package game.animation;

import biuoop.DrawSurface;

/**
 * @author yuval.Yuval Cohen
 * This class controls game.animation frames with which game.animation to draw and
 * with conditions - when to stop draw this game.animation.
 */
public interface Animation {
    /**
     * What to do with each iteration of the game.animation.
     *
     * @param d  a draw surface
     * @param dt dt specifies the amount of seconds passed since the last call.
     */
    void doOneFrame(DrawSurface d, double dt);

    /**
     * This method indicates when to stop the current game.animation.
     *
     * @return true/ false
     */
    boolean shouldStop();
}