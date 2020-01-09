package game.levels;

import geometry.Velocity;
import game.objects.Block;
import game.objects.sprite.Sprite;

import java.util.List;

/**
 * @author Yuval Cohen
 * contains information of a level.
 */
public interface LevelInformation {

    /**
     * returns the number of the ball in the level.
     *
     * @return int
     */
    int numberOfBalls();

    /**
     * The initial velocity of each ball.
     *
     * @return velocities
     */
    List<Velocity> initialBallVelocities();

    /**
     * returns the speed of the paddle.
     *
     * @return value
     */
    int paddleSpeed();

    /**
     * returns the width of the paddle.
     *
     * @return value
     */
    int paddleWidth();

    /**
     * returns the name of the level.
     *
     * @return string
     */
    String levelName();

    /**
     * Returns a game.objects.sprite with the background of the level.
     *
     * @return game.objects.sprite
     */
    Sprite getBackground();

    /**
     * The Blocks that make up this level, each block contains
     * its size, color and location.
     *
     * @return block's list
     */
    List<Block> blocks();

    /**
     * Number of block that should be removed.
     *
     * @return value
     */
    int numberOfBlocksToRemove();
}