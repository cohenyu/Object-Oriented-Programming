package game.objects.collision;

import geometry.Rectangle;
import geometry.Point;
import geometry.Velocity;
import game.objects.Ball;

/**
 * @author Yuval Cohen
 * The game.objects.collision.Collidable interface will be used by things that can be collided with.
 */
public interface Collidable {
    /**
     * Return the "game.objects.collision shape" of the object.
     *
     * @return "game.objects.collision shape"
     */
    Rectangle getCollisionRectangle();

    /**
     * The method calculate the new velocity expected after a hit with the object.
     *
     * @param collisionPoint  - game.objects.collision point with the block
     * @param currentVelocity - the current velocity of the ball
     * @param hitter          - the hitter object
     * @return new velocity
     */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);
}