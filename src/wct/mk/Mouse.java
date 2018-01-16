package wct.mk;

import java.awt.Robot;
import java.awt.event.InputEvent;

/**
 *
 * @author xuanlinhha
 */
public class Mouse {

    private static long mouseMoveWating = 20;
    private static long mPRWating = 20;
    private static Mouse instance;

    private Mouse() {
    }

    public static Mouse getInstance() {
        if (instance == null) {
            instance = new Mouse();
        }
        return instance;
    }

    public void click(Robot r, Position p) throws InterruptedException {
        r.mouseMove(p.getX(), p.getY());
        Thread.sleep(mouseMoveWating);
        r.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        Thread.sleep(mPRWating);
        r.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        Thread.sleep(mPRWating);
    }
}
