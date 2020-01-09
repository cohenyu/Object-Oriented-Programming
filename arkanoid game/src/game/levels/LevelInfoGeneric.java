package game.levels;

import game.objects.Block;
import game.objects.sprite.Sprite;
import geometry.Velocity;

import java.util.List;

/**
 * @author yuval Cohen
 * contains information of certain level.
 */
public class LevelInfoGeneric implements LevelInformation {
    //Members
    private String levelName;
    private Sprite background;
    private int paddleSpeed;
    private int paddleWidth;
    private List<Block> blocks;
    private List<Velocity> velocities;

    /**
     * constructor.
     *
     * @param levelName   the name of the level
     * @param background  the background
     * @param velocities  the speed of the balls
     * @param paddleSpeed the speed of the paddle
     * @param paddleWidth the width of the paddle
     * @param blocks      list of blocks
     */
    public LevelInfoGeneric(String levelName, Sprite background, List<Velocity> velocities
            , int paddleSpeed, int paddleWidth, List<Block> blocks) {
        this.levelName = levelName;
        this.background = background;
        this.velocities = velocities;
        this.paddleSpeed = paddleSpeed;
        this.paddleWidth = paddleWidth;
        this.blocks = blocks;
    }

    @Override
    public int numberOfBalls() {
        return this.velocities.size();
    }

    @Override
    public List<Velocity> initialBallVelocities() {
        return this.velocities;
    }

    @Override
    public int paddleSpeed() {
        return this.paddleSpeed;
    }

    @Override
    public int paddleWidth() {
        return this.paddleWidth;
    }

    @Override
    public String levelName() {
        return this.levelName;
    }

    @Override
    public Sprite getBackground() {
        return this.background;
    }

    @Override
    public List<Block> blocks() {
        return this.blocks;
    }

    @Override
    public int numberOfBlocksToRemove() {
        return this.blocks.size();
    }
}