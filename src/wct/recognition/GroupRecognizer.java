package wct.recognition;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Robot;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

/**
 *
 * @author xuanlinhha
 */
public class GroupRecognizer {

    public static List<Color> getActiveGroupColors(int fromX, int toX, int fromY, int toY) throws AWTException {
        Pair<Integer, Integer> range = getRange(fromX, fromY, toY);
        Robot robot = new Robot();
        Integer middle = (range.getLeft() + range.getRight()) / 2;
        List<Color> colors = new ArrayList<Color>();
        for (int i = fromX; i <= toX; i++) {
            colors.add(robot.getPixelColor(i, middle));
        }
        return colors;
    }

    public static Pair<Integer, Integer> getRange(int x, int fromY, int toY) throws AWTException {
        Robot robot = new Robot();
        Color beginColor = robot.getPixelColor(x, fromY);
        Color diffColor = null;
        int from = 0, to = 0;
        for (int i = fromY + 1; i <= toY; i++) {
            Color tmp = robot.getPixelColor(x, i);
            if (!isSameColor(beginColor, tmp)) {
                diffColor = tmp;
                from = i;
                break;
            }
        }
        for (int i = from; i <= toY; i++) {
            Color tmp = robot.getPixelColor(x, i);
            if (!isSameColor(diffColor, tmp)) {
                to = i - 1;
                break;
            }
        }
        return new MutablePair<>(from, to);
    }

    public static boolean isSameGroup(List<Color> cs1, List<Color> cs2) {
        if (cs1 == null && cs2 == null) {
            return true;
        } else if ((cs1 == null && cs2 != null) || (cs1 != null && cs2 == null)) {
            return false;
        }
        for (int i = 0; i < cs1.size(); i++) {
            if (!isSameColor(cs1.get(i), cs2.get(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isRecognizedGroup(List<Color> colors, List<List<Color>> groupsColors) {
        for (List<Color> gc : groupsColors) {
            if (GroupRecognizer.isSameGroup(colors, gc)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isSameColor(Color c1, Color c2) {
        return c1.getRed() == c2.getRed() && c1.getGreen() == c2.getGreen() && c1.getBlue() == c2.getBlue();
    }
}
