package game.spaceInvaders;

import biuoop.DrawSurface;
import game.levels.GameLevel;
import game.objects.Ball;
import game.objects.Block;
import game.objects.listeners.HitListener;
import game.objects.listeners.HitNotifier;
import geometry.Point;
import geometry.Rectangle;
import geometry.Velocity;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Yuval Cohen
 * Alien is a kind of block that can move.
 */
public class Alien extends Block implements HitNotifier {
    public static final int HEIGHT = 30;
    private static final int WIDTH = 40;
    private static final String IMAGE = "enemy.png";
    private List<HitListener> hitListeners;
    private int hitPoints;
    private double xPos;
    private double yPos;
    private static  Image image;

    /**
     * constructor.
     *
     * @param xPos the x position of the alien
     * @param yPos the y position of the alien
     */
    public Alien(int xPos, int yPos) {
        this.hitListeners = new ArrayList<>();
        this.xPos = xPos;
        this.yPos = yPos;
        this.hitPoints = 1;

        if (image == null) {
            try {
                image = ImageIO.read(ClassLoader.getSystemResourceAsStream(IMAGE));
            } catch (Exception e) {
                System.out.println("Error loading image");
                System.exit(0);
            }
        }
    }

    /**
     * change the position of the block.
     *
     * @param dx the change in X coordinate.
     * @param dy the change in Y coordinate.
     */
    public void move(double dx, double dy) {
        this.xPos += dx;
        this.yPos += dy;
    }

    @Override
    public Rectangle getCollisionRectangle() {
        return new Rectangle(new Point(xPos, yPos), WIDTH, HEIGHT);
    }

    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        if (hitter.getVelocity().getDy() < 0) {
            this.hitPoints--;
        }

        notifyHit(hitter);
        return currentVelocity;
    }


    @Override
    public void drawOn(DrawSurface d) {
        d.drawImage((int) this.xPos, (int) this.yPos, this.image);
    }

    @Override
    public void timePassed(double dt) {

    }

    @Override
    public void addToGame(GameLevel g) {
        g.addCollidable(this);
    }

    @Override
    public void removeFromGame(GameLevel game) {
        game.removeCollidable(this);
    }

    @Override
    public void addHitListener(HitListener hl) {
        hitListeners.add(hl);
    }

    @Override
    public void removeHitListener(HitListener hl) {
        hitListeners.remove(hl);
    }

    /**
     * notify hit has been made.
     *
     * @param hitter - the ball hit this block.
     */
    private void notifyHit(Ball hitter) {
        List<HitListener> tempListeners = new ArrayList<>(this.hitListeners);
        for (HitListener hl : tempListeners) {
            hl.hitEvent(this, hitter);
        }
    }

    @Override
    public Integer getHitPoints() {
        return this.hitPoints;
    }

    @Override
    public int getWidth() {
        return WIDTH;
    }
}
