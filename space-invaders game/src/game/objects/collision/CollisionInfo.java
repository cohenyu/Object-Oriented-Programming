package game.objects.collision;

import geometry.Point;

/**
 * @author Yuval Cohen
 * this class hold the information about the closest
 * game.objects.collision point and object that is going to occur.
 */
public class CollisionInfo {
    //Members
    private Point collisionPoint;
    private Collidable collisionObject;

    /**
     * constructor of collisionInfo.
     *
     * @param collisionPoint  - a point
     * @param collisionObject - a game.objects.collision.Collidable
     */
    public CollisionInfo(Point collisionPoint, Collidable collisionObject) {
        this.collisionPoint = collisionPoint;
        this.collisionObject = collisionObject;
    }

    /**
     * the point at which the game.objects.collision occurs.
     *
     * @return a point
     */
    public Point collisionPoint() {
        return this.collisionPoint;
    }

    /**
     * the collidable object involved in the game.objects.collision.
     *
     * @return a game.objects.collision.Collidable
     */
    public Collidable collisionObject() {
        return this.collisionObject;
    }
}