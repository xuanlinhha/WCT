package wct.main;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Robot;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.RandomStringUtils;
import wct.mk.Keyboard;
import wct.mk.Mouse;
import wct.mk.Position;
import wct.recognition.GroupRecognizer;

/**
 *
 * @author workshop
 */
public class Test {

    private static String inputFolder = "C:\\Users\\workshop\\Documents\\WCT\\input";
    private static String outputFolder = "C:\\Users\\workshop\\Documents\\WCT\\output";
    private static int RAMDOM_LENGTH = 50;

    public static void main(String[] args) throws Exception {
        Robot r = new Robot();

        Mouse.getInstance().click(r, new Position(892, 731));
        Thread.sleep(1000);
//        GroupRecognizer.getActiveGroupColors(77, 131, 67, 705);
        List<List<Color>> groupsColors = new ArrayList<List<Color>>();

        for (int i = 0; i < 10; i++) {
            List<Color> newGroupColors = getNewGroupColors(77, 131, 67, 705, groupsColors);
            if (newGroupColors == null) {
                break;
            }
        }
//        for (int i = 0; i < 10; i++) {
//            List<Color> activeGroupColors = GroupRecognizer.getActiveGroupColors(77, 131, 67, 705);
//            if (!GroupRecognizer.isRecognizedGroup(activeGroupColors, groupsColors)) {
//                groupsColors.add(activeGroupColors);
//            }
//            Keyboard.getInstance().down(r, 1);
//        }
        // print
        for (List<Color> gc : groupsColors) {
            for (Color c : gc) {
                System.out.printf("(%d,%d,%d)", c.getRed(), c.getGreen(), c.getBlue());
            }
            System.out.println("\n-----------");
        }
    }

    //77 67
    //131 705
    private static List<Color> getNewGroupColors(int fromX, int toX, int fromY, int toY, List<List<Color>> recognizedColors) throws AWTException {
//        List<List<Color>> recognizedColors = new ArrayList<List<Color>>();
        List<Color> preColors = null;
        List<Color> activeGroupColors = null;
        try {
            Robot r = new Robot();
            while (true) {
                activeGroupColors = GroupRecognizer.getActiveGroupColors(fromX, toX, fromY, toY);
                // if not recognized
                if (!GroupRecognizer.isRecognizedGroup(activeGroupColors, recognizedColors)) {
                    recognizedColors.add(activeGroupColors);
                    System.out.println("new group added");
                    return activeGroupColors;
                } else {
                    // if active group does not change then return null
                    if (GroupRecognizer.isSameGroup(preColors, activeGroupColors)) {
                        System.out.println("same as prev");
                        return null;
                    } else {
                        preColors = activeGroupColors;
                        System.out.println("not same as prev");
                        Keyboard.getInstance().down(r, 1);
                    }
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        return activeGroupColors;
    }

//    private boo()
    private static void f() throws Exception {
        File folder = new File(inputFolder);
        File[] files = folder.listFiles();
        for (int i = 0; i < 3; i++) {
            String randString = RandomStringUtils.random(RAMDOM_LENGTH);
            for (File f : files) {
                if (f.isFile() && f.getName().endsWith(".mp3")) {
                } else if (f.isFile() && (f.getName().endsWith(".mp4"))) {
                }
            }
            // print md5
            for (File f : files) {
                if (f.isFile() && (f.getName().endsWith(".mp3") || f.getName().endsWith(".mp4"))) {
                    FileInputStream fis = new FileInputStream(f);
                    System.out.println(org.apache.commons.codec.digest.DigestUtils.md5Hex(fis));
                    fis.close();
                }
            }
            System.out.println("---");
        }
    }

}
