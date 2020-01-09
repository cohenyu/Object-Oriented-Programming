package game.files;

import game.levels.LevelInfoGeneric;
import game.levels.LevelInformation;
import game.objects.Block;
import game.objects.sprite.Background;
import game.objects.sprite.Sprite;
import geometry.Velocity;

import javax.imageio.ImageIO;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author yuval Cohen
 * class which parses the level information file.
 */
public class LevelSpecificationReader {

    /**
     * parse the level information from a reader.
     *
     * @param reader the reader to get information from.
     * @return list of all the information on all the levels.
     */
    public List<LevelInformation> fromReader(java.io.Reader reader) {
        List<String> levelLines = null;
        List<LevelInformation> levelInformations = new ArrayList<>();

        BufferedReader bufferedReader = new BufferedReader(reader);

        try {
            levelLines = readLevel(bufferedReader);

            while (levelLines.size() > 0) {

                LevelInformation levelInfo = parasLevel(levelLines);
                levelInformations.add(levelInfo);
                levelLines = readLevel(bufferedReader);
            }
        } catch (Exception e) {
            System.out.println("No levels can be created for the game");
            System.exit(0);
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        return levelInformations;
    }

    /**
     * Read the file lines.
     *
     * @param br the bufferedreader to get information from.
     * @return the needed lines
     */
    public List<String> readLevel(BufferedReader br) {
        List<String> lines = new ArrayList<>();
        try {
            String line = br.readLine();
            while (line != null) {
                if (line.trim().equals("START_LEVEL")) {
                    break;
                }
                line = br.readLine();
            }
            line = br.readLine();
            while (line != null) {
                line = line.trim();

                if (line.equals("END_LEVEL")) {
                    break;
                }

                if (!line.isEmpty()) {
                    lines.add(line);
                }
                line = br.readLine();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
        return lines;
    }

    /**
     * create levelInformation from the line's file.
     *
     * @param lines - the lines from the file
     * @return new levelInformation
     */
    public LevelInformation parasLevel(List<String> lines) {
        List<String> blocksLines = new ArrayList<>();
        Map<String, String> level = parasLines(lines, blocksLines);
        String levelName = level.get("level_name").trim();
        Sprite background = parasBackground(level.get("background").trim());
        List<Velocity> velocities = parasVelocity(level.get("ball_velocities").trim());
        int paddleSpeed = Integer.parseInt(level.get("paddle_speed").trim());
        int paddleWidth = Integer.parseInt(level.get("paddle_width").trim());
        List<Block> blocks = parseBlocks(blocksLines, level);

        return new LevelInfoGeneric(levelName, background, velocities, paddleSpeed, paddleWidth, blocks);
    }

    /**
     * get the lines of the blocks.
     *
     * @param lines       list of the lines.
     * @param blocksLines The list to which the lines are placed
     * @return new map of block definitions
     */
    public Map<String, String> parasLines(List<String> lines, List<String> blocksLines) {
        Map<String, String> linesMap = new TreeMap<>();
        boolean blockLine = false;
        for (String line : lines) {
            if (!line.equals("END_BLOCKS")) {
                if (blockLine) {
                    blocksLines.add(line);
                    continue;
                }

                if (line.equals("START_BLOCKS")) {
                    blockLine = true;
                    continue;
                }
            }
            if (!line.equals("END_BLOCKS")) {
                String[] splitLine = line.split(":");
                linesMap.put(splitLine[0].trim(), splitLine[1].trim());
            }
        }
        return linesMap;
    }

    /**
     * Create the background of the level.
     *
     * @param backgroundInfo background definitions
     * @return the background
     */
    public Sprite parasBackground(String backgroundInfo) {

        if (backgroundInfo.startsWith("image")) {
            String image = backgroundInfo.replace("image(", "").replace(")", "");
            try {
                return new Background(ImageIO.read(ClassLoader.getSystemClassLoader().getResourceAsStream(image)));
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.exit(0);
            }
        }
        String color = backgroundInfo.trim();
        return new Background(new ColorsParser().colorFromString(color));
    }

    /**
     * Create list of ball's velocity.
     *
     * @param velocities in string
     * @return list of velocity
     */
    public List<Velocity> parasVelocity(String velocities) {
        List<Velocity> velocity = new ArrayList<>();
        String[] velocityTemp = velocities.split(" ");
        for (String v : velocityTemp) {
            String[] oneVelocity = v.split(",");
            velocity.add(Velocity.fromAngleAndSpeed(Integer.parseInt(oneVelocity[0])
                    , Integer.parseInt(oneVelocity[1])));

        }
        return velocity;
    }

    /**
     * Create list of blocks.
     *
     * @param blockLines the lines from the file
     * @param level      level definitions
     * @return list of blocks
     */
    public List<Block> parseBlocks(List<String> blockLines, Map<String, String> level) {
        List<Block> blocks = new ArrayList<>();
        int xpos = Integer.parseInt(level.get("blocks_start_x").trim());
        int ypos = Integer.parseInt(level.get("blocks_start_y").trim());
        int rowHeight = Integer.parseInt(level.get("row_height").trim());
        BlocksFromSymbolsFactory blockFactory = null;
        try {
            URL url = ClassLoader.getSystemClassLoader().getResource(level.get("block_definitions").trim());
            if (url == null) {
                System.out.println(
                        "Could not find block definition file '" + level.get("block_definitions").trim() + "'");
                System.exit(0);
            }
            blockFactory = BlocksDefinitionReader.fromReader(new InputStreamReader(
                    ClassLoader.getSystemClassLoader().getResourceAsStream(level.get("block_definitions").trim())));

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
        int currentX = xpos;
        int currentY = ypos;

        for (String line : blockLines) {
            for (int i = 0; i < line.length(); i++) {
                String s = Character.toString(line.charAt(i));
                if (blockFactory.isSpaceSymbol(s)) {
                    currentX += blockFactory.getSpaceWidth(s);
                } else if (blockFactory.isBlockSymbol(s)) {
                    Block block = blockFactory.getBlock(s, currentX, currentY);
                    blocks.add(block);
                    currentX += block.getWidth();
                } else {
                    System.out.println("Error - found undefined character.");
                    System.exit(0);
                }
            }
            currentX = xpos;
            currentY += rowHeight;
        }
        return blocks;
    }
}