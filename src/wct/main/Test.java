package wct.main;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import javax.imageio.ImageIO;
import org.apache.commons.codec.digest.DigestUtils;
import wct.resourses.Position;

/**
 *
 * @author workshop
 */
public class Test {

    private static long WAIT_FOR_PASTING = 1000L;

    public static void main(String[] args) throws Exception {
        Robot r = new Robot();
        Position p1 = new Position(1, 1);
        Position p2 = new Position(10, 10);
        int counter = 0;
        while (counter < 10) {
//            Mouse.getInstance().press(new Position(1, 1), 2000L);
//            Mouse.getInstance().click(new Position(1, 1));
//            Thread.sleep(WAIT_FOR_PASTING);
            BufferedImage image = r.createScreenCapture(new Rectangle(p1.getX(), p2.getY(), Math.abs(p2.getX() - p1.getX()), Math.abs(p2.getY() - p1.getY())));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", baos);
            byte[] bytes = baos.toByteArray();
            DigestUtils.sha256Hex(bytes);
            System.out.println(DigestUtils.sha256Hex(bytes));
//            SystemClipboard.getInstance().copyString("-");
//            Keyboard.getInstance().paste();
            p2.setX(p2.getX() + 1);
            p2.setY(p2.getY() + 1);
            counter++;
//            if (counter < 10 - 1) {
//                Thread.sleep(3000L);
//            }
        }
        counter = 0;
        while (counter < 10) {
//            Mouse.getInstance().press(new Position(1, 1), 2000L);
//            Mouse.getInstance().click(new Position(1, 1));
//            Thread.sleep(WAIT_FOR_PASTING);
            BufferedImage image = r.createScreenCapture(new Rectangle(p1.getX(), p2.getY(), Math.abs(p2.getX() - p1.getX()), Math.abs(p2.getY() - p1.getY())));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", baos);
            byte[] bytes = baos.toByteArray();
            DigestUtils.sha256Hex(bytes);
            System.out.println(DigestUtils.sha256Hex(bytes));
//            SystemClipboard.getInstance().copyString("-");
//            Keyboard.getInstance().paste();
            p2.setX(p2.getX() - 1);
            p2.setY(p2.getY() - 1);
            counter++;
//            if (counter < 10 - 1) {
//                Thread.sleep(3000L);
//            }
        }
    }

}
