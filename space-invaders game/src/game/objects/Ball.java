package game.objects;

import biuoop.DrawSurface;
import game.objects.collision.Collidable;
import game.objects.collision.CollisionInfo;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import geometry.Velocity;
import game.levels.GameLevel;
import game.objects.sprite.Sprite;

import java.awt.Color;
import java.util.List;

/**
 * @author Yuval Cohen
 * This class represents a ball. The ball has a size, center point, and color.
 * Balls also know how to draw themselves on a DrawSurface.
 */
public class Ball implements Sprite {
    public static final int INITIALIZE = 2;

    //Members
    private Point center;
    private int r;
    private java.awt.Color color;
    private Velocity velocity = new Velocity(INITIALIZE, INITIALIZE);
    private int leftLimit;
    private int rightLimit;
    private int topLimit;
    private int bottomLimit;
    private GameEnvironment game;

    /**
     * constructor of a ball without border.
     *
     * @param center - center point
     * @param r      - radius
     * @param color  - ball color
     */
    public Ball(Point center, int r, java.awt.Color color) {
        this.center = center;
        if (r == 0) {
            this.r = 1;
        } else {
            this.r = r;
        }
        this.color = color;
    }

    /**
     * constructor of a ball with border.
     *
     * @param center      - center point
     * @param r           - radius
     * @param color       - ball color
     * @param leftLimit   - ball left limit
     * @param rightLimit  - ball right limit
     * @param topLimit    - ball top limit
     * @param bottomLimit - ball bottom limit
     */
    public Ball(Point center, int r, java.awt.Color color, int leftLimit, int rightLimit,
                int topLimit, int bottomLimit) {
        this.center = center;
        this.r = r;
        this.color = color;
        this.leftLimit = leftLimit;
        this.rightLimit = rightLimit;
        this.topLimit = topLimit;
        this.bottomLimit = bottomLimit;
    }

    /**
     * this method set the game environment of the ball.
     *
     * @param gameEnvironment - the current game
     */
    public void setGameEnvironment(GameEnvironment gameEnvironment) {
        this.game = gameEnvironment;
    }

    /**
     * This method returns the x value of the ball's center point.
     *
     * @return int - x of the center point
     */
    public int getX() {
        return (int) this.center.getX();
    }

    /**
     * This method returns the Y value of the ball's center point.
     *
     * @return int - Y of the center point
     */
    public int getY() {
        return (int) this.center.getY();
    }

    /**
     * This method returns the ball radius.
     *
     * @return int - radius
     */
    public int getSize() {
        return this.r;
    }

    /**
     * This method returns the ball color.
     *
     * @return java.awt.Color - the ball color
     */
    public java.awt.Color getColor() {
        return this.color;
    }

    /**
     * This method draw the ball on the given DrawSurface according to
     * his color, size and his location.
     *
     * @param surface - a given DrawSurface.
     */
    public void drawOn(DrawSurface surface) {
        //Drawing the rectangle space
        surface.setColor(this.color);
        surface.fillCircle((int) this.center.getX(), (int) this.center.getY(), this.r);
        //Drawing the perimeter of the rectangle
        surface.setColor(Color.BLACK);
        surface.drawCircle((int) this.center.getX(), (int) this.center.getY(), this.r);
    }

    /**
     * The method change the velocity value of a game.objects.Ball.
     *
     * @param v - a given geometry.Velocity.
     */
    public void setVelocity(Velocity v) {
        this.velocity = v;
    }

    /**
     * The method change the velocity value of a game.objects.Ball.
     *
     * @param dx - a given dx of new geometry.Velocity.
     * @param dy - a given dy of new geometry.Velocity.
     */
    public void setVelocity(double dx, double dy) {
        this.velocity = new Velocity(dx, dy);
    }


    /**
     * The method returns the velocity value of a game.objects.Ball.
     *
     * @return geometry.Velocity - the ball velocity
     */
    public Velocity getVelocity() {
        return this.velocity;
    }


    /**
     * move the ball one step onward. Unless the ball is about to collide
     * with an object, then change the direction of the ball.
     *
     * @param dt the amount of seconds passed since the last call
     */
    public void moveOneStep(double dt) {

        Velocity ve = getVelocity().applyDt(dt);
        Line trajectory = new Line(this.center, ve.applyToPoint(this.center));
        CollisionInfo collisionInfo = this.game.getClosestCollision(trajectory);

        Point futureCenter = null;

        if (collisionInfo == null) {
            futureCenter = ve.applyToPoint(this.center);
        } else {
            Point p1 = new Point(collisionInfo.collisionPoint().getX() - 0.05
                    , collisionInfo.collisionPoint().getY() - 0.05);
            Velocity v = collisionInfo.collisionObject().hit(this, collisionInfo.collisionPoint(), this.velocity);
            this.setVelocity(v);
            futureCenter = v.applyDt(dt).applyToPoint(p1);
        }

        /*
         * Just before setting up the next ball position:
         * Make sure this position is not enter inside another collidable rectangle.
         * If this it does happen: we will set the ball position at the edge of that collidable so that the
         * next iteration that rectangle will be chosen as closet game.objects.collision object and move away from it.
         */
        Point edgePoint = normalizeNextMove(futureCenter);
        if (edgePoint != null) {
            futureCenter = edgePoint;
        }
        this.center = futureCenter;
    }

    /**
     * This method normalizes movement of the ball.
     *
     * @param futureCenter a point
     * @return new point
     */
    private Point normalizeNextMove(Point futureCenter) {
        //Make sure this position is not enter inside another collidable rectangle.
        List<Collidable> collidables = game.getCollidables();
        for (Collidable c : collidables) {
            Rectangle rect = c.getCollisionRectangle();
            if (rect.isPointInsideRectangle(futureCenter)) {
                return new Line(this.center, futureCenter).closestIntersectionToStartOfLine(rect);
            }
        }
        return null;
    }

    /**
     * game.objects.sprite.Sprite.
     *
     * @param dt the amount of seconds passed since the last call
     */
    public void timePassed(double dt) {
        this.moveOneStep(dt);
    }

    /**
     * this method adds the ball to the game.
     *
     * @param g - game.levels.GameLevel
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }

    /**
     * Remove this ball from the game.
     *
     * @param g - a given game.levels.GameLevel
     */
    public void removeFromGame(GameLevel g) {
        g.removeSprite(this);
    }
}