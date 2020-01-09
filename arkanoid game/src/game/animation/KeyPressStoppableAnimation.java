package game.animation;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

/**
 * @author yuval.Yuval Cohen
 * Animation that can be stopped.
 */
public class KeyPressStoppableAnimation implements Animation {
    private KeyboardSensor sensor;
    private String key;
    private Animation animation;
    private boolean isAlreadyPressed;
    private boolean stop;

    /**
     * constructor.
     *
     * @param sensor    keyboardSensor
     * @param key       the key that indicts when to stop the current animation.
     * @param animation the animation to draw
     */
    public KeyPressStoppableAnimation(KeyboardSensor sensor, String key, Animation animation) {
        this.sensor = sensor;
        this.key = key;
        this.animation = animation;
        this.stop = false;
        this.isAlreadyPressed = true;
    }

    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        this.animation.doOneFrame(d, dt);
        if (sensor.isPressed(key) && !this.isAlreadyPressed) {
            this.stop = true;
        } else if (!(sensor.isPressed(this.key))) {
            isAlreadyPressed = false;
        }
    }

    @Override
    public boolean shouldStop() {
        return stop;
    }
}