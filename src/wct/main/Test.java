package wct.main;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.RandomStringUtils;
import wct.mk.Keyboard;
import wct.mk.Mouse;
import wct.mk.Position;
import wct.mk.Screen;
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

        Mouse.getInstance().click(r, new Position(576, 728));
        Thread.sleep(1000);

        Screen sc = new Screen();
        sc.initPositions(new Position(84, 643), new Position(126, 684));
//        for (Position p : sc.getPositions()) {
//            Mouse.getInstance().click(r,p);
//            Thread.sleep(2000);
//        }

//
        Set<String> colors = new HashSet<String>();
        int limit = 15;
        for (int i = 0; i < limit; i++) {
            Mouse.getInstance().press(r, new Position(323, 695), 3000L);
            String colorData = sc.getColorData(r);
            if (!colors.contains(colorData)) {
                System.out.println(colorData);
                copyTextToClipboard(":-)");
                Keyboard.getInstance().paste(r);
                Thread.sleep(1000L);
            }
        }
    }

    private static void copyTextToClipboard(String s) {
        StringSelection ss = new StringSelection(s);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, ss);
    }
}
