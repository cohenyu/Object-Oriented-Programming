package game.objects;

import game.objects.collision.Collidable;
import geometry.Rectangle;
import geometry.Point;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import geometry.Velocity;
import game.levels.GameLevel;
import game.objects.sprite.Sprite;

import java.awt.Color;

/**
 * @author Yuval Cohen
 * The game.objects.Paddle is the player in the game. It is a rectangle that is controlled
 * by the arrow keys, and moves according to the player key presses.
 * It implement the game.objects.sprite.Sprite and the game.objects.collision.Collidable interfaces.
 */
public class Paddle implements Sprite, Collidable {
    public static final int BORDER_WIDTH = 25;
    public static final int PADDLE_HEIGHT = 25;
    private static final int SHOTS_DELAY = 350;

    private KeyboardSensor keyboard;
    private Rectangle rect;
    private Color color;
    private int speed;
    private int max;
    private BallCreator ballCreator;
    private boolean ifHit;
    private long lastShoot;

    /**
     * constructor.
     *
     * @param keyboard    - The keyboard sensor the paddle uses.
     * @param speed       - The speed of the paddle movement.
     * @param width       - The width of the paddle.
     * @param point       - the location of the paddle.
     * @param widthScreen - the width of the screen.
     */
    public Paddle(KeyboardSensor keyboard, int speed, int width, Point point, int widthScreen) {
        this.rect = new Rectangle(point, width, PADDLE_HEIGHT);
        this.color = Color.ORANGE;
        this.keyboard = keyboard;
        this.speed = speed;
        this.max = widthScreen - (int) rect.getWidth() - BORDER_WIDTH;
        this.ballCreator = null;
        this.ifHit = false;
        this.lastShoot = 0;
    }

    /**
     * This method moving the paddle left according to his speed, but without leaving the screen.
     *
     * @param dt the amount of seconds passed since the last call.
     */
    public void moveLeft(double dt) {
        if (rect.getUpperLeft().getX() > BORDER_WIDTH + (this.speed * dt)) {
            Point newUpperLeft = new Point(rect.getUpperLeft().getX() - (this.speed * dt), rect.getUpperLeft().getY());
            this.rect = new Rectangle(newUpperLeft, rect.getWidth(), rect.getHeight());
        }
    }

    /**
     * This method moving the paddle right according to his speed, but without leaving the screen.
     * @param dt the amount of seconds passed since the last call.
     */
    public void moveRight(double dt) {
        if (rect.getUpperLeft().getX() + (this.speed * dt) < max) {
            Point newUpperLeft = new Point(rect.getUpperLeft().getX() + (this.speed * dt), rect.getUpperLeft().getY());
            this.rect = new Rectangle(newUpperLeft, rect.getWidth(), rect.getHeight());
        }
    }

    /**
     * notify that time has passed -- to change the position controlled
     * by the arrow keys, and moves according to the player key presses.
     * @param dt the amount of seconds passed since the last call.
     */
    public void timePassed(double dt) {
        if (keyboard.isPressed(keyboard.LEFT_KEY)) {
            this.moveLeft(dt);
        }
        if (keyboard.isPressed(keyboard.RIGHT_KEY)) {
            this.moveRight(dt);
        }
        if (keyboard.isPressed(KeyboardSensor.SPACE_KEY) && ballCreator != null) {
            long curTime = System.currentTimeMillis();
            if (lastShoot < curTime - SHOTS_DELAY) {
                this.lastShoot = curTime;
                ballCreator.create((int) rect.topLine().middle().getX(), (int) rect.getUpperLeft().getY() - 1);
            }
        }
    }

    /**
     * This method draw the paddle on the given DrawSurface.
     *
     * @param d - the draw Surface
     */
    public void drawOn(DrawSurface d) {
        // Painting rectangle area
        d.setColor(this.color);
        d.fillRectangle((int) this.rect.getUpperLeft().getX(), (int) this.rect.getUpperLeft().getY(),
                (int) this.rect.getWidth(), (int) this.rect.getHeight());
        // Painting rectangle perimeter
        d.setColor(Color.BLACK);
        d.drawRectangle((int) this.rect.getUpperLeft().getX(), (int) this.rect.getUpperLeft().getY(),
                (int) this.rect.getWidth(), (int) this.rect.getHeight());
    }

    /**
     * This method returns game.objects.collision rectangle.
     *
     * @return paddle's rect
     */
    public Rectangle getCollisionRectangle() {
        return new Rectangle(rect.getUpperLeft(), rect.getWidth(), this.rect.getHeight());
    }

    /**
     * The behavior of the ball's bounce depends on where it hits
     * the paddle according to an angle and speed.
     *
     * @param collisionPoint  - game.objects.collision point with the block
     * @param currentVelocity - the current velocity of the ball
     * @param hitter          - the hitter object
     * @return new velocity after the hit
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        ifHit = true;
        double collideOffset = collisionPoint.getX() - rect.getUpperLeft().getX();
        int collideGroup = (int) (collideOffset / (this.rect.getWidth() / 5)) + 1;

        switch (collideGroup) {
            case 1:
                return Velocity.fromAngleAndSpeed(-60, currentVelocity.getSpeed());

            case 2:
                return Velocity.fromAngleAndSpeed(-30, currentVelocity.getSpeed());

            case 3:
                return new Velocity(currentVelocity.getDx(), -currentVelocity.getDy());

            case 4:
                return Velocity.fromAngleAndSpeed(30, currentVelocity.getSpeed());

            case 5:
            default:
                return Velocity.fromAngleAndSpeed(60, currentVelocity.getSpeed());

        }
    }

    /**
     * Add this paddle to the game.
     *
     * @param g - game.levels.GameLevel
     */
    public void addToGame(GameLevel g) {
        g.addCollidable(this);
        g.addSprite(this);
    }

    /**
     * remove this paddle from the game.
     *
     * @param g - game.levels.GameLevel
     */
    public void removeFromGame(GameLevel g) {
        g.removeCollidable(this);
        g.removeSprite(this);
    }

    /**
     * sets the balls created by the paddle.
     *
     * @param newBallCreator the ball creator of the paddle.
     */
    public void setBallCreator(BallCreator newBallCreator) {
        this.ballCreator = newBallCreator;
    }

    /**
     * @return true if the paddle has been hit, false otherwise.
     */
    public boolean ifHit() {
        if (ifHit) {
            ifHit = false;
            return true;
        }
        return false;
    }
}

