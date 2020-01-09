package game.objects.listeners;

import game.levels.GameLevel;
import game.objects.Ball;
import game.objects.Block;

/**
 * @author Yuval Cohen
 * BlockRemover is in charge of removing blocks from the game, as well as
 * keeping count of the number of blocks that remain.
 */
public class BlockRemover implements HitListener {
    private GameLevel game;
    private Counter remainingBlocks;

    /**
     * constructor.
     *
     * @param game            - the current game.levels.GameLevel
     * @param remainingBlocks - a counter that holds the number of blocks that remain in the game.
     */
    public BlockRemover(GameLevel game, Counter remainingBlocks) {
        this.game = game;
        this.remainingBlocks = remainingBlocks;
    }

    /**
     * constructor.
     *
     * @param game - the current gameLevel
     */
    public BlockRemover(GameLevel game) {
        this.game = game;
        this.remainingBlocks = null;
    }

    /**
     * Blocks that are hit and reach 0 hit-points should be removed
     * from the game.
     *
     * @param beingHit - the beingHit object
     * @param hitter   - the hitter object
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        if (beingHit.getHitPoints() == 0) {
            beingHit.removeHitListener(this);
            beingHit.removeFromGame(this.game);
            if (remainingBlocks != null) {
                this.remainingBlocks.decrease(1);
            }
        }
    }
}