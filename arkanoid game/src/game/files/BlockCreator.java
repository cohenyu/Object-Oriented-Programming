package game.files;

import game.objects.Block;

/**
 * @author yuval Cohen
 * game.files.BlockCreator is an interface of a factory-object that is used for creating blocks.
 */
public interface BlockCreator {

    /**
     * Create a block at the specified location.
     *
     * @param xpos - x location
     * @param ypos - y location
     * @return new block
     */
    Block create(int xpos, int ypos);
}