package game.animation;

/**
 * @author Yuval Cohen
 * Animation that can be stopped
 * general menu to hold keys and the action when they are pressed.
 *
 * @param <T> <T> the type of the returned action.
 */
public interface Menu<T> extends Animation {

    /**
     * adds new selection to the menu.
     *
     * @param key       - key to wait for
     * @param message   -  line to print
     * @param returnVal - what to return
     */
    void addSelection(String key, String message, T returnVal);

    /**
     * @return T @return the chosen option from the menu.
     */
    T getStatus();

    /**
     * Adds sub menu to the menu.
     *
     * @param key     the key which will pressed in order to get to the sub menu.
     * @param message the message which describes the action.
     * @param subMenu the sub menu.
     */
    void addSubMenu(String key, String message, Menu<T> subMenu);

}