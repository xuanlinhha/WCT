package wct.background;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.util.List;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.SwingWorker;
import wct.mk.Keyboard;
import wct.mk.Mouse;
import wct.mk.Position;
import wct.mk.Screen;

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
    private long sendingTime;
    private String alternativeMsg;
    private List<Position> imagePositions;

    private Set<String> sentGroups;

    @Override
    protected Void doInBackground() {
        startJButton.setEnabled(false);
        stopJButton.setEnabled(true);
        try {
            Robot r;
            r = new Robot();
            Screen sc = new Screen();
            sc.initPositions(imagePositions.get(0), imagePositions.get(1));

            // click to WeChat app
            Mouse.getInstance().click(r, taskbarPosition);

            int counter = 0;
            while (counter < noOfGroups) {
                // clear
//                FileProcessor.clearClipboard();

                // down
                Mouse.getInstance().press(r, scrollPosition, scrollTime);

                // get color to check
                String color = sc.getColorData(r);

                // if group has been sent
                boolean isNew = false;
                if (sentGroups.contains(color)) {
                    StringSelection stringSelection = new StringSelection(alternativeMsg);
                    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(
                            stringSelection, null);
                } else {
                    // copy text
                    copyText();
                    sentGroups.add(color);
                    counter++;
                    isNew = true;
                }

                // click last group
                Mouse.getInstance().click(r, imagePositions.get(0));

                //paste
                Keyboard.getInstance().paste(r);

                // wait
                if (isNew) {
                    Thread.sleep(sendingTime);
                }
                if (isCancelled()) {
                    break;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
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

    public long getSendingTime() {
        return sendingTime;
    }

    public void setSendingTime(long sendingTime) {
        this.sendingTime = sendingTime;
    }

    public String getAlternativeMsg() {
        return alternativeMsg;
    }

    public void setAlternativeMsg(String alternativeMsg) {
        this.alternativeMsg = alternativeMsg;
    }

    public List<Position> getImagePositions() {
        return imagePositions;
    }

    public void setImagePositions(List<Position> imagePositions) {
        this.imagePositions = imagePositions;
    }

    public Set<String> getSentGroups() {
        return sentGroups;
    }

    public void setSentGroups(Set<String> sentGroups) {
        this.sentGroups = sentGroups;
    }

}
