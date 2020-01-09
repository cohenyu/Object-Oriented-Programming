package game.objects;

import game.objects.collision.Collidable;
import game.objects.collision.CollisionInfo;
import geometry.Point;
import geometry.Line;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yuval Cohen
 * The game.objects.GameEnvironment class will be a collection of many game.objects that ball can collide with.
 */
public class GameEnvironment {
    // Member
    private ArrayList<Collidable> collidables;

    /**
     * constructor of game environment.
     */
    public GameEnvironment() {
        this.collidables = new ArrayList<Collidable>();
    }

    /**
     * Add the given collidable to the environment.
     *
     * @param c - a collidable
     */
    public void addCollidable(Collidable c) {
        this.collidables.add(c);
    }

    /**
     * Remove the given collidable from the environment.
     *
     * @param c - a collidable
     */
    public void removeCollidable(Collidable c) {
        this.collidables.remove(c);
    }

    /**
     * Return the collidables list of the game.
     *
     * @return - list of collidables
     */
    public List<Collidable> getCollidables() {
        return this.collidables;
    }


    /**
     * Assume an object moving from line.start() to line.end().
     * If this object will not collide with any of the collidables
     * in this collection, return null. Else, return the information
     * about the closest game.objects.collision that is going to occur.
     *
     * @param trajectory - a line
     * @return game.objects.collision.CollisionInfo / null
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        if (this.collidables.isEmpty()) {
            return null;
        }
        Point collisionPoint = null;
        Collidable collisionObject = null;

        //Initialize the variables at the first intersection point
        for (int i = 0; i < this.collidables.size() && collisionPoint == null; i++) {
            collisionPoint = trajectory.closestIntersectionToStartOfLine(collidables.get(i).getCollisionRectangle());
            collisionObject = this.collidables.get(i);
        }
        //the line doesn't collide with any of the collidables- return null
        if (collisionPoint == null) {
            return null;
        }

        //Trying to find collidables that cut the line more close to line.start
        for (Collidable c : collidables) {
            Point p = trajectory.closestIntersectionToStartOfLine(c.getCollisionRectangle());
            if (p != null && p.distance(trajectory.start()) <= collisionPoint.distance(trajectory.start())) {
                collisionPoint = p;
                collisionObject = c;
            }
        }
        //Returns the information of the closest object
        return new CollisionInfo(collisionPoint, collisionObject);
    }
}