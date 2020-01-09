package game.objects.listeners;

/**
 * @author Yuval Cohen
 * The game.objects.listeners.HitNotifier interface indicate that game.objects that implement it
 * send notifications when they are being hit.
 */
public interface HitNotifier {

    /**
     * Add hl as a listener to hit events.
     *
     * @param hl - a listener
     */
    void addHitListener(HitListener hl);

    /**
     * Remove hl from the list of game.objects.listeners to hit events.
     *
     * @param hl - a listener
     */
    void removeHitListener(HitListener hl);
}