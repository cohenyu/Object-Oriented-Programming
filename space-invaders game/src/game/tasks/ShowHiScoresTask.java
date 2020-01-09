package game.tasks;

import biuoop.KeyboardSensor;
import game.animation.Animation;
import game.animation.AnimationRunner;

import game.animation.KeyPressStoppableAnimation;

/**
 *@author yuval Cohen
 * This class show the high score table the game.
 */
public class ShowHiScoresTask implements Task<Void> {
    private AnimationRunner runner;
    private Animation highScoresAnimation;
    private KeyboardSensor keyboard;

    /**
     * constructor.
     *
     * @param runner              - The animationRunner
     * @param highScoresAnimation - The score display
     * @param keyboard            - KeyboardSensor
     */
    public ShowHiScoresTask(AnimationRunner runner, Animation highScoresAnimation, KeyboardSensor keyboard) {
        this.runner = runner;
        this.highScoresAnimation = highScoresAnimation;
        this.keyboard = keyboard;
    }

    /**
     * this function run the given animation.
     *
     * @return void = null
     */
    public Void run() {
        this.runner.run(new KeyPressStoppableAnimation(
                this.keyboard, this.keyboard.SPACE_KEY, this.highScoresAnimation));
        return null;
    }
}