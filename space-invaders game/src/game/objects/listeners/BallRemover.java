package game.objects.listeners;

import game.levels.GameLevel;
import game.objects.Ball;
import game.objects.Block;

/**
 * @author Yuval Cohen
 * A game.objects.listeners.BallRemover is in charge of removing balls from the gameLevel, as well as
 * keeping count of the number of balls that remain in the game.
 */
public class BallRemover implements HitListener {
    //members
    private GameLevel game;

    /**
     * constructor.
     *
     * @param game - the current game
     */
    public BallRemover(GameLevel game) {
        this.game = game;
    }


    /**
     * Ball that hits the death block should be removed
     * from the game.
     *
     * @param beingHit - the beingHit object
     * @param hitter   - the hitter object
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        hitter.removeFromGame(game);
    }
}