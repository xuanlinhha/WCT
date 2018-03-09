package wct.background;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import org.apache.commons.lang3.RandomStringUtils;
import wct.fileprocessing.FileProcessor;
import wct.fileprocessing.TextReaderWriter;
import wct.resourses.Keyboard;
import wct.resourses.Mouse;
import wct.resourses.Position;
import wct.resourses.Screen;
import wct.resourses.SystemClipboard;

/**
 *
 * @author xuanlinhha
 */
public class FileSender extends SwingWorker<Void, Void> {

    private static final Long SWITCH_TIME = 1000L;
    private static final int RAMDOM_LENGTH = 20;
    private static final Long CLICK_WAITING = 1000L;
    private static final Long GO_TOP_WAITING = 2000L;
    private static final String SENT_FILE_GROUPS = "sent_groups_FILE.txt";

    // gui
    private JButton startJButton;
    private JButton stopJButton;

    // data
    private String inputFolder;
    private int noOfGroups;
    private long sendingTime;
    private Position taskbarPosition;
    private Position scrollPosition;
    private long scrollTime;
    private int counter;

    // image recognition
    private boolean groupRecognition;
    private List<Position> imagePositions;
    private boolean isContinue;
    private static Set<String> sentGroups = new HashSet<String>();
    private Position secondLastPosition;

    @Override
    protected Void doInBackground() {
        startJButton.setEnabled(false);
        stopJButton.setEnabled(true);
        if (groupRecognition) {
            bottomUpSendWithImageRecognition();
        } else {
            bottomUpSend();
        }
        startJButton.setEnabled(true);
        stopJButton.setEnabled(false);
        return null;
    }

    private void bottomUpSend() {
        try {
            // click to WeChat app
            Mouse.getInstance().click(taskbarPosition);
            Thread.sleep(SWITCH_TIME);

            // run
            String randString = RandomStringUtils.random(RAMDOM_LENGTH);
            counter = 0;
            while (counter < noOfGroups) {
                Mouse.getInstance().press(scrollPosition, scrollTime);
                FileProcessor.changeHashcode(inputFolder, randString + counter);
                SystemClipboard.getInstance().copyFiles(FileProcessor.getFiles(inputFolder));
                Mouse.getInstance().click(imagePositions.get(0));
                Thread.sleep(CLICK_WAITING);
                Keyboard.getInstance().pasteWithEnter();
                counter++;
                if (counter < noOfGroups) {
                    Thread.sleep(sendingTime);
                }
                if (isCancelled()) {
                    break;
                }
            }
            JOptionPane.showMessageDialog(null, noOfGroups + " groups sent!", "Sent Groups", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void bottomUpSendWithImageRecognition() {
        boolean isInterrupted = false;
        try {
            Screen sc = Screen.getInstance();
            sc.setPositions(imagePositions);
            // clear if start from beginning
            if (!isContinue) {
                sentGroups.clear();
            } else {
                sentGroups = TextReaderWriter.loadSentFileGroups(SENT_FILE_GROUPS);
            }

            // click to WeChat app
            Mouse.getInstance().click(taskbarPosition);
            Thread.sleep(SWITCH_TIME);

            // run
            String randString = RandomStringUtils.random(RAMDOM_LENGTH);
            counter = 0;
            while (counter < noOfGroups) {
                Mouse.getInstance().press(scrollPosition, scrollTime);
                Mouse.getInstance().click(imagePositions.get(0));
                Thread.sleep(CLICK_WAITING);

                // identify group is sent or not
                boolean isAdded = false;
                String color = "";
                while (true) {
                    if (isCancelled()) {
                        break;
                    }
                    color = sc.getColorData();
                    if (sentGroups.contains(color)) {
                        SystemClipboard.getInstance().copyString("U");
                        Keyboard.getInstance().pasteWithoutEnter();
                        Mouse.getInstance().click(secondLastPosition);
                        Thread.sleep(GO_TOP_WAITING);
                    } else {
                        sentGroups.add(color);
                        isAdded = true;
                        break;
                    }
                }
                if (isCancelled()) {
                    if (isAdded) {
                        sentGroups.remove(color);
                    }
                    break;
                }
                // send video
                FileProcessor.changeHashcode(inputFolder, randString + counter);
                SystemClipboard.getInstance().copyFiles(FileProcessor.getFiles(inputFolder));
                Keyboard.getInstance().pasteWithEnter();
                counter++;
                if (counter < noOfGroups) {
                    Thread.sleep(sendingTime);
                }

            }
            JOptionPane.showMessageDialog(null, sentGroups.size() + " groups sent!", "Sent Groups", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            TextReaderWriter.saveSentFileGroups(SENT_FILE_GROUPS, sentGroups);
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

    public Position getSecondLastPosition() {
        return secondLastPosition;
    }

    public void setSecondLastPosition(Position secondLastPosition) {
        this.secondLastPosition = secondLastPosition;
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

    public boolean isGroupRecognition() {
        return groupRecognition;
    }

    public void setGroupRecognition(boolean groupRecognition) {
        this.groupRecognition = groupRecognition;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public boolean isIsContinue() {
        return isContinue;
    }

    public void setIsContinue(boolean isContinue) {
        this.isContinue = isContinue;
    }

}
