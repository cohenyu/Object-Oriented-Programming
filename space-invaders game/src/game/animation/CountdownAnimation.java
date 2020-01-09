package game.animation;

import biuoop.DrawSurface;
import game.objects.listeners.Counter;
import game.objects.sprite.SpriteCollection;

import java.awt.Color;

/**
 * @author Yuval Cohen
 * The CountdownAnimation will display the given gameScreen,
 * for numOfSeconds seconds, and on top of them it will show
 * a countdown from countFrom back to 1 and then will be GO!.
 */
public class CountdownAnimation implements Animation {
    // Members
    private SpriteCollection gameScreen;
    private Counter counter;
    private boolean stop;
    private long millisPerNum;
    private long timer;

    /**
     * constructor.
     *
     * @param numOfSeconds - How long it will be displayed on the screen.
     * @param countingFrom - From which number start the countdown.
     * @param gameScreen   - the game screen after the countdown is over
     */
    public CountdownAnimation(double numOfSeconds, int countingFrom, SpriteCollection gameScreen) {
        this.gameScreen = gameScreen;
        this.counter = new Counter(countingFrom);
        this.stop = false;
        this.millisPerNum = (long) (numOfSeconds * 1000) / (countingFrom + 1);
        this.timer = System.currentTimeMillis();

    }

    /**
     * This method prints the countdown to the screen.
     *
     * @param d  a draw surface
     * @param dt dt specifies the amount of seconds passed since the last call.
     */
    public void doOneFrame(DrawSurface d, double dt) {
        String text;
        if (this.counter.getValue() == 0) {
            text = "Go!";
        } else {
            text = Integer.toString(this.counter.getValue());
        }
        this.gameScreen.drawAllOn(d);
        d.setColor(Color.lightGray);
        d.drawText(380, 280, text, 60);
        d.setColor(new Color(219, 47, 57));
        d.drawText(382, 282, text, 60);

        if (System.currentTimeMillis() - this.timer > this.millisPerNum) {
            this.timer = System.currentTimeMillis();
            this.counter.decrease(1);
        }
        if (counter.getValue() < 0) {
            stop = true;
        }
    }

    /**
     * Once the count is over, stop this game.animation.
     *
     * @return true / false
     */
    public boolean shouldStop() {
        return this.stop;
    }
}