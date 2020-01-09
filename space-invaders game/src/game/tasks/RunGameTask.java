package game.tasks;

import biuoop.KeyboardSensor;
import game.animation.AnimationRunner;

import game.levels.GameFlow;
import game.levels.LevelInfoInvaders;
import game.levels.LevelInformation;
import game.score.HighScoresTable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yuval Cohen
 * This class is responsible for start the game.
 */
public class RunGameTask implements Task<Void> {
    private AnimationRunner runner;
    private KeyboardSensor keyboard;
    private int lives;
    private HighScoresTable highScoresTable;

    /**
     * constructor.
     *
     * @param runner          - The animationRunner
     * @param keyboard        - KeyboardSensor
     * @param lives           - number of lives
     * @param highScoresTable - the high score of the game
     */
    public RunGameTask(AnimationRunner runner, KeyboardSensor keyboard, int lives, HighScoresTable highScoresTable) {
        this.runner = runner;
        this.keyboard = keyboard;
        this.lives = lives;
        this.highScoresTable = highScoresTable;
    }

    /**
     * this function run the given animation.
     *
     * @return void
     */
    public Void run() {

        List<LevelInformation> levels = new ArrayList<>();
        for (int levelNum = 1; levelNum <= 100; levelNum++) {
            levels.add(new LevelInfoInvaders(levelNum));
        }
        GameFlow gameFlow = new GameFlow(this.runner, this.keyboard, this.lives, this.highScoresTable);
        gameFlow.runLevels(levels);
        return null;
    }
}