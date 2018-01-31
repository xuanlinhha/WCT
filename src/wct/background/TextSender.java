package wct.background;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import javax.swing.JButton;
import javax.swing.SwingWorker;
import wct.mk.Keyboard;
import wct.mk.Mouse;
import wct.mk.Position;

/**
 *
 * @author workshop
 */
public class TextSender extends SwingWorker<Void, Void> {

    // gui
    private JButton startJButton;
    private JButton stopJButton;

    // data
    private String text;
    private int noOfGroups;
    private Position taskbarPosition;
    private Position scrollPosition;
    private long scrollTime;
    private Position lastHistoryPosition;
    private long sendingTime;

    @Override
    protected Void doInBackground() throws Exception {
        startJButton.setEnabled(false);
        stopJButton.setEnabled(true);
        Robot r;
        r = new Robot();
        // click to WeChat app
        Mouse.getInstance().click(r, taskbarPosition);

        // copy text
        copyText();

        // select group and paste
        for (int i = 0; i < noOfGroups; i++) {
            // down
            Mouse.getInstance().press(r, taskbarPosition, scrollTime);

            //paste
            Keyboard.getInstance().paste(r);

            // wait
            Thread.sleep(sendingTime);
            if (isCancelled()) {
                break;
            }
        }
        startJButton.setEnabled(true);
        stopJButton.setEnabled(false);

        return null;
    }

    private void copyText() {
        StringSelection ss = new StringSelection(text);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, ss);
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getNoOfGroups() {
        return noOfGroups;
    }

    public void setNoOfGroups(int noOfGroups) {
        this.noOfGroups = noOfGroups;
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

    public long getSendingTime() {
        return sendingTime;
    }

    public void setSendingTime(long sendingTime) {
        this.sendingTime = sendingTime;
    }

}
