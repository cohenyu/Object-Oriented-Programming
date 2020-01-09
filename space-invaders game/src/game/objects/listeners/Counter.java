package game.objects.listeners;

/**
 * @author Yuval Cohen
 * Counter is a simple class that is used for counting things.
 */
public class Counter {
    //Member
    private int count;

    /**
     * constructor that start counting from 0.
     */
    public Counter() {
        this.count = 0;
    }

    /**
     * constructor with option to start counting from any number.
     *
     * @param count - the number that we start counting from
     */
    public Counter(int count) {
        this.count = count;
    }

    /**
     * add number to current count.
     *
     * @param number - value
     */
    public void increase(int number) {
        this.count += number;
    }

    /**
     * subtract number from current count.
     *
     * @param number - value
     */
    public void decrease(int number) {
        this.count -= number;
    }

    /**
     * get current count.
     *
     * @return - the current count
     */
    public int getValue() {
        return this.count;
    }
}