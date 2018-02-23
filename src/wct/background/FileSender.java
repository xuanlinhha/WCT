package wct.background;

import java.util.List;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import org.apache.commons.lang3.RandomStringUtils;
import wct.fileprocessing.FileProcessor;
import wct.mk.Keyboard;
import wct.mk.Mouse;
import wct.mk.Position;
import wct.mk.Screen;
import wct.mk.SystemClipboard;

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
    private Set<String> sentGroups;

    private Position taskbarPosition;
    private Position scrollPosition;
    private long scrollTime;
    private String alternativeMsg;
    private List<Position> imagePositions;

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
            Screen sc = Screen.getInstance();
            sc.initPositions(imagePositions.get(0), imagePositions.get(1));

            // click to WeChat app
            Mouse.getInstance().click(taskbarPosition);
            Thread.sleep(SWITCH_TIME);

            // run
            String randString = RandomStringUtils.random(RAMDOM_LENGTH);
            int counter = 0;
            while (counter < noOfGroups) {

                Mouse.getInstance().press(scrollPosition, scrollTime);

                String color = sc.getColorData();

                boolean isNew = false;
                if (!sentGroups.contains(color)) {

                    FileProcessor.changeHashcode(inputFolder, randString + counter);
                    SystemClipboard.getInstance().copyFiles(FileProcessor.getFiles(inputFolder));

                    sentGroups.add(color);
                    counter++;
                    isNew = true;
                } else {
                    SystemClipboard.getInstance().copyString(alternativeMsg);
                }

                Mouse.getInstance().click(imagePositions.get(0));

                Keyboard.getInstance().paste();
                
                if (isNew) {
                    Thread.sleep(sendingTime);
                }
                if (isCancelled()) {
                    break;
                }
            }
            JOptionPane.showMessageDialog(null, sentGroups.size() + " groups sent!", "Sent Groups", JOptionPane.INFORMATION_MESSAGE);
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
