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

    public String getColorData() {
        try {
            BufferedImage image = r.createScreenCapture(new Rectangle(positions.get(0).getX(), positions.get(1).getY(), Math.abs(positions.get(1).getX() - positions.get(0).getX()), Math.abs(positions.get(1).getY() - positions.get(0).getY())));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", baos);
            byte[] bytes = baos.toByteArray();
            return DigestUtils.sha256Hex(bytes);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return "";
    }

    public List<Position> getPositions() {
        return positions;
    }

    public void setPositions(List<Position> positions) {
        this.positions = positions;
    }

}
