package game.files;

import java.awt.Color;

/**
 * @author yuval Cohen
 * This class holds the ability to return color from a string.
 */
public class ColorsParser {

    /**
     * parse color definition and return the specified color.
     * If the color does not exist will return black color.
     *
     * @param s string of color
     * @return new color
     */
    public java.awt.Color colorFromString(String s) {
        if (s == null) {
            return null;
        }

        Color color = null;
        String tmpColor;

        if (s.startsWith("color(RGB")) {
            tmpColor = s.replace("color(RGB(", "").replace("))", "").trim();
            String[] rgbColor = tmpColor.split(",");
            color = new Color(Integer.parseInt(rgbColor[0]), Integer.parseInt(rgbColor[1])
                    , Integer.parseInt(rgbColor[2]));
        } else if (s.startsWith("color")) {
            tmpColor = s.replace("color(", "").replace(")", "");
            switch (tmpColor) {
                case "blue":
                    color = Color.blue;
                    break;
                case "yellow":
                    color = Color.yellow;
                    break;
                case "red":
                    color = Color.red;
                    break;
                case "green":
                    color = Color.green;
                    break;
                case "pink":
                    color = Color.pink;
                    break;
                case "white":
                    color = Color.white;
                    break;
                case "cyan":
                    color = Color.cyan;
                    break;
                case "orange":
                    color = Color.orange;
                    break;
                case "lightGray":
                    color = Color.lightGray;
                    break;
                case "gray":
                    color = Color.gray;
                    break;
                case "black":
                default:
                    color = Color.black;
                    break;
            }
        } else {
            color = Color.black;
        }
        return color;
    }
}