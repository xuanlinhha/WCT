package wct.main;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import wct.mk.Mouse;
import wct.mk.Position;
import wct.mk.Screen;

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

        Mouse.getInstance().click(new Position(576, 728));
//        Mouse.getInstance().click(r, new Position(467, 616));
        Thread.sleep(1000);

        Screen sc = Screen.getInstance();
        sc.initPositions(new Position(84, 643), new Position(126, 684));
//        sc.initPositions(new Position(75, 553), new Position(110, 588));

        for (Position p : sc.getPositions()) {
            Mouse.getInstance().click(p);
            Thread.sleep(2000);
        }

//
//        Set<String> colors = new HashSet<String>();
//        int limit = 15;
//        for (int i = 0; i < limit; i++) {
//            Mouse.getInstance().press(r, new Position(307, 602), 3000L);
//            String colorData = sc.getColorData(r);
//            if (!colors.contains(colorData)) {
//                System.out.println(colorData);
//                copyTextToClipboard(":)");
//                Mouse.getInstance().click(r, new Position(143, 569));
//                Keyboard.getInstance().paste(r);
//                Thread.sleep(1000L);
//                colors.add(colorData);
//            }
//        }
    }

    private static void copyTextToClipboard(String s) {
        StringSelection ss = new StringSelection(s);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, ss);
    }
}
