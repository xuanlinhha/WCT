package wct.resourses;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.io.IOException;

/**
 *
 * @author xuanlinhha
 */
public class Mouse {

    private static final long MM_WAITING_TIME = 50;
    private static final long PR_WAITING_TIME = 50;
    private Robot r;
    private static Mouse instance;

    private Mouse() {
    }

    public static Mouse getInstance() throws AWTException {
        if (instance == null) {
            instance = new Mouse();
            instance.r = new Robot();
        }
        return instance;
    }

    public void click(Coordinate p) throws InterruptedException, IOException {
        r.mouseMove(p.getX(), p.getY());
        Thread.sleep(MM_WAITING_TIME);
        r.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        Thread.sleep(PR_WAITING_TIME);
        r.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        Thread.sleep(PR_WAITING_TIME);
    }

    public void press(Coordinate p, Long pressTime) throws InterruptedException, IOException {
        r.mouseMove(p.getX(), p.getY());
        Thread.sleep(MM_WAITING_TIME);
        r.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        Thread.sleep(pressTime);
        r.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        Thread.sleep(PR_WAITING_TIME);
    }
}
