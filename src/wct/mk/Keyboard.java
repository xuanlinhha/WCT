package wct.mk;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import org.apache.commons.lang3.SystemUtils;
import wct.configuration.ConfigurationHandler;

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

    public void paste(Robot r) throws IOException, InterruptedException {
        Long prWatingTime = ConfigurationHandler.getConfig().getKbPRWaiting();
        if (SystemUtils.IS_OS_MAC) { //mac
            r.keyPress(KeyEvent.VK_META);
            Thread.sleep(prWatingTime);
            r.keyPress(KeyEvent.VK_V);
            Thread.sleep(prWatingTime);
            r.keyRelease(KeyEvent.VK_V);
            Thread.sleep(prWatingTime);
            r.keyRelease(KeyEvent.VK_META);
            Thread.sleep(prWatingTime);
            
            r.keyPress(KeyEvent.VK_ENTER);
            Thread.sleep(prWatingTime);
            r.keyRelease(KeyEvent.VK_ENTER);
            Thread.sleep(prWatingTime);
        } else if (SystemUtils.IS_OS_WINDOWS) { //win
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
    }

    public void down(Robot r, int no) throws IOException, InterruptedException {
        Long prWatingTime = ConfigurationHandler.getConfig().getKbPRWaiting();
        Long finishWatingTime = ConfigurationHandler.getConfig().getKbFinishWaiting();
        for (int i = 0; i < no; i++) {
            r.keyPress(KeyEvent.VK_DOWN);
            Thread.sleep(prWatingTime);
            r.keyRelease(KeyEvent.VK_DOWN);
            Thread.sleep(finishWatingTime);
        }
    }
    public void up(Robot r, int no) throws IOException, InterruptedException {
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
