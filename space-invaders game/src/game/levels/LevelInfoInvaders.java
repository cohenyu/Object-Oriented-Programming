package game.levels;

import game.objects.Block;
import game.objects.sprite.Background;
import game.objects.sprite.Sprite;
import game.spaceInvaders.AliensFormation;
import geometry.Point;
import geometry.Rectangle;


import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Yuval Cohen
 * holds information of one space invaders level.
 */
public class LevelInfoInvaders implements LevelInformation {
    private static final int SMALL_BLOCK_SIZE = 5;
    private static final int ROWS = 5;
    private static final int COLS = 10;
    private static final int SPEED = 50;
    private AliensFormation aliensFormation;
    private int battleNum;

    /**
     * creates new space invaders level.
     *
     * @param battleNum the current level.
     */
    public LevelInfoInvaders(int battleNum) {
        this.battleNum = battleNum;
        this.aliensFormation = new AliensFormation(ROWS, COLS, SPEED * (Math.pow(1.4, battleNum - 1)));
    }

    @Override
    public AliensFormation initialAliensFormation() {
        return this.aliensFormation;
    }

    @Override
    public int paddleSpeed() {
        return 650;
    }

    @Override
    public int paddleWidth() {
        return 75;
    }

    @Override
    public String levelName() {
        return "Battle no." + this.battleNum;
    }

    @Override
    public Sprite getBackground() {
        return new Background(Color.black);
    }

    @Override
    public List<Block> blocks() {
        List<Block> blocks = createShields(100, 500);
        blocks.addAll(createShields(560, 500));
        blocks.addAll(createShields(330, 500));
        blocks.addAll(initialAliensFormation().getEnemies());

        return blocks;
    }

    /**
     * creates the  shields.
     *
     * @param x the X pos of the upper left point.
     * @param y the  Y pos of the upper left point.
     * @return list of small protective blocks.
     */
    private List<Block> createShields(int x, int y) {
        List<Block> blocks = new ArrayList<>();
        for (int i = 0; i < 28; i++) {
            for (int j = 0; j < 3; j++) {
                Block block = new Block(new Rectangle(new Point(x + i * SMALL_BLOCK_SIZE, y + j * SMALL_BLOCK_SIZE)
                        , SMALL_BLOCK_SIZE, SMALL_BLOCK_SIZE), Color.CYAN);
                block.setHitPoints(1);
                blocks.add(block);
            }
        }
        return blocks;
    }

    @Override
    public int numberOfBlocksToRemove() {
        return 50;
    }
}
