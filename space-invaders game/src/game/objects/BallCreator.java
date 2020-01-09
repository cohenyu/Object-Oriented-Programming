package game.objects;

/**
 * @author Yuval Cohen
 * interface for creating a ball factory.
 */
public interface BallCreator {

    /**
     * Create a ball at the specified location.
     *
     * @param xpos the x position of the ball.
     * @param ypos the y position of the ball.
     * @return the created ball.
     */
    Ball create(int xpos, int ypos);
}