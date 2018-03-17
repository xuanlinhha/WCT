package wct.background;

import javax.swing.JButton;
import wct.resourses.Coordinate;

/**
 *
 * @author xuanlinhha
 */
public class FileSenderParams {

    private String inputFolder;
    private Coordinate onTaskbarCoordinate;
    private int noOfGroups;
    private long sendingTime;
    private JButton startJButton;
    private JButton stopJButton;

    public String getInputFolder() {
        return inputFolder;
    }

    public void setInputFolder(String inputFolder) {
        this.inputFolder = inputFolder;
    }

    public Coordinate getOnTaskbarCoordinate() {
        return onTaskbarCoordinate;
    }

    public void setOnTaskbarCoordinate(Coordinate onTaskbarCoordinate) {
        this.onTaskbarCoordinate = onTaskbarCoordinate;
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

}
