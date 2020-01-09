package game.levels;

import game.animation.Animation;
import game.animation.AnimationRunner;
import game.animation.CountdownAnimation;
import game.animation.KeyPressStoppableAnimation;
import game.animation.PauseScreen;
import game.objects.Ball;
import game.objects.Block;
import game.objects.GameEnvironment;
import game.objects.Paddle;
import game.objects.collision.Collidable;
import game.objects.PaddleShot;
import game.objects.BallCreator;
import geometry.Rectangle;
import geometry.Point;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import game.objects.listeners.BallRemover;
import game.objects.listeners.BlockRemover;
import game.objects.listeners.Counter;
import game.objects.listeners.ScoreTrackingListener;
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

    // members
    private Paddle paddle;
    private SpriteCollection sprites;
    private GameEnvironment environment;
    private Counter remainBlocks;
    private Counter score;
    private Counter lives;
    private AnimationRunner runner;
    private boolean running;
    private KeyboardSensor keyboard;
    private LevelInformation levelInformation;
    private List<Ball> bullets;

    /**
     * constructor.
     *
     * @param levelInfo       - level the level Information object for the current level.
     * @param keyboard        - a keyboard sensor connected to a gui object.
     * @param animationRunner - an game.animation runner connected to a gui object.
     * @param score           - a score counter holding the current score.
     * @param lives           - a lives counter holding the current lives.
     * @param remainBlocks    - a lives counter holding the current lives.
     */
    public GameLevel(LevelInformation levelInfo, KeyboardSensor keyboard, AnimationRunner animationRunner,
                     Counter score, Counter lives, Counter remainBlocks) {
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
        this.remainBlocks = remainBlocks;
        this.score = score;
        this.lives = lives;
        this.runner = animationRunner;
        this.running = true;
        this.keyboard = keyboard;
        this.levelInformation = levelInfo;
        this.bullets = new ArrayList<>();
    }

    /**
     * return true if the animation loop should end, and false otherwise.
     *
     * @return true / false
     */
    public boolean shouldStop() {
        return !this.running;
    }

    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        this.sprites.drawAllOn(d);
        this.sprites.notifyAllTimePassed(dt);

        if (levelInformation.initialAliensFormation().shouldLose() || paddle.ifHit()) {
            this.running = false;
            this.lives.decrease(1);
            levelInformation.initialAliensFormation().resetPos();

            for (Ball shot : this.bullets) {
                shot.removeFromGame(this);
            }
        }

        if (this.remainBlocks.getValue() == 0) {
            this.running = false;
        }

        if (this.keyboard.isPressed("p")) {
            this.runner.run(new KeyPressStoppableAnimation(keyboard, KeyboardSensor.SPACE_KEY, new PauseScreen()));
        }

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

        this.addSprite(this.levelInformation.getBackground());
        BallRemover ballRemover = new BallRemover(this);
        ScoreTrackingListener stl = new ScoreTrackingListener(this.score);

        //death - region
        Block d = new Block(new Rectangle(new Point(BORDER_SIZE
                , this.runner.getGuiHeight() - BORDER_SIZE + BLOCK_HEIGHT)
                , this.runner.getGuiWidth() - 2 * BORDER_SIZE, BORDER_SIZE), Color.lightGray);
        d.setHitPoints(0);
        d.addHitListener(ballRemover);
        d.addToGame(this);

        Block u = new Block(new Rectangle(new Point(0, 0), this.runner.getGuiWidth(), BORDER_SIZE), Color.lightGray);
        u.setHitPoints(0);
        u.addHitListener(ballRemover);
        u.addToGame(this);

        this.remainBlocks.increase(levelInformation.numberOfBlocksToRemove() - this.remainBlocks.getValue());

        List<Block> blocksOnScreen = this.levelInformation.blocks();
        for (Block b : blocksOnScreen) {
            b.addHitListener(new BlockRemover(this));
            b.addHitListener(new BallRemover(this));
            b.addToGame(this);
        }
        levelInformation.initialAliensFormation().addHitListener(new BlockRemover(this, this.remainBlocks));
        levelInformation.initialAliensFormation().addHitListener(stl);
        levelInformation.initialAliensFormation().addToGame(this);

        levelInformation.initialAliensFormation().addToGame(this);
    }

    /**
     * This method starts a game round.
     */
    public void playOneTurn() {


        createPaddle();

        this.runner.run(new CountdownAnimation(2, 3, sprites));

        this.running = true;
        this.runner.run(this);

        paddle.removeFromGame(this);


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
     * @return the game environment of the game.
     */
    public GameEnvironment getEnvironment() {
        return this.environment;
    }

    /**
     * This method creates paddle and adds it to the game.
     */
    public void createPaddle() {
        paddle = new Paddle(this.keyboard, this.levelInformation.paddleSpeed(), this.levelInformation.paddleWidth()
                , new Point(this.runner.getGuiWidth() / 2 - (this.levelInformation.paddleWidth() / 2), 550)
                , this.runner.getGuiWidth());

        GameLevel gameLevel = this;
        paddle.setBallCreator(new BallCreator() {
            @Override
            public Ball create(int xpos, int ypos) {
                Ball shot = new PaddleShot(xpos, ypos);
                shot.setGameEnvironment(environment);
                shot.addToGame(gameLevel);
                gameLevel.addShots(shot);
                return shot;
            }
        });
        paddle.addToGame(this);
    }

    /**
     * add shots to the game.
     *
     * @param ball the shot to add to the game.
     */
    public void addShots(Ball ball) {
        bullets.add(ball);
    }
}