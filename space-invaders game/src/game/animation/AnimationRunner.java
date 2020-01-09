package game.animation;

import biuoop.DialogManager;
import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;

/**
 * @author Yuval Cohen
 * The AnimationRunner takes an Animation object and runs it.
 */
public class AnimationRunner {
    private GUI gui;
    private int framesPerSecond;
    private Sleeper sleeper;
    private double dt;

    /**
     * constructor.
     *
     * @param gui - a gui
     */
    public AnimationRunner(GUI gui) {
        this.gui = gui;
        this.framesPerSecond = 60;
        this.sleeper = new Sleeper();
        this.dt = 1.0 / this.framesPerSecond;
    }

    /**
     * Return the width of the screen.
     *
     * @return gui
     */
    public int getGuiWidth() {
        return this.gui.getDrawSurface().getWidth();
    }

    /**
     * Return the height of the screen.
     *
     * @return gui
     */
    public int getGuiHeight() {
        return this.gui.getDrawSurface().getHeight();
    }

    /**
     * Shows a dialog and wait for input.
     *
     * @param text1 The title of the dialog.
     * @param text2 The text written to the user.
     * @param text3 The text written in the the text box once the dialog is shown.
     * @return the text inputted in the dialog.
     */
    public String dialogWindow(String text1, String text2, String text3) {
        DialogManager dialog = gui.getDialogManager();
        return dialog.showQuestionDialog(text1, text2, text3);
    }

    /**
     * takes an Animation object and runs it.
     *
     * @param animation an game.animation
     */
    public void run(Animation animation) {
        int millisecondsPerFrame = 1000 / this.framesPerSecond;

        while (!animation.shouldStop()) {
            // timing
            long startTime = System.currentTimeMillis();
            DrawSurface d = gui.getDrawSurface();

            animation.doOneFrame(d, this.dt);

            gui.show(d);
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                this.sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }
    }
}