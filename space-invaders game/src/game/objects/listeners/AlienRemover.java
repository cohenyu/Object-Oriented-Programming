package game.objects.listeners;

import game.objects.Ball;
import game.objects.Block;
import game.spaceInvaders.AliensFormation;

/**
 * @author Yuval Cohen
 * a AlienRemover is in charge of removing aliens from the gameLevel.
 */
public class AlienRemover implements HitListener {
    private AliensFormation aliensFormation;

    /**
     * constructor.
     *
     * @param aliensFormation a collection which holds all the enemies which are alive.
     */
    public AlienRemover(AliensFormation aliensFormation) {
        this.aliensFormation = aliensFormation;
    }

    /**
     * alien remove from the gameLevel if was a hit.
     *
     * @param beingHit the block being hit.
     * @param hitter   the hitting ball.
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        if (!(beingHit.getHitPoints() > 0)) {
            aliensFormation.removeFromGame(beingHit);
        }
    }
}