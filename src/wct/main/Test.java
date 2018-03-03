package wct.main;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import javax.imageio.ImageIO;
import org.apache.commons.codec.digest.DigestUtils;
import wct.resourses.Keyboard;
import wct.resourses.Mouse;
import wct.resourses.Position;
import wct.resourses.SystemClipboard;

/**
 *
 * @author workshop
 */
public class Test {

    private static long WAIT_FOR_PASTING = 1000L;

    public static void main(String[] args) throws Exception {
        Robot r = new Robot();
        Position p0 = new Position(170, 750);
        Position p1 = new Position(74, 688);
        Position p2 = new Position(112, 726);
        int counter = 0;
        Mouse.getInstance().click(p0);
        Thread.sleep(3000L);
        /*
         while (counter < 10) {
         Mouse.getInstance().press(new Position(307, 734), 2000L);
         Mouse.getInstance().click(p1);
         Thread.sleep(WAIT_FOR_PASTING);
         BufferedImage image = r.createScreenCapture(new Rectangle(p1.getX(), p2.getY(), Math.abs(p2.getX() - p1.getX()), Math.abs(p2.getY() - p1.getY())));
         ByteArrayOutputStream baos = new ByteArrayOutputStream();
         ImageIO.write(image, "jpg", baos);
         byte[] bytes = baos.toByteArray();
         DigestUtils.sha256Hex(bytes);
         System.out.println(DigestUtils.sha256Hex(bytes));
         SystemClipboard.getInstance().copyString(".");
         Keyboard.getInstance().paste();

         counter++;
         if (counter < 10 - 1) {
         Thread.sleep(3000L);
         }
         }
         */
        BufferedImage image = r.createScreenCapture(new Rectangle(p1.getX(), p1.getY(), Math.abs(p2.getX() - p1.getX()), Math.abs(p2.getY() - p1.getY())));
//        BufferedImage image = r.createScreenCapture(new Rectangle(new Point(p1.getX(), p1.getY()), new Dimension(Math.abs(p2.getX() - p1.getX()), Math.abs(p2.getY() - p1.getY()))));
//        BufferedImage image = new Robot().createScreenCapture(
//           new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()) );
        ImageIO.write(image, "jpg", new File("image.jpg"));
    }

}
