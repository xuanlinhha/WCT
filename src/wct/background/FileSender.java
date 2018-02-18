package wct.background;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.SwingWorker;
import org.apache.commons.lang3.RandomStringUtils;
import wct.fileprocessing.FileProcessor;
import wct.mk.Keyboard;
import wct.mk.Mouse;
import wct.mk.Position;
import wct.mk.Screen;

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
    private String inputFolder;
    private int noOfGroups;
    private long sendingTime;
    private String option;

    private Position taskbarPosition;
    private Position scrollPosition;
    private long scrollTime;
    private Position lastHistoryPosition;
    private String alternativeMsg;
    private List<Position> imagePositions;

    Set<String> sentGroups;

    public FileSender() {
        sentGroups = new HashSet<String>();
    }

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
            if (!option.equals("Continue")) {
                sentGroups.clear();
            }
            Robot r;
            r = new Robot();
            Screen sc = new Screen();
            sc.initPositions(imagePositions.get(0), imagePositions.get(1));

            // click to WeChat app
            Mouse.getInstance().click(r, taskbarPosition);
            Thread.sleep(SWITCH_TIME);

            // run
            for (int i = 0; i < noOfGroups; i++) {
//                FileProcessor.clearClipboard();

                // scroll down to the last history
                Mouse.getInstance().press(r, scrollPosition, scrollTime);

                // get color to check
                String color = sc.getColorData(r);

                // if new group
                if (!sentGroups.contains(color)) {
                    // change hash code and copy to clipboard
                    String randString = RandomStringUtils.random(RAMDOM_LENGTH) + i;
                    FileProcessor.changeHashcode(inputFolder, randString);
                    FileProcessor.copyToClipboard(inputFolder);
                    sentGroups.add(color);

                } else {
                    StringSelection stringSelection = new StringSelection(alternativeMsg);
                    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(
                            stringSelection, null);
                }

                // click last group
                Mouse.getInstance().click(r, lastHistoryPosition);

                Thread.sleep(1000);

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

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getAlternativeMsg() {
        return alternativeMsg;
    }

    public void setAlternativeMsg(String alternativeMsg) {
        this.alternativeMsg = alternativeMsg;
    }

    public Set<String> getSentGroups() {
        return sentGroups;
    }

    public void setSentGroups(Set<String> sentGroups) {
        this.sentGroups = sentGroups;
    }

    public List<Position> getImagePositions() {
        return imagePositions;
    }

    public void setImagePositions(List<Position> imagePositions) {
        this.imagePositions = imagePositions;
    }

}
