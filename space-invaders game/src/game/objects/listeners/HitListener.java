package game.objects.listeners;

import game.objects.Ball;
import game.objects.Block;

/**
 * @author Yuval Cohen
 * Objects that want to be notified of hit events, should implement the
 * game.objects.listeners.HitListener interface, and register themselves with a
 * game.objects.listeners.HitNotifier object using its addHitListener method.
 */
public interface HitListener {

    /**
     * This method is called whenever the beingHit object is hit.
     * The hitter parameter is the game.objects.Ball that's doing the hitting.
     *
     * @param beingHit - the beingHit object
     * @param hitter   - the hitter object
     */
    void hitEvent(Block beingHit, Ball hitter);
}