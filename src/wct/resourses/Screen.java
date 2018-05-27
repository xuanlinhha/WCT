package wct.resourses;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author xuanlinhha
 */
public class Screen {

    private int counter = 0;
    private List<Coordinate> staticCorners;
    private List<Coordinate> dynPositions;
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

    public String getDynColorData() {
        int shift = dynPositions.get(3).getY() - dynPositions.get(2).getY();
        int edge = dynPositions.get(4).getY() - dynPositions.get(3).getY();
        int x0 = dynPositions.get(3).getX();
        int x1 = x0 + edge;
        try {
            int grayY = findGrayY();
//            System.out.println("grayY="+grayY);
            int y0 = grayY + shift;
            int y1 = y0 + edge;
            Coordinate c1 = new Coordinate(x0, y0);
            Coordinate c2 = new Coordinate(x1, y1);
            return getColorData(c1, c2);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }

    private int findGrayY() throws AWTException {
        int x0 = dynPositions.get(0).getX();
        int y0 = dynPositions.get(0).getY();
        int y1 = dynPositions.get(1).getY();
        Color c = r.getPixelColor(x0, y0);
        int grayY = y0;
        for (int i = dynPositions.get(0).getY() + 1; i <= y1; i++) {
            Color tmp = r.getPixelColor(x0, i);
//            System.out.println("[" + tmp.getRed() + "," + tmp.getGreen() + "," + tmp.getBlue() + "]");
            if (!isSame(c, tmp)) {
                grayY = i;
                break;
            }
        }
        return grayY;
    }

    private boolean isSame(Color c1, Color c2) {
        return (c1.getRed() == c2.getRed());
//        return c1.getRGB() == c2.getRGB();
    }

    public String getStaticColorData() {
        return getColorData(staticCorners.get(0), staticCorners.get(1));
    }

    private String getColorData(Coordinate p1, Coordinate p2) {
        try {
            BufferedImage image = r.createScreenCapture(new Rectangle(p1.getX(), p1.getY(), Math.abs(p2.getX() - p1.getX()), Math.abs(p2.getY() - p1.getY())));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", baos);
            //
            ImageIO.write(image, "jpg", new File("img" + counter + ".jpg"));
            counter++;

            byte[] bytes = baos.toByteArray();
            return DigestUtils.sha256Hex(bytes);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return "";
    }

    public List<Coordinate> getStaticCorners() {
        return staticCorners;
    }

    public void setStaticCorners(List<Coordinate> staticCorners) {
        this.staticCorners = staticCorners;
    }

    public List<Coordinate> getDynPositions() {
        return dynPositions;
    }

    public void setDynPositions(List<Coordinate> dynPositions) {
        this.dynPositions = dynPositions;
    }

    public Robot getR() {
        return r;
    }

    public void setR(Robot r) {
        this.r = r;
    }

}
