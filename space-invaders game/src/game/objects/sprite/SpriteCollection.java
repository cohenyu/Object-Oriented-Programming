package game.objects.sprite;

import biuoop.DrawSurface;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yuval.Yuval Cohen
 * This class have a collection of sprites.
 */
public class SpriteCollection {
    private ArrayList<Sprite> sprites;

    /**
     * constructor of a game.objects.sprite collection.
     */
    public SpriteCollection() {
        this.sprites = new ArrayList<Sprite>();
    }

    /**
     * add game.objects.sprite to the game.
     *
     * @param s - game.objects.sprite
     */
    public void addSprite(Sprite s) {
        sprites.add(s);
    }

    /**
     * Remove game.objects.sprite from the game.
     *
     * @param s - game.objects.sprite
     */
    public void removeSprite(Sprite s) {
        this.sprites.remove(s);
    }

    /**
     * call timePassed() on all sprites.
     * @param dt the amount of seconds passed since the last call.
     */
    public void notifyAllTimePassed(double dt) {
        List<Sprite> list = new ArrayList<Sprite>(this.sprites);
        for (Sprite s : list) {
            s.timePassed(dt);
        }
    }

    /**
     * call drawOn(d) on all sprites.
     *
     * @param d - a draw surface
     */
    public void drawAllOn(DrawSurface d) {
        for (Sprite s : sprites) {
            s.drawOn(d);
        }
    }
}