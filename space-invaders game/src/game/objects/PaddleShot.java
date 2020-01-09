package game.objects;
import geometry.Point;

import java.awt.Color;

/**
 * @author Yuval Cohen
 * contains a ball which is shot by the client.
 */
public class PaddleShot extends Ball {
    /**
     * creates new client ball.
     *
     * @param x the X position of the ball.
     * @param y the Y position of the ball.
     */
    public PaddleShot(int x, int y) {
        super(new Point(x , y), 2, Color.white);
        super.setVelocity(0, -540);
    }
}