package game.objects.indicators;

import biuoop.DrawSurface;
import game.objects.listeners.Counter;
import game.objects.sprite.Sprite;
import java.awt.Color;

/**
 * @author Yuval Cohen
 * will be in charge of displaying the current score.
 */
public class ScoreIndicator implements Sprite {
    //member
    private Counter scores;

    /**
     * constructor.
     *
     * @param scores - the counter of the score.
     */
    public ScoreIndicator(Counter scores) {
        this.scores = scores;
    }

    /**
     * draw the game.objects.sprite to the screen.
     *
     * @param d - the draw Surface
     */
    public void drawOn(DrawSurface d) {
        d.setColor(Color.darkGray);
        d.drawText(370, 20, "Score: " + this.scores.getValue(), 15);
    }

    /**
     * notify the game.objects.sprite that time has passed.
     * @param dt the amount of seconds passed since the last call.
     */
    public void timePassed(double dt) {

    }
}
