package wct.background;

import java.awt.Robot;
import javax.swing.JButton;
import javax.swing.SwingWorker;
import wct.invcopy.InvCopier;
import wct.mk.Keyboard;
import wct.mk.Mouse;
import wct.mk.Position;

/**
 *
 * @author xuanlinhha
 */
public class FileSender extends SwingWorker<Void, Void> {

    private static final Long SWITCH_TIME = 2000L;

    // gui
    private JButton startJButton;
    private JButton stopJButton;

    // data
    private int noOfGroups;
    private Position taskbarPosition;
    private Position scrollPosition;
    private long scrollTime;
    private Position lastHistoryPosition;
    private InvCopier invCopier;
    private long sendingTime;

    @Override
    protected Void doInBackground() throws Exception {
        startJButton.setEnabled(false);
        stopJButton.setEnabled(true);
        Robot r;
        r = new Robot();
        // click to WeChat app
        Mouse.getInstance().click(r, taskbarPosition);
        Thread.sleep(SWITCH_TIME);

        // set starting point to copy
        invCopier.setNextGroup(0);

        // run
        for (int i = 0; i < noOfGroups; i++) {
            // scroll down to the last history
            Mouse.getInstance().press(r, scrollPosition, scrollTime);

            // click last group
            Mouse.getInstance().click(r, lastHistoryPosition);

            // copy file
            invCopier.copy();

            //paste
            Keyboard.getInstance().paste(r);

            Thread.sleep(sendingTime);
            if (isCancelled()) {
                break;
            }
        }
        startJButton.setEnabled(true);
        stopJButton.setEnabled(false);

        return null;
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

    public InvCopier getInvCopier() {
        return invCopier;
    }

    public void setInvCopier(InvCopier invCopier) {
        this.invCopier = invCopier;
    }

    public long getSendingTime() {
        return sendingTime;
    }

    public void setSendingTime(long sendingTime) {
        this.sendingTime = sendingTime;
    }

}
