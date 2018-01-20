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

    // gui
    private JButton startJButton;
    private JButton stopJButton;

    // data
    private int noOfGroups;
    private InvCopier invCopier;
    private long waitingTime;
    private int skip;
    private Position wcPosition;

    @Override
    protected Void doInBackground() throws Exception {
        startJButton.setEnabled(false);
        stopJButton.setEnabled(true);
        Robot r;
        r = new Robot();
        // click to WeChat app
        Mouse.getInstance().click(r, wcPosition);

        // set starting point to copy
        invCopier.setNext(skip);

        // run
        for (int i = 0; i < noOfGroups; i++) {
            // copy file
            invCopier.copy();
            // down
            Keyboard.getInstance().down(r, skip + i);
            //paste
            Keyboard.getInstance().paste(r);
            Thread.sleep(waitingTime);
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

    public InvCopier getInvCopier() {
        return invCopier;
    }

    public void setInvCopier(InvCopier invCopier) {
        this.invCopier = invCopier;
    }

    public long getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(long waitingTime) {
        this.waitingTime = waitingTime;
    }

    public int getSkip() {
        return skip;
    }

    public void setSkip(int skip) {
        this.skip = skip;
    }

    public Position getWcPosition() {
        return wcPosition;
    }

    public void setWcPosition(Position wcPosition) {
        this.wcPosition = wcPosition;
    }

}
