package game.spaceInvaders;

import biuoop.DrawSurface;
import game.levels.GameLevel;
import game.objects.Ball;
import game.objects.BallCreator;
import game.objects.Block;
import game.objects.listeners.AlienRemover;
import game.objects.listeners.HitListener;
import game.objects.sprite.Sprite;
import geometry.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Yuval Cohen
 * Aliens formation is a group of aliens that can shoot.
 */
public class AliensFormation implements Sprite {
    private List<List<Alien>> aliens;
    private double speed;
    private  double initialSpeed;
    private double lowestLocation;
    private long lastShoot;
    private BallCreator ballCreator;

    /**
     * creates new enemy formation.
     *
     * @param rows  the number of rows in each formation.
     * @param cols  the number of cols in each formation.
     * @param speed the speed of the formation.
     */
    public AliensFormation(int rows, int cols, double speed) {
        this.aliens = new ArrayList<>();

        AlienRemover enemyRemover = new AlienRemover(this);
        for (int i = 0; i < cols; i++) {
            List<Alien> col = new ArrayList<>();
            for (int j = rows - 1; j >= 0; j--) {
                Alien alien = new Alien(i * 50 + 25, j * 40 + 50);
                alien.addHitListener(enemyRemover);
                col.add(alien);
            }
            aliens.add(col);
        }

        this.speed = speed;
        this.initialSpeed = speed;
        this.lowestLocation = 0;
        this.lastShoot = 0;
        ballCreator = null;
    }

    @Override
    public void drawOn(DrawSurface d) {
        for (List<Alien> col : aliens) {
            for (Alien enemy : col) {
                enemy.drawOn(d);
            }
        }
    }

    @Override
    public void timePassed(double dt) {
        double dx, dy = 0;

        if (aliens == null || aliens.size() == 0) {
            return;
        }

        if (speed > 0) {
            double rightPos = aliens.get(aliens.size() - 1).get(0).getCollisionRectangle().upperRight().getX();
            dx = Math.min(800 - rightPos, speed * dt);
        } else {
            double leftPos = aliens.get(0).get(0).getCollisionRectangle().getUpperLeft().getX();
            dx = Math.max(-leftPos, speed * dt);
        }

        if (dx == 0) {
            dy = 30;
            speed *= -1.1;
        }

        for (List<Alien> col : aliens) {
            for (Alien enemy : col) {
                enemy.move(dx, dy);
                lowestLocation = Math.max(lowestLocation, enemy.getCollisionRectangle().bottomLeft().getY());
            }
        }

        long curTime = System.currentTimeMillis();
        if (curTime - lastShoot > 500 && ballCreator != null) {
            Random rnd = new Random();
            int shotCol = rnd.nextInt(aliens.size());
            Point point = aliens.get(shotCol).get(0).getCollisionRectangle().botLine().middle();
            ballCreator.create((int) point.getX(), (int) point.getY() + 1);
            lastShoot = curTime;
        }
    }

    /**
     * Add the enemy formation to the game.
     *
     * @param game the game this formation will be added to.
     */
    public void addToGame(GameLevel game) {
        game.addSprite(this);
        ballCreator = new BallCreator() {
            @Override
            public Ball create(int xpos, int ypos) {
                Ball ball = new AlienShot(xpos, ypos);
                ball.setGameEnvironment(game.getEnvironment());
                ball.addToGame(game);
                game.addShots(ball);
                return ball;
            }
        };
    }

    /**
     * remove an enemy block from the game.
     *
     * @param enemy the enemy block to remove.
     */
    public void removeFromGame(Block enemy) {
        for (int i = 0; i < aliens.size(); i++) {
            List<Alien> col = aliens.get(i);
            for (int j = 0; j < col.size(); j++) {
                if (col.get(j).equals(enemy)) {
                    col.remove(j);
                    if (col.isEmpty()) {
                        aliens.remove(i);
                    }
                    break;
                }
            }
        }
    }

    /**
     * @return the enemy blocks in this formation.
     */
    public List<Block> getEnemies() {
        List<Block> retEnemies = new ArrayList<>();
        for (List<Alien> col : aliens) {
            retEnemies.addAll(col);
        }
        return retEnemies;
    }

    /**
     * @return true if the formation is at the bottom, false otherwise.
     */
    public boolean shouldLose() {
        return lowestLocation >= 500;
    }

    /**
     * resets the position of the formation to their initial point.
     */
    public void resetPos() {
        double mostLeft = aliens.get(0).get(0).getCollisionRectangle().bottomLeft().getX();
        double highestPos = 800;
        for (List<Alien> col : aliens) {
            highestPos = Math.min(col.get(col.size() - 1).getCollisionRectangle().getUpperLeft().getY(), highestPos);
        }

        for (List<Alien> col : aliens) {
            for (Alien alien : col) {
                alien.move(-mostLeft + 25, -highestPos + 50);
            }
        }

        lowestLocation = 0;
        speed = initialSpeed;
    }

    /**
     * adds hit listener to an alien.
     *
     * @param hitListener hot listener
     */
    public void addHitListener(HitListener hitListener) {
        for (List<Alien> col : aliens) {
            for (Alien alien : col) {
                alien.addHitListener(hitListener);
            }
        }
    }
}
