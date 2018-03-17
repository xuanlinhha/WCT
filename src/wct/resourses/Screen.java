package wct.resourses;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author xuanlinhha
 */
public class Screen {

    private List<Coordinate> positions;
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

    public String getColorData() {
        try {
            Coordinate p1 = positions.get(0);
            Coordinate p2 = positions.get(1);
            BufferedImage image = r.createScreenCapture(new Rectangle(p1.getX(), p1.getY(), Math.abs(p2.getX() - p1.getX()), Math.abs(p2.getY() - p1.getY())));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", baos);
            byte[] bytes = baos.toByteArray();
            return DigestUtils.sha256Hex(bytes);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return "";
    }

    public List<Coordinate> getPositions() {
        return positions;
    }

    public void setPositions(List<Coordinate> positions) {
        this.positions = positions;
    }

}
