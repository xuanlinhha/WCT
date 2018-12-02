package wct.test;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import org.apache.commons.codec.digest.DigestUtils;
import wct.resourses.Coordinate;
import wct.resourses.Keyboard;
import wct.resourses.Screen;

/**
 *
 * @author threenine333
 */
public class DynCapture {

    public static void main(String[] args) throws AWTException {
        DynCapture dc = new DynCapture();
        dc.capture();
    }

    private void test1() throws AWTException {
        Robot r = new Robot();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Coordinate corner1 = new Coordinate(72, 544);
        Coordinate corner2 = new Coordinate(112, 582);
        int fixedX = corner1.getX();
        int len = corner2.getX() - corner1.getX();
        //
        int vertical = fixedX - len / 6;
        int i = 1;
        // find Y of gray region
        int begin = r.getPixelColor(vertical, i).getRGB();
        while (i < screenSize.height) {
            int tmp = r.getPixelColor(vertical, i).getRGB();
            if (tmp == begin) {
                i++;
                continue;
            } else {
                break;
            }
        }
        int dynY = i+len/3;
        
        // capture image
        int counter = 0;
        try {
            BufferedImage image = r.createScreenCapture(new Rectangle(fixedX, dynY, len, len));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", baos);
            //
            ImageIO.write(image, "jpg", new File("img" + counter + ".jpg"));
            counter++;

            byte[] bytes = baos.toByteArray();
            String hash = DigestUtils.sha256Hex(bytes);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void capture() {
        Coordinate c1 = new Coordinate(66, 48);
        Coordinate c2 = new Coordinate(66, 388);
        Coordinate c3 = new Coordinate(72, 125);
        Coordinate c4 = new Coordinate(72, 137);
        Coordinate c5 = new Coordinate(72, 176);
        List<Coordinate> dynCoors = new ArrayList<Coordinate>();
        dynCoors.add(c1);
        dynCoors.add(c2);
        dynCoors.add(c3);
        dynCoors.add(c4);
        dynCoors.add(c5);
        try {
            Screen.getInstance().setDynPositions(dynCoors);
            Thread.sleep(3000);
            for (int i = 0; i < 5; i++) {
                System.out.println(Screen.getInstance().getDynColorData());
                Keyboard.getInstance().down(1);
                Thread.sleep(2000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
