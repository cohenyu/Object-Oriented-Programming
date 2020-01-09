package game.files;

import game.objects.Block;

import java.util.Map;

/**
 * @author yuval Cohen
 * class which creates blocks from their symbol.
 */
public class BlocksFromSymbolsFactory {
    // Members
    private Map<String, Integer> spacerWidths;
    private Map<String, BlockCreator> blockCreators;

    /**
     * constructor.
     *
     * @param spacerWidths -
     * @param blockCreators -
     */
    public BlocksFromSymbolsFactory(Map<String, Integer> spacerWidths, Map<String, BlockCreator> blockCreators) {
        this.blockCreators = blockCreators;
        this.spacerWidths = spacerWidths;
    }

    /**
     * returns true if 's' is a valid space symbol.
     *
     * @param s a string represent a symbol.
     * @return true if 's' is a valid space symbol.
     */
    public boolean isSpaceSymbol(String s) {
        return this.spacerWidths.containsKey(s);
    }

    /**
     * returns true if 's' is a valid block symbol.
     *
     * @param s a string represent a symbol.
     * @return true if 's' is a valid block symbol.
     */
    public boolean isBlockSymbol(String s) {
        return this.blockCreators.containsKey(s);
    }

    /**
     * Return a block according to the definitions associated
     * with symbol s. The block will be located at position (xpos, ypos).
     *
     * @param s    - a symbol
     * @param xpos - the x location of the block
     * @param ypos - the y location of the block
     * @return a new block
     */
    public Block getBlock(String s, int xpos, int ypos) {
        return this.blockCreators.get(s).create(xpos, ypos);
    }

    /**
     * Returns the width in pixels associated with the given spacer-symbol.
     *
     * @param s a given spacer-symbol
     * @return the width in pixels
     */
    public int getSpaceWidth(String s) {
        return this.spacerWidths.get(s);
    }
}