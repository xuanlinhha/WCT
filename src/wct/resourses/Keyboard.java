package wct.resourses;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;

/**
 *
 * @author xuanlinhha
 */
public class Keyboard {

    private static final long PR_WAITING_TIME = 50;
    private static final long FINISH_WAITING_TIME = 50;
    private Robot r;
    private static Keyboard instance;

    private Keyboard() {
    }

    public static Keyboard getInstance() throws AWTException {
        if (instance == null) {
            instance = new Keyboard();
            instance.r = new Robot();
        }
        return instance;
    }

    public void pasteWithoutEnter() throws IOException, InterruptedException {
        r.keyPress(KeyEvent.VK_CONTROL);
        Thread.sleep(PR_WAITING_TIME);
        r.keyPress(KeyEvent.VK_V);
        Thread.sleep(PR_WAITING_TIME);
        r.keyRelease(KeyEvent.VK_V);
        Thread.sleep(PR_WAITING_TIME);
        r.keyRelease(KeyEvent.VK_CONTROL);
        Thread.sleep(PR_WAITING_TIME);
    }

    public void pasteWithEnter() throws IOException, InterruptedException {
        r.keyPress(KeyEvent.VK_CONTROL);
        Thread.sleep(PR_WAITING_TIME);
        r.keyPress(KeyEvent.VK_V);
        Thread.sleep(PR_WAITING_TIME);
        r.keyRelease(KeyEvent.VK_V);
        Thread.sleep(PR_WAITING_TIME);
        r.keyRelease(KeyEvent.VK_CONTROL);
        Thread.sleep(PR_WAITING_TIME);
        r.keyPress(KeyEvent.VK_ENTER);
        Thread.sleep(PR_WAITING_TIME);
        r.keyRelease(KeyEvent.VK_ENTER);
        Thread.sleep(PR_WAITING_TIME);
    }

    public void down(int no) throws IOException, InterruptedException {
        for (int i = 0; i < no; i++) {
            r.keyPress(KeyEvent.VK_DOWN);
            Thread.sleep(PR_WAITING_TIME);
            r.keyRelease(KeyEvent.VK_DOWN);
            Thread.sleep(FINISH_WAITING_TIME);
        }
    }

    public void up(int no) throws IOException, InterruptedException {
        for (int i = 0; i < no; i++) {
            r.keyPress(KeyEvent.VK_UP);
            Thread.sleep(PR_WAITING_TIME);
            r.keyRelease(KeyEvent.VK_UP);
            Thread.sleep(FINISH_WAITING_TIME);
        }
    }
}
