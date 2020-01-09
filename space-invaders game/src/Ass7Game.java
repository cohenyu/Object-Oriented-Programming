import biuoop.KeyboardSensor;
import game.animation.AnimationRunner;
import biuoop.GUI;
import game.animation.HighScoresAnimation;
import game.animation.Menu;
import game.animation.MenuAnimation;
import game.score.HighScoresTable;
import game.tasks.QuitGameTask;
import game.tasks.RunGameTask;
import game.tasks.ShowHiScoresTask;
import game.tasks.Task;

import java.io.File;

/**
 * @author Yuval Cohen
 * This class will start the game by run the levels.
 */
public class Ass7Game {
    public static final int LIVES = 3;
    public static final int WIDTH_SCREEN = 800;
    public static final int HEIGHT_SCREEN = 600;
    public static final String GAME_NAME = "Space Invaders";
    public static final String START_GAME = "Start Game";
    public static final String START_KEY = "s";
    public static final String HIGH_SCORE = "High Scores";
    public static final String SCORE_KEY = "h";
    public static final String QUIT = "Quit";
    public static final String QUIT_KEY = "q";

    /**
     * starts the main menu.
     *
     * @param args from the command line
     */
    public static void main(String[] args) {
        GUI gui = new GUI(GAME_NAME, WIDTH_SCREEN, HEIGHT_SCREEN);
        KeyboardSensor keyboard = gui.getKeyboardSensor();
        AnimationRunner runner = new AnimationRunner(gui);
        HighScoresTable highScoresTable = getHighScoresTable();
        Menu<Task> menu = new MenuAnimation<>(GAME_NAME, gui.getKeyboardSensor(), runner);
        menu.addSelection(START_KEY, START_GAME, new RunGameTask(runner, keyboard, LIVES, highScoresTable));
        menu.addSelection(SCORE_KEY, HIGH_SCORE, new ShowHiScoresTask(runner
                , new HighScoresAnimation(highScoresTable), keyboard));
        menu.addSelection(QUIT_KEY, QUIT, new QuitGameTask());

        while (true) {
            runner.run(menu);
            menu.getStatus().run();
        }
    }

    /**
     * @return the high score table of the game.
     */
    public static HighScoresTable getHighScoresTable() {
        HighScoresTable scores = HighScoresTable.loadFromFile(new File(HighScoresTable.FILE_NAME));

        try {
            scores.save(new File(HighScoresTable.FILE_NAME));
        } catch (Exception e) {
            System.out.println("Failed to save file");
        }
        return scores;
    }
}