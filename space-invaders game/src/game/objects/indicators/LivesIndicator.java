package game.objects.indicators;

import biuoop.DrawSurface;
import game.objects.listeners.Counter;
import game.objects.sprite.Sprite;

import java.awt.Color;

/**
 * @author Yuval Cohen
 * will be in charge of displaying the current lives.
 */
public class LivesIndicator implements Sprite {
    //member
    private Counter lives;

    /**
     * constructor.
     *
     * @param lives the number of live
     */
    public LivesIndicator(Counter lives) {
        this.lives = lives;
    }

    /**
     * draw the game.objects.sprite to the screen.
     *
     * @param d - the draw Surface
     */
    public void drawOn(DrawSurface d) {
        d.setColor(Color.darkGray);
        d.drawText(100, 20, "Lives: " + this.lives.getValue(), 15);
    }

    /**
     * notify the game.objects.sprite that time has passed.
     *
     * @param dt the amount of seconds passed since the last call.
     */
    public void timePassed(double dt) {

    }
}
