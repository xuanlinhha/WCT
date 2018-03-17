package wct.background;

import javax.swing.JButton;
import wct.resourses.Coordinate;

/**
 *
 * @author xuanlinhha
 */
public class TextSenderParams {

    private String text;
    private Coordinate onTaskbarCoordinate;
    private int noOfGroups;
    private JButton startJButton;
    private JButton stopJButton;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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
