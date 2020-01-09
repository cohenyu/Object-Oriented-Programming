package game.spaceInvaders;

import game.objects.Ball;
import geometry.Point;

import java.awt.Color;

/**
 * @author Yuval Cohen
 * contains a ball which is shot by an alien.
 */
public class AlienShot extends Ball {
    /**
     * creates new alien ball.
     *
     * @param x the X position of the ball.
     * @param y the Y position of the ball.
     */
    public AlienShot(int x, int y) {
        super(new Point(x, y), 4, Color.red);
        super.setVelocity(0, 480);
    }
}