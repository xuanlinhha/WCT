package wct.mk;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import org.apache.commons.lang3.SystemUtils;

/**
 *
 * @author xuanlinhha
 */
public class Keyboard {

    private static Keyboard instance;

    private Keyboard() {
    }

    public static Keyboard getInstance() {
        if (instance == null) {
            instance = new Keyboard();
        }
        return instance;
    }

    public void copy(Robot r) {
        if (SystemUtils.IS_OS_MAC) {
            r.keyPress(KeyEvent.VK_META);
            r.keyPress(KeyEvent.VK_C);
            r.keyRelease(KeyEvent.VK_C);
            r.keyRelease(KeyEvent.VK_META);
        } else if (SystemUtils.IS_OS_WINDOWS) {
            r.keyPress(KeyEvent.VK_CONTROL);
            r.keyPress(KeyEvent.VK_C);
            r.keyRelease(KeyEvent.VK_C);
            r.keyRelease(KeyEvent.VK_CONTROL);
        }
    }

    public void paste(Robot r) {
        if (SystemUtils.IS_OS_MAC) { //mac
            r.keyPress(KeyEvent.VK_META);
            r.keyPress(KeyEvent.VK_V);
            r.keyRelease(KeyEvent.VK_V);
            r.keyRelease(KeyEvent.VK_META);
        } else if (SystemUtils.IS_OS_WINDOWS) { //win
            r.keyPress(KeyEvent.VK_CONTROL);
            r.keyPress(KeyEvent.VK_V);
            r.keyRelease(KeyEvent.VK_V);
            r.keyRelease(KeyEvent.VK_CONTROL);
            r.keyPress(KeyEvent.VK_ENTER);
            r.keyRelease(KeyEvent.VK_ENTER);
        }
    }

    public void down(Robot r, int no) {
        for (int i = 0; i < no; i++) {
            r.keyPress(KeyEvent.VK_DOWN);
            r.keyRelease(KeyEvent.VK_DOWN);
        }
    }

    public void select(Robot r, int no) {
        r.keyPress(KeyEvent.VK_SHIFT);
        for (int i = 0; i < no - 1; i++) {
            r.keyPress(KeyEvent.VK_DOWN);
            r.keyRelease(KeyEvent.VK_DOWN);
        }
        r.keyRelease(KeyEvent.VK_SHIFT);
    }
}
