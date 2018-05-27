package wct.test;

import java.awt.AWTException;
import java.util.ArrayList;
import java.util.List;
import wct.resourses.Coordinate;
import wct.resourses.Keyboard;
import wct.resourses.Screen;

/**
 *
 * @author threenine333
 */
public class DynCapture {

    Coordinate c1 = new Coordinate(66, 48);
    Coordinate c2 = new Coordinate(66, 388);
    Coordinate c3 = new Coordinate(72, 125);
    Coordinate c4 = new Coordinate(72, 137);
    Coordinate c5 = new Coordinate(72, 176);

    public static void main(String[] args) throws AWTException {
        DynCapture dc = new DynCapture();
        dc.capture();
    }

    private void capture() {
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
