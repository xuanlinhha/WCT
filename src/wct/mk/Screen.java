package wct.mk;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Robot;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author xuanlinhha
 */
public class Screen {

    private List<Position> positions;
    private Robot r;
    private static Screen instance;

    private Screen() {
    }

    public static Screen getInstance() throws AWTException {
        if (instance == null) {
            instance = new Screen();
            instance.r = new Robot();
        }
        return instance;
    }

    public void initPositions(Position p1, Position p2) {
        int width = Math.abs(p2.getX() - p1.getX()) / 3;
        int haft = width / 2;
        int firstX = p1.getX() + haft;
        int firstY = p1.getY() + haft;
        List<Position> positions = new ArrayList<Position>();

        for (int i = 0; i < 3; i++) {
            int x = firstX + i * width;
            for (int j = 0; j < 3; j++) {
                int y = firstY + j * width;
                positions.add(new Position(x, y));
            }
        }
        positions.remove(6);
        this.positions = positions;
    }

    public String getColorData() {
        StringBuilder sb = new StringBuilder();
        for (Position p : positions) {
            Color c = r.getPixelColor(p.getX(), p.getY());
            sb.append(new Integer(c.getRed()));
            sb.append(new Integer(c.getGreen()));
            sb.append(new Integer(c.getBlue()));
        }
        return sb.toString();
    }

    public List<Position> getPositions() {
        return positions;
    }

    public void setPositions(List<Position> positions) {
        this.positions = positions;
    }

}
