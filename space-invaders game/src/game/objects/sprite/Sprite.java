package game.objects.sprite;

import biuoop.DrawSurface;

/**
 * @author yuval.Yuval Cohen
 * A game.objects.sprite.Sprite is a game object that can be drawn to the screen.
 */
public interface Sprite {

    /**
     * draw the game.objects.sprite to the screen.
     *
     * @param d - the draw Surface
     */
    void drawOn(DrawSurface d);

    /**
     * notify the game.objects.sprite that time has passed.
     *
     * @param dt the amount of seconds passed since the last call.
     */
    void timePassed(double dt);
}