package wct.background;

import java.awt.Robot;
import javax.swing.JButton;
import javax.swing.SwingWorker;
import org.apache.commons.lang3.RandomStringUtils;
import wct.fileprocessing.FileProcessor;
import wct.mk.Keyboard;
import wct.mk.Mouse;
import wct.mk.Position;

/**
 *
 * @author xuanlinhha
 */
public class FileSender extends SwingWorker<Void, Void> {

    private static final Long SWITCH_TIME = 1000L;
    private static final int RAMDOM_LENGTH = 20;

    // gui
    private JButton startJButton;
    private JButton stopJButton;

    // data
    private String kid3Path;
    private String inputFolder;
    private int noOfGroups;
    private long sendingTime;

    private Position taskbarPosition;
    private Position scrollPosition;
    private long scrollTime;
    private Position lastHistoryPosition;

    @Override
    protected Void doInBackground() {
        startJButton.setEnabled(false);
        stopJButton.setEnabled(true);
        bottomUpSend();
        startJButton.setEnabled(true);
        stopJButton.setEnabled(false);
        return null;
    }

    private void bottomUpSend() {
        try {
            Robot r;
            r = new Robot();
            // click to WeChat app
            Mouse.getInstance().click(r, taskbarPosition);
            Thread.sleep(SWITCH_TIME);

            // run
            for (int i = 0; i < noOfGroups; i++) {
                // change hash code and copy to clipboard
                String randString = RandomStringUtils.random(RAMDOM_LENGTH) + i;
                FileProcessor.changeHashcode(kid3Path, inputFolder, randString);
                FileProcessor.copy(inputFolder);
                
                // scroll down to the last history
                Mouse.getInstance().press(r, scrollPosition, scrollTime);
                
                // click last group
                Mouse.getInstance().click(r, lastHistoryPosition);

                //paste
                Keyboard.getInstance().paste(r);

                Thread.sleep(sendingTime);
                if (isCancelled()) {
                    break;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public JButton getStartJButton() {
        return startJButton;
    }

    public void setStartJButton(JButton startJButton) {
        this.startJButton = startJButton;
    }

    public JButton getStopJButton() {
        return stopJButton;
    }

    public void setStopJButton(JButton stopJButton) {
        this.stopJButton = stopJButton;
    }

    public String getKid3Path() {
        return kid3Path;
    }

    public void setKid3Path(String kid3Path) {
        this.kid3Path = kid3Path;
    }

    public String getInputFolder() {
        return inputFolder;
    }

    public void setInputFolder(String inputFolder) {
        this.inputFolder = inputFolder;
    }

    public int getNoOfGroups() {
        return noOfGroups;
    }

    public void setNoOfGroups(int noOfGroups) {
        this.noOfGroups = noOfGroups;
    }

    public long getSendingTime() {
        return sendingTime;
    }

    public void setSendingTime(long sendingTime) {
        this.sendingTime = sendingTime;
    }

    public Position getTaskbarPosition() {
        return taskbarPosition;
    }

    public void setTaskbarPosition(Position taskbarPosition) {
        this.taskbarPosition = taskbarPosition;
    }

    public Position getScrollPosition() {
        return scrollPosition;
    }

    public void setScrollPosition(Position scrollPosition) {
        this.scrollPosition = scrollPosition;
    }

    public long getScrollTime() {
        return scrollTime;
    }

    public void setScrollTime(long scrollTime) {
        this.scrollTime = scrollTime;
    }

    public Position getLastHistoryPosition() {
        return lastHistoryPosition;
    }

    public void setLastHistoryPosition(Position lastHistoryPosition) {
        this.lastHistoryPosition = lastHistoryPosition;
    }

}
