import biuoop.KeyboardSensor;
import game.animation.AnimationRunner;
import biuoop.GUI;
import game.animation.HighScoresAnimation;
import game.animation.Menu;
import game.animation.MenuAnimation;
import game.files.LevelSpecificationReader;
import game.levels.LevelInformation;
import game.score.HighScoresTable;
import game.tasks.QuitGameTask;
import game.tasks.RunGameTask;
import game.tasks.ShowHiScoresTask;
import game.tasks.Task;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.List;

/**
 * @author Yuval Cohen
 * This class will start the game by run the levels.
 */
public class Ass6Game {
    public static final int LIVES = 7;
    public static final int WIDTH_SCREEN = 800;
    public static final int HEIGHT_SCREEN = 600;
    public static final String GAME_NAME = "Arkanoid";
    public static final String LEVEL_SET = "Level Sets";
    public static final String START_GAME = "Start Game";
    public static final String START_KEY = "s";
    public static final String HIGH_SCORE = "High Scores";
    public static final String SCORE_KEY = "h";
    public static final String QUIT = "Quit";
    public static final String QUIT_KEY = "q";
    public static final String DEFAULT_LEVELS = "level_sets.txt";

    /**
     * This is the main of the game.
     *
     * @param args - from command line
     */
    public static void main(String[] args) {
        GUI gui = new GUI(GAME_NAME, WIDTH_SCREEN, HEIGHT_SCREEN);
        List<LevelInformation> levels = null;
        AnimationRunner animationRunner = new AnimationRunner(gui);

        String fileName;
        if (args.length > 0) {
            fileName = args[0];
        } else {
            fileName = DEFAULT_LEVELS;
        }
        HighScoresTable highScoresTable = getHighScoresTable();


        while (true) {
            Menu<Task> lvlMenu = new MenuAnimation<>(LEVEL_SET, gui.getKeyboardSensor(), animationRunner);
            readLevel(lvlMenu, animationRunner, gui.getKeyboardSensor(), fileName);

            Menu<Task> mainMenu = new MenuAnimation<>(GAME_NAME, gui.getKeyboardSensor(), animationRunner);
            mainMenu.addSubMenu(START_KEY, START_GAME, lvlMenu);
            mainMenu.addSelection(SCORE_KEY, HIGH_SCORE, new ShowHiScoresTask(animationRunner
                    , new HighScoresAnimation(highScoresTable), gui.getKeyboardSensor()));
            mainMenu.addSelection(QUIT_KEY, QUIT, new QuitGameTask());
            animationRunner.run(mainMenu);
            mainMenu.getStatus().run();
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

    /**
     * reads a level sets file.
     *
     * @param menu     the menu which the level sets will be added to.
     * @param runner   the animation runner of the levels.
     * @param keyboard a keyboard sensor to detect the selection in the menu.
     * @param file     the file which contains the level sets.
     */
    private static void readLevel(Menu<Task> menu, AnimationRunner runner, KeyboardSensor keyboard, String file) {
        try (LineNumberReader reader = new LineNumberReader(
                new InputStreamReader(ClassLoader.getSystemClassLoader().getResourceAsStream(file)))) {

            String line = reader.readLine();
            String key = null;
            String message = null;

            while (line != null) {
                if (reader.getLineNumber() % 2 == 1) {
                    if (!line.contains(":")) {
                        System.out.println("The level set file is not valid!");
                        System.exit(0);
                    }

                    String[] splitted = line.split(":");
                    key = splitted[0].trim();
                    message = splitted[1].trim();
                    if (key.length() > 1) {
                        key = key.substring(0, 1);
                    }
                } else {
                    menu.addSelection(key, message, new RunGameTask(runner, keyboard, getLevel(line.trim())
                            , LIVES, getHighScoresTable()));
                }

                line = reader.readLine();
            }
        } catch (Exception e) {
            System.out.println("Could not read the level set file!");
            System.exit(0);
        }
    }

    /**
     * reads a levels set file and returns the levels it contains.
     *
     * @param levelFile the levels set file.
     * @return the levels the file contains.
     */
    private static List<LevelInformation> getLevel(String levelFile) {
        InputStream in = ClassLoader.getSystemClassLoader().getResourceAsStream(levelFile);
        List<LevelInformation> levelInformationList = null;

        try {
            LevelSpecificationReader reader = new LevelSpecificationReader();
            levelInformationList = reader.fromReader(new InputStreamReader(in));
        } catch (Exception e) {
            System.out.println("Could not parse level set file - '" + levelFile + "'");
            System.exit(0);
        }
        return levelInformationList;
    }
}