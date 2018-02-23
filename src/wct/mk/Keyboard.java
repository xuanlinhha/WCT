package wct.mk;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import wct.configuration.ConfigurationHandler;

/**
 *
 * @author xuanlinhha
 */
public class Keyboard {

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

    public void paste() throws IOException, InterruptedException {
        Long prWatingTime = ConfigurationHandler.getConfig().getKbPRWaiting();
        r.keyPress(KeyEvent.VK_CONTROL);
        Thread.sleep(prWatingTime);
        r.keyPress(KeyEvent.VK_V);
        Thread.sleep(prWatingTime);
        r.keyRelease(KeyEvent.VK_V);
        Thread.sleep(prWatingTime);
        r.keyRelease(KeyEvent.VK_CONTROL);
        Thread.sleep(prWatingTime);
        r.keyPress(KeyEvent.VK_ENTER);
        Thread.sleep(prWatingTime);
        r.keyRelease(KeyEvent.VK_ENTER);
        Thread.sleep(prWatingTime);
    }

    public void down(int no) throws IOException, InterruptedException {
        Long prWatingTime = ConfigurationHandler.getConfig().getKbPRWaiting();
        Long finishWatingTime = ConfigurationHandler.getConfig().getKbFinishWaiting();
        for (int i = 0; i < no; i++) {
            r.keyPress(KeyEvent.VK_DOWN);
            Thread.sleep(prWatingTime);
            r.keyRelease(KeyEvent.VK_DOWN);
            Thread.sleep(finishWatingTime);
        }
    }

    public void up(int no) throws IOException, InterruptedException {
        Long prWatingTime = ConfigurationHandler.getConfig().getKbPRWaiting();
        Long finishWatingTime = ConfigurationHandler.getConfig().getKbFinishWaiting();
        for (int i = 0; i < no; i++) {
            r.keyPress(KeyEvent.VK_UP);
            Thread.sleep(prWatingTime);
            r.keyRelease(KeyEvent.VK_UP);
            Thread.sleep(finishWatingTime);
        }
    }
}
