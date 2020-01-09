package game.score;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;

/**
 * @author yuval Cohen
 * class that hold an high score table.
 */
public class HighScoresTable implements Serializable {
    public static final String FILE_NAME = "highscores";
    private static final int DEFAULT_HIGHSCORES_SIZE = 5;

    private List<ScoreInfo> highScores;
    private int size;

    /**
     * Create an empty high-scores table with the specified size.
     * The size means that the table holds up to size top scores.
     *
     * @param size the size of the high-scores table.
     */
    public HighScoresTable(int size) {
        this.size = size;
        this.highScores = new ArrayList<ScoreInfo>();
    }

    /**
     * Add a high-score.
     *
     * @param score game.score.ScoreInfo
     */
    public void add(ScoreInfo score) {
        int rank = this.getRank(score.getScore());
        if (rank != -1) {
            this.highScores.add(rank - 1, score);
        }

        if (this.highScores.size() > this.size) {
            this.highScores.remove(this.size);
        }
    }

    /**
     * Return table size.
     *
     * @return int
     */
    public int size() {
        return this.highScores.size();
    }

    /**
     * Return the current high scores.
     * The list is sorted such that the highest
     * scores come first.
     *
     * @return current high scores.
     */
    public List<ScoreInfo> getHighScores() {
        return this.highScores;
    }

    /**
     * return the rank of the current score that means where will it
     * be on the list if added.
     *
     * @param score the score to check
     * @return the rank
     */
    public int getRank(int score) {
        int i;
        for (i = 0; i < this.highScores.size(); i++) {
            if (this.highScores.get(i).getScore() < score) {
                break;
            }
        }
        if (i < this.size) {
            return i + 1;
        }
        return -1;
    }

    /**
     * Clears the table.
     */
    public void clear() {
        this.highScores.clear();
    }

    /**
     * Load table data from file.
     * Current table data is cleared.
     *
     * @param filename a given file
     * @throws IOException an exception
     */
    public void load(File filename) throws IOException {

        HighScoresTable temp = HighScoresTable.loadFromFile(filename);
        if (temp != null) {
            this.highScores = temp.highScores;
            this.size = temp.size;
        }
    }

    /**
     * Save table data to the specified file.
     *
     * @param filename the file to save the high score table to.
     * @throws IOException exception if there was a problem saving to the file.
     */
    public void save(File filename) throws IOException {
        FileOutputStream writer = null;

        try {
            writer = new FileOutputStream(filename);
            ObjectOutputStream objectWriter = new ObjectOutputStream(writer);
            objectWriter.writeObject(this);

        } catch (Exception e) {
            System.err.println("Failed saving file: " + filename.getName());
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                System.err.println("Failed closing file: " + filename.getName());
            }
        }
    }

    /**
     * Read a table from file and return it.
     * If the file does not exist, or there is a problem with
     * reading it, an empty table is returned.
     *
     * @param filename a given file
     * @return game.score.HighScoresTable
     */
    public static HighScoresTable loadFromFile(File filename) {
        FileInputStream reader = null;

        try {
            reader = new FileInputStream(filename);
            ObjectInputStream objectReader = new ObjectInputStream(reader);
            return (HighScoresTable) objectReader.readObject();

        } catch (Exception e) {
            return new HighScoresTable(DEFAULT_HIGHSCORES_SIZE);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                System.err.println("Failed closing file: " + filename.getName());
            }
        }
    }
}