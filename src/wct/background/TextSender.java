package wct.background;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import wct.fileprocessing.TextReaderWriter;
import wct.resourses.Keyboard;
import wct.resourses.Mouse;
import wct.resourses.Position;
import wct.resourses.Screen;
import wct.resourses.SystemClipboard;

/**
 *
 * @author workshop
 */
public class TextSender extends SwingWorker<Void, Void> {

    private static final Long SWITCH_TIME = 1000L;
    private static final Long CLICK_WAITING = 1000L;
    private static final Long GO_TOP_WAITING = 2000L;
    private static final String SENT_TEXT_GROUPS = "sent_groups_TEXT.txt";

    // gui
    private JButton startJButton;
    private JButton stopJButton;

    // data
    private String text;
    private int noOfGroups;
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
            Mouse.getInstance().click(taskbarPosition);
            SystemClipboard.getInstance().copyString(text);
            Thread.sleep(SWITCH_TIME);
            counter = 0;
            while (counter < noOfGroups) {
                Mouse.getInstance().press(scrollPosition, scrollTime);
                Mouse.getInstance().click(imagePositions.get(0));
                Thread.sleep(CLICK_WAITING);
                Keyboard.getInstance().pasteWithEnter();
                counter++;
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
        try {
            Screen sc = Screen.getInstance();
            sc.setPositions(imagePositions);
            // clear if start from beginning
            if (!isContinue) {
                sentGroups.clear();
            } else {
                sentGroups = TextReaderWriter.loadSentFileGroups(SENT_TEXT_GROUPS);
            }

            // click to WeChat app
            Mouse.getInstance().click(taskbarPosition);

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
                // send text
                SystemClipboard.getInstance().copyString(text);
                Keyboard.getInstance().pasteWithEnter();
                counter++;
            }

            JOptionPane.showMessageDialog(null, sentGroups.size() + " groups sent!", "Sent Groups", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            TextReaderWriter.saveSentFileGroups(SENT_TEXT_GROUPS, sentGroups);
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

    public Position getSecondLastPosition() {
        return secondLastPosition;
    }

    public void setSecondLastPosition(Position secondLastPosition) {
        this.secondLastPosition = secondLastPosition;
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
