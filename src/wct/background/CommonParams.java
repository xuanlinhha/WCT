package wct.background;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import wct.resourses.Coordinate;

/**
 *
 * @author xuanlinhha
 */
public class CommonParams {

    protected Coordinate onTaskbarCoordinate;
    protected Coordinate scrollingCoordinate;
    protected Coordinate imageCoordinate1;
    protected Coordinate imageCoordinate2;
    protected Coordinate secondLastCoordinate;
    private long scrollingTime;
    private JComboBox optionJComboBox;
    private JCheckBox imageRecognitionJCheckBox;
    private JButton startJButton;
    private JButton stopJButton;

    public Coordinate getOnTaskbarCoordinate() {
        return onTaskbarCoordinate;
    }

    public void setOnTaskbarCoordinate(Coordinate onTaskbarCoordinate) {
        this.onTaskbarCoordinate = onTaskbarCoordinate;
    }

    public Coordinate getScrollingCoordinate() {
        return scrollingCoordinate;
    }

    public void setScrollingCoordinate(Coordinate scrollingCoordinate) {
        this.scrollingCoordinate = scrollingCoordinate;
    }

    public Coordinate getImageCoordinate1() {
        return imageCoordinate1;
    }

    public void setImageCoordinate1(Coordinate imageCoordinate1) {
        this.imageCoordinate1 = imageCoordinate1;
    }

    public Coordinate getImageCoordinate2() {
        return imageCoordinate2;
    }

    public void setImageCoordinate2(Coordinate imageCoordinate2) {
        this.imageCoordinate2 = imageCoordinate2;
    }

    public Coordinate getSecondLastCoordinate() {
        return secondLastCoordinate;
    }

    public void setSecondLastCoordinate(Coordinate secondLastCoordinate) {
        this.secondLastCoordinate = secondLastCoordinate;
    }

    public long getScrollingTime() {
        return scrollingTime;
    }

    public void setScrollingTime(long scrollingTime) {
        this.scrollingTime = scrollingTime;
    }

    public JComboBox getOptionJComboBox() {
        return optionJComboBox;
    }

    public void setOptionJComboBox(JComboBox optionJComboBox) {
        this.optionJComboBox = optionJComboBox;
    }

    public JCheckBox getImageRecognitionJCheckBox() {
        return imageRecognitionJCheckBox;
    }

    public void setImageRecognitionJCheckBox(JCheckBox imageRecognitionJCheckBox) {
        this.imageRecognitionJCheckBox = imageRecognitionJCheckBox;
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
