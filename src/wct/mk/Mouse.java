package wct.mk;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.io.IOException;
import wct.configuration.ConfigurationHandler;

/**
 *
 * @author xuanlinhha
 */
public class Mouse {

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

    public void click(Position p) throws InterruptedException, IOException {
        Long mouseMoveWating = ConfigurationHandler.getConfig().getMouseMoveWaiting();
        Long mPRWating = ConfigurationHandler.getConfig().getMousePRWaiting();
        r.mouseMove(p.getX(), p.getY());
        Thread.sleep(mouseMoveWating);
        r.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        Thread.sleep(mPRWating);
        r.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        Thread.sleep(mPRWating);
    }

    public void press(Position p, Long pressTime) throws InterruptedException, IOException {
        Long mouseMoveWating = ConfigurationHandler.getConfig().getMouseMoveWaiting();
        Long mPRWating = ConfigurationHandler.getConfig().getMousePRWaiting();
        r.mouseMove(p.getX(), p.getY());
        Thread.sleep(mouseMoveWating);
        r.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        Thread.sleep(pressTime);
        r.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        Thread.sleep(mPRWating);
    }
}
