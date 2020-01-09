package game.tasks;


/**
 * @author yuval Cohen
 * This class is responsible for closing the game.
 */
public class QuitGameTask implements Task<Void> {

    /**
     * constructor.
     */
    public QuitGameTask() {

    }

    /**
     * this function run the given animation.
     *
     * @return void = null
     */
    public Void run() {
        System.exit(0);
        return null;
    }
}