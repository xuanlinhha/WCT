package wct.mk;

import java.awt.Robot;
import java.awt.event.InputEvent;

/**
 *
 * @author xuanlinhha
 */
public class Mouse {

    private static Mouse instance;

    private Mouse() {
    }

    public static Mouse getInstance() {
        if (instance == null) {
            instance = new Mouse();
        }
        return instance;
    }

    public void click(Robot r, Position p) {
        r.mouseMove(p.getX(), p.getY());
        r.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        r.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }

}
