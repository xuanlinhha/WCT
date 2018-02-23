package wct.background;

import java.util.List;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import wct.mk.Keyboard;
import wct.mk.Mouse;
import wct.mk.Position;
import wct.mk.Screen;
import wct.mk.SystemClipboard;

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
            Screen sc = Screen.getInstance();
            sc.initPositions(imagePositions.get(0), imagePositions.get(1));

            // click to WeChat app
            Mouse.getInstance().click(taskbarPosition);

            int counter = 0;
            while (counter < noOfGroups) {
                Mouse.getInstance().press(scrollPosition, scrollTime);

                String color = sc.getColorData();

                if (sentGroups.contains(color)) {
                    SystemClipboard.getInstance().copyString(alternativeMsg);

                } else {
                    SystemClipboard.getInstance().copyString(text);
                    sentGroups.add(color);
                    counter++;
                }

                Mouse.getInstance().click(imagePositions.get(0));

                Keyboard.getInstance().paste();

                if (isCancelled()) {
                    break;
                }
            }
            JOptionPane.showMessageDialog(null, sentGroups.size() + " groups sent!", "Sent Groups", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
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
