package game.animation;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Animation that can be stopped
 * animates a menu.
 *
 * @param <T> the type of the returned values of the menu.
 */
public class MenuAnimation<T> implements Menu<T> {
    // Members
    private String menuTitle;
    private List<String> keys;
    private List<String> messages;
    private List<T> actions;
    private T status;
    private boolean stop;
    private KeyboardSensor ks;
    private AnimationRunner animationRunner;
    private List<Boolean> isSub;
    private List<Menu<T>> subMenus;


    /**
     * constructor.
     *
     * @param menuTitle       the title of the menu.
     * @param keyboardSensor  a keyboard sensor
     * @param animationRunner an animation runner
     */
    public MenuAnimation(String menuTitle, KeyboardSensor keyboardSensor,
                         AnimationRunner animationRunner) {
        this.menuTitle = menuTitle;
        this.keys = new ArrayList<String>();
        this.messages = new ArrayList<>();
        this.actions = new ArrayList<>();
        this.stop = false;
        this.ks = keyboardSensor;
        this.animationRunner = animationRunner;
        this.isSub = new ArrayList<>();
        this.subMenus = new ArrayList<>();
    }

    @Override
    public void addSelection(String key, String message, T returnVal) {
        this.keys.add(key);
        this.messages.add(message);
        this.actions.add(returnVal);
        this.isSub.add(false);
        this.subMenus.add(null);
    }

    @Override
    public T getStatus() {
        this.stop = false;
        T statusTemp = this.status;
        this.status = null;
        return statusTemp;
    }

    @Override
    public void doOneFrame(DrawSurface d, double dt) {

        for (int i = 0; i < this.actions.size(); ++i) {
            if (this.ks.isPressed(this.keys.get(i))) {
                if (!this.isSub.get(i)) {
                    this.status = this.actions.get(i);
                    this.stop = true;
                } else {
                    this.animationRunner.run(this.subMenus.get(i));
                    this.status = this.subMenus.get(i).getStatus();
                    this.stop = true;
                }
                break;
            }
        }

        d.setColor(Color.BLACK);
        d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
        if (!stop) {
            d.setColor(Color.magenta);
            d.drawText(254, 102, this.menuTitle, 70);
            d.setColor(Color.cyan);
            d.drawText(250, 100, this.menuTitle, 70);
            d.setColor(Color.magenta);

            for (int i = 0; i < this.messages.size(); i++) {
                d.drawText(250, 200 + (60 * i), "(" + this.keys.get(i) + ")  " + this.messages.get(i), 32);
            }
        }
    }

    @Override
    public boolean shouldStop() {
        return this.stop;
    }

    @Override
    public void addSubMenu(String key, String message, Menu<T> subMenu) {
        this.keys.add(key);
        this.messages.add(message);
        this.actions.add(null);
        this.subMenus.add(subMenu);
        this.isSub.add(true);
    }
}