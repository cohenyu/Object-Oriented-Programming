package game.objects.indicators;

import biuoop.DrawSurface;
import game.objects.sprite.Sprite;

import java.awt.Color;

/**
 * @author Yuval Cohen
 * This class is an indicator of the game to display the level name.
 */
public class LevelNameIndicator implements Sprite {
    //Member
    private String name;

    /**
     * constructor.
     *
     * @param name - the name of the level
     */
    public LevelNameIndicator(String name) {
        this.name = name;
    }

    /**
     * draw the game.objects.sprite to the screen.
     *
     * @param d - the draw Surface
     */
    public void drawOn(DrawSurface d) {
        d.setColor(Color.darkGray);
        if (this.name.length() > 15) {
            d.drawText(545, 20, "Level Name: " + this.name, 15);
        } else {
            d.drawText(590, 20, "Level Name: " + this.name, 15);
        }
    }

    /**
     * notify the game.objects.sprite that time has passed.
     *
     * @param dt the amount of seconds passed since the last call.
     */
    public void timePassed(double dt) {
    }
}
