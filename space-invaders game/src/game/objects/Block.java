package game.objects;

import game.objects.collision.Collidable;
import geometry.Rectangle;
import geometry.Point;
import biuoop.DrawSurface;
import geometry.Velocity;
import game.levels.GameLevel;
import game.objects.listeners.HitListener;
import game.objects.listeners.HitNotifier;
import game.objects.sprite.Sprite;

import java.awt.Color;
import java.awt.Image;
import java.util.List;
import java.util.ArrayList;

/**
 * @author Yuval Cohen
 * this class create a game.objects.Block. block has a rectangle that
 * indicate the location and size, color and hitPoints.
 * game.objects.Block implements to  game.objects.collision.Collidable and game.objects.sprite.
 */
public class Block implements Collidable, Sprite, HitNotifier {
    public static final int FROM_DAWN_RIGHT = 1;
    public static final int FROM_UP_RIGHT = 2;
    public static final int FROM_DAWN_LEFT = 3;
    public static final int FROM_UP_LEFT = 4;
    public static final int ELSE_DIRECTION = 5;

    //members
    private Rectangle rect;
    private Integer hitPoints;
    private List<HitListener> hitListeners;
    private Color color;
    private Image image;
    private boolean isColor;


    /**
     *
     */
    protected Block() {

    }

    /**
     * constructor of a block.
     *
     * @param rect  - a rectangle
     * @param color - the color of the block
     */
    public Block(Rectangle rect, Color color) {
        this.rect = rect;
        this.hitListeners = new ArrayList<HitListener>();
        this.color = color;
        this.isColor = true;
        this.image = null;

    }

    /**
     * constructor of a block.
     *
     * @param rect  - a rectangle
     * @param image - an image
     */
    public Block(Rectangle rect, Image image) {
        this.rect = rect;
        this.hitListeners = new ArrayList<HitListener>();
        this.color = null;
        this.image = image;
        this.isColor = false;
    }


    /**
     * set how many hits is left.
     *
     * @param hitNumber - number of hits
     */
    public void setHitPoints(Integer hitNumber) {
        this.hitPoints = hitNumber;
    }


    /**
     * This method return the number of hits that left to the block.
     *
     * @return - number of hits that left to the block
     */
    public Integer getHitPoints() {
        return hitPoints;
    }

    /**
     * return the width of the block.
     *
     * @return int
     */
    public int getWidth() {
        return (int) this.rect.getWidth();
    }

    /**
     * Return the "game.objects.collision shape" of the object.
     *
     * @return a rectangle
     */
    public Rectangle getCollisionRectangle() {
        return this.rect;
    }

    /**
     * The method calculate the new velocity expected after a hit with the block,
     * and updates the hit points of the block.
     *
     * @param collisionPoint  - game.objects.collision point with the block
     * @param currentVelocity - the current velocity of the ball
     * @param hitter          - the hitter object
     * @return new velocity
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {

        //updates the hitPoints
        if (this.getHitPoints() != 0) {
            this.setHitPoints(this.hitPoints - 1);
        }

        // calculating the direction from which the ball reaches the block.
        int direction = this.getDirection(currentVelocity);

        /*
         * In the next section there are four special conditions tested with which of the
         * corners of the ball collided. Each corner has 3 directions from which the ball
         * can come and in each direction there will be a new speed.
         *
         * After the 4 cases, there are 2 regular cases of hit with the ribs of the block.
         */
        // UpperLeft corner
        if (collisionPoint.equals(this.rect.getUpperLeft())) {
            if (direction == FROM_UP_RIGHT) {
                currentVelocity = new Velocity(currentVelocity.getDx(), -currentVelocity.getDy());
            } else if (direction == FROM_DAWN_LEFT) {
                currentVelocity = new Velocity(-currentVelocity.getDx(), currentVelocity.getDy());
            } else if (direction == FROM_UP_LEFT) {
                currentVelocity = new Velocity(-currentVelocity.getDx(), -currentVelocity.getDy());
            }
            // bottomLeft corner
        } else if (collisionPoint.equals(this.rect.bottomLeft())) {
            if (direction == FROM_UP_LEFT) {
                currentVelocity = new Velocity(-currentVelocity.getDx(), currentVelocity.getDy());
            } else if (direction == FROM_DAWN_RIGHT) {
                currentVelocity = new Velocity(currentVelocity.getDx(), -currentVelocity.getDy());
            } else if (direction == FROM_DAWN_LEFT) {
                currentVelocity = new Velocity(-currentVelocity.getDx(), -currentVelocity.getDy());
            }
            // bottomRight corner
        } else if (collisionPoint.equals(this.rect.bottomRight())) {
            if (direction == FROM_UP_RIGHT) {
                currentVelocity = new Velocity(-currentVelocity.getDx(), currentVelocity.getDy());
            } else if (direction == FROM_DAWN_LEFT) {
                currentVelocity = new Velocity(currentVelocity.getDx(), -currentVelocity.getDy());
            } else if (direction == FROM_DAWN_RIGHT) {
                currentVelocity = new Velocity(-currentVelocity.getDx(), -currentVelocity.getDy());
            }
            // upperRight corner
        } else if (collisionPoint.equals(this.rect.upperRight())) {
            if (direction == FROM_UP_LEFT) {
                currentVelocity = new Velocity(currentVelocity.getDx(), -currentVelocity.getDy());
            } else if (direction == FROM_DAWN_RIGHT) {
                currentVelocity = new Velocity(-currentVelocity.getDx(), currentVelocity.getDy());
            } else if (direction == FROM_UP_RIGHT) {
                currentVelocity = new Velocity(-currentVelocity.getDx(), -currentVelocity.getDy());
            }
            //Hit in the top or bottom line leads to a change in the Y axis
        } else if (this.rect.topLine().onLine(collisionPoint) || this.rect.botLine().onLine(collisionPoint)) {
            currentVelocity = new Velocity(currentVelocity.getDx(), -currentVelocity.getDy());
            //Hit in the left or right line leads to a change in the x axis
        } else if (this.rect.leftLine().onLine(collisionPoint) || this.rect.rightLine().onLine(collisionPoint)) {
            currentVelocity = new Velocity(-currentVelocity.getDx(), currentVelocity.getDy());
        }

        this.notifyHit(hitter);
        //new velocity expected after the hit with a block
        return currentVelocity;
    }

    /**
     * This method draw the block on the given DrawSurface according to
     * his color, size, location and with the number of hits on it.
     *
     * @param d - a draw surface
     */
    public void drawOn(DrawSurface d) {

        if (this.isColor) {
            d.setColor(this.color);
            d.fillRectangle((int) this.rect.getUpperLeft().getX(), (int) this.rect.getUpperLeft().getY(),
                    (int) this.rect.getWidth(), (int) this.rect.getHeight());
        } else {
            d.drawImage((int) this.rect.getUpperLeft().getX()
                    , (int) this.rect.getUpperLeft().getY(), this.image);
        }

    }

    /**
     * @param dt double
     */
    public void timePassed(double dt) {

    }

    /**
     * this method adds the block to the game.
     *
     * @param g - the game
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
        g.addCollidable(this);
    }

    /**
     * This method returns a number that indicates the direction from which the ball reached the block.
     *
     * @param velocity speed of the ball.
     * @return - number that indicates the direction
     */
    public int getDirection(Velocity velocity) {
        if (velocity.getDx() < 0 && velocity.getDy() < 0) {
            return FROM_DAWN_RIGHT;

        } else if (velocity.getDx() < 0 && velocity.getDy() > 0) {
            return FROM_UP_RIGHT;

        } else if (velocity.getDx() > 0 && velocity.getDy() < 0) {
            return FROM_DAWN_LEFT;

        } else if (velocity.getDx() > 0 && velocity.getDy() > 0) {
            return FROM_UP_LEFT;
        }
        return ELSE_DIRECTION;
    }

    /**
     * Remove this block from the game.
     *
     * @param game - a given game.levels.GameLevel
     */
    public void removeFromGame(GameLevel game) {
        game.removeCollidable(this);
        game.removeSprite(this);
    }

    /**
     * notify hit has been made.
     *
     * @param hitter - the ball hit this block.
     */
    private void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);
        // Notify all game.objects.listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }

    /**
     * Add hl as a listener to hit events.
     *
     * @param hl - a listener
     */
    public void addHitListener(HitListener hl) {
        hitListeners.add(hl);
    }

    /**
     * Remove hl from the list of game.objects.listeners to hit events.
     *
     * @param hl - a listener
     */
    public void removeHitListener(HitListener hl) {
        hitListeners.remove(hl);
    }
}
