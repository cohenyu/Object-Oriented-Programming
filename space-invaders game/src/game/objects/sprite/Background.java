package game.objects.sprite;

import biuoop.DrawSurface;

import java.awt.Color;
import java.awt.Image;

/**
 * This class holds a background of a level in the game.
 */
public class Background implements Sprite {
    //Members
    private Image image;
    private Color color;
    private boolean isColor;

    /**
     * constructor of color background.
     *
     * @param color the color of the backgrpund.
     */
    public Background(Color color) {
        this.color = color;
        this.image = null;
        this.isColor = true;
    }

    /**
     * constructor of image background.
     *
     * @param image the image of the background
     */
    public Background(Image image) {
        this.image = image;
        this.isColor = false;
        this.color = null;
    }

    @Override
    public void drawOn(DrawSurface d) {

        if (isColor) {
            d.setColor(this.color);
            d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
            d.setColor(Color.white);
            d.fillRectangle(0, 0, d.getWidth(), 25);
        } else {
            d.drawImage(0, 0, this.image);
        }
        d.setColor(Color.white);
        d.fillRectangle(0, 0, d.getWidth(), 25);
    }

    @Override
    public void timePassed(double dt) {

    }
}
