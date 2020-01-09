package game.objects.listeners;

import game.objects.Ball;
import game.objects.Block;

/**
 * @author Yuval Cohen
 * tracks the game score.
 */
public class ScoreTrackingListener implements HitListener {

    //member
    private Counter currentScore;

    /**
     * constructor.
     *
     * @param scoreCounter - counter of the score in the game
     */
    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter;
    }

    /**
     * scoring rule: hitting a block is worth 5 points, and destroying a block
     * is worth and additional 10 points.
     *
     * @param beingHit - the beingHit object
     * @param hitter   - the hitter object
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        if (!(beingHit.getHitPoints() > 0)) {
            currentScore.increase(100);
        }
    }
}