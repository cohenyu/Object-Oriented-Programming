package game.levels;

import game.animation.AnimationRunner;
import game.animation.EndScreen;
import biuoop.KeyboardSensor;
import game.animation.HighScoresAnimation;
import game.animation.KeyPressStoppableAnimation;
import game.objects.indicators.LevelNameIndicator;
import game.objects.indicators.LivesIndicator;
import game.objects.indicators.ScoreIndicator;
import game.objects.listeners.Counter;
import game.score.HighScoresTable;
import game.score.ScoreInfo;

import java.io.File;
import java.util.List;

/**
 * @author yuval Cohen
 * class responsible for running multiple level one after another.
 */
public class GameFlow {
    //members
    private AnimationRunner animationRunner;
    private KeyboardSensor keyboardSensor;
    private Counter score;
    private Counter lives;
    private Counter remainingBlocks;
    private HighScoresTable highScoresTable;

    /**
     * constructor.
     *
     * @param ar              - the game.animation runner of the game.
     * @param ks              - the keyboard sensor of the game.
     * @param live            - the amount of time the player can lose all his balls.
     * @param highScoresTable - the scores of the players on the game
     */
    public GameFlow(AnimationRunner ar, KeyboardSensor ks, int live, HighScoresTable highScoresTable) {
        this.animationRunner = ar;
        this.keyboardSensor = ks;
        this.score = new Counter();
        this.lives = new Counter(live);
        this.remainingBlocks = new Counter();
        this.highScoresTable = highScoresTable;
    }

    /**
     * runs sequence of game.levels.
     *
     * @param levels - the game.levels of the game
     */
    public void runLevels(List<LevelInformation> levels) {

        for (LevelInformation levelInfo : levels) {
            GameLevel level = new GameLevel(levelInfo, this.keyboardSensor
                    , this.animationRunner, score, this.lives, remainingBlocks);

            level.initialize();

            level.addSprite(new ScoreIndicator(this.score));
            level.addSprite(new LivesIndicator(this.lives));
            level.addSprite(new LevelNameIndicator(levelInfo.levelName()));

            do {
                level.playOneTurn();
            } while (this.lives.getValue() > 0 && this.remainingBlocks.getValue() > 0);

            if (this.lives.getValue() == 0) {
                gameEnded(false);
                return;
            }
        }
        gameEnded(true);
    }

    /**
     * End of the game with dialog window and end screen with the score.
     *
     * @param win true / false if the player win  / lose
     */
    private void gameEnded(boolean win) {

        if (this.highScoresTable.getRank(this.score.getValue()) != -1) {
            String name = this.animationRunner.dialogWindow("Enter Name", "What is your name?", "Anonymous");
            this.highScoresTable.add(new ScoreInfo(name, this.score.getValue()));
        }

        try {
            this.highScoresTable.save(new File(HighScoresTable.FILE_NAME));
        } catch (Exception e) {
            System.out.println("Failed to save file");
        }

        this.animationRunner.run(new KeyPressStoppableAnimation(
                this.keyboardSensor, this.keyboardSensor.SPACE_KEY, new EndScreen(this.score.getValue(), win)));
        this.animationRunner.run(new KeyPressStoppableAnimation(
                this.keyboardSensor, this.keyboardSensor.SPACE_KEY, new HighScoresAnimation(this.highScoresTable)));
    }

}