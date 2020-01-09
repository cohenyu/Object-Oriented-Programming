package game.levels;

import game.animation.Animation;
import game.animation.AnimationRunner;
import game.animation.CountdownAnimation;
import game.animation.KeyPressStoppableAnimation;
import game.animation.PauseScreen;
import game.objects.collision.Collidable;
import geometry.Rectangle;
import geometry.Point;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import game.objects.listeners.BallRemover;
import game.objects.listeners.BlockRemover;
import game.objects.listeners.Counter;
import game.objects.listeners.ScoreTrackingListener;
import game.objects.Ball;
import game.objects.Block;
import game.objects.GameEnvironment;
import game.objects.Paddle;
import game.objects.sprite.Sprite;
import game.objects.sprite.SpriteCollection;

import java.util.ArrayList;
import java.awt.Color;
import java.util.List;

/**
 * @author Yuval Cohen
 * game.levels.GameLevel class hold the sprites and the collidables, and be
 * in charge of the game.animation, initialize and run.
 */
public class GameLevel implements Animation {
    public static final int BLOCK_HEIGHT = 25;
    public static final int BORDER_SIZE = 25;
    public static final int RADIUS = 5;
    public static final int END_LEVEL_SCORE = 100;
    public static final int COUNTING_FROM = 3;
    public static final int NUM_SECONDS = 2;

    // members
    private SpriteCollection sprites;
    private GameEnvironment environment;
    private Counter rameinBlocks;
    private Counter rameinBalls;
    private Counter score;
    private Counter lives;
    private AnimationRunner runner;
    private boolean running;
    private KeyboardSensor keyboard;
    private LevelInformation levelInformation;

    /**
     * constructor.
     *
     * @param level           - level the level Information object for the current level.
     * @param keyboard        - a keyboard sensor connected to a gui object.
     * @param animationRunner - an game.animation runner connected to a gui object.
     * @param score           - a score counter holding the current score.
     * @param lives           - a lives counter holding the current lives.
     * @param rameinBlocks    - a lives counter holding the current lives.
     */
    public GameLevel(LevelInformation level, KeyboardSensor keyboard, AnimationRunner animationRunner,
                     Counter score, Counter lives, Counter rameinBlocks) {
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
        this.rameinBlocks = rameinBlocks;
        this.rameinBalls = new Counter();
        this.score = score;
        this.lives = lives;
        this.runner = animationRunner;
        this.running = true;
        this.keyboard = keyboard;
        this.levelInformation = level;
    }

    /**
     * return true if the game.animation loop should end, and false otherwise.
     *
     * @return true / false
     */
    public boolean shouldStop() {
        return !this.running;
    }

    @Override
    public void doOneFrame(DrawSurface d, double dt) {

        if (this.keyboard.isPressed("p")) {
            this.runner.run(new KeyPressStoppableAnimation(
                    this.keyboard, this.keyboard.SPACE_KEY, new PauseScreen()));
        }
        if (this.rameinBlocks.getValue() == 0) {
            this.score.increase(END_LEVEL_SCORE);
            this.running = false;
        }
        if (this.rameinBalls.getValue() == 0) {
            this.lives.decrease(1);
            this.running = false;
        }

        this.sprites.drawAllOn(d);
        this.sprites.notifyAllTimePassed(dt);
    }


    /**
     * add collidable to the environment.
     *
     * @param c - a collidable
     */
    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }

    /**
     * add game.objects.sprite to the environment.
     *
     * @param s - a game.objects.sprite
     */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }

    /**
     * Initialize a new game: create the Blocks, border blocks,
     * game.objects.Ball and game.objects.Paddle, and add them to the game.
     */
    public void initialize() {
        List<Block> blocks = new ArrayList<>();
        Color color;
        ScoreTrackingListener scoreTrack = new ScoreTrackingListener(this.score);

        this.addSprite(this.levelInformation.getBackground());

        for (Block b : this.levelInformation.blocks()) {
            b.addHitListener(new BlockRemover(this, this.rameinBlocks));
            b.addHitListener(scoreTrack);
            b.addToGame(this);
            rameinBlocks.increase(1);
        }


        // Border blocks
        Block b = new Block(new Rectangle(new Point(0, 25), this.runner.getGuiWidth(), BLOCK_HEIGHT), Color.lightGray);
        blocks.add(b);
        b = new Block(new Rectangle(new Point(0, 2 * BORDER_SIZE), BORDER_SIZE
                , this.runner.getGuiHeight()), Color.lightGray);
        blocks.add(b);
        b = new Block(new Rectangle(new Point(this.runner.getGuiWidth() - BORDER_SIZE, 2 * BORDER_SIZE),
                BORDER_SIZE, this.runner.getGuiHeight()), Color.lightGray);
        blocks.add(b);

        //death - region
        b = new Block(new Rectangle(new Point(BORDER_SIZE, this.runner.getGuiHeight() - BORDER_SIZE + BLOCK_HEIGHT),
                this.runner.getGuiWidth() - 2 * BORDER_SIZE, BORDER_SIZE), Color.lightGray);
        b.addHitListener(new BallRemover(this, this.rameinBalls));
        blocks.add(b);

        // setting hitPoints to each border block
        for (Block block : blocks) {
            block.setHitPoints(0);
            block.addToGame(this);
        }

    }

    /**
     * This method starts a game round.
     */
    public void playOneTurn() {
        // create paddle
        Paddle p = new Paddle(this.keyboard, this.levelInformation.paddleSpeed(), this.levelInformation.paddleWidth()
                , new Point(this.runner.getGuiWidth() / 2 - (this.levelInformation.paddleWidth() / 2), 550)
                , this.runner.getGuiWidth());
        p.addToGame(this);

        // Create balls
        createBalls();

        this.runner.run(new CountdownAnimation(NUM_SECONDS, COUNTING_FROM, this.sprites));
        this.running = true;
        this.runner.run(this);

        p.removeFromGame(this);
    }

    /**
     * Run the game -- start the game.animation loop.
     */
    public void run() {

        while (this.lives.getValue() != 0) {
            playOneTurn();
            this.lives.decrease(1);
        }
    }

    /**
     * Remove collidable from the game.
     *
     * @param c - a collidable
     */
    public void removeCollidable(Collidable c) {
        this.environment.removeCollidable(c);
    }

    /**
     * Remove game.objects.sprite from the game.
     *
     * @param s - a game.objects.sprite
     */
    public void removeSprite(Sprite s) {
        this.sprites.removeSprite(s);
    }

    /**
     * creating the balls for the game.
     */
    public void createBalls() {

        Ball[] balls = new Ball[this.levelInformation.numberOfBalls()];
        for (int i = 0; i < balls.length; i++) {
            balls[i] = new Ball(new Point(this.runner.getGuiWidth() / 2
                    , this.runner.getGuiHeight() - 68), RADIUS, Color.white);
            balls[i].setVelocity(this.levelInformation.initialBallVelocities().get(i).getDx()
                    , this.levelInformation.initialBallVelocities().get(i).getDy());
            balls[i].setGameEnvironment(this.environment);
            balls[i].addToGame(this);
            this.rameinBalls.increase(1);
        }
    }
}