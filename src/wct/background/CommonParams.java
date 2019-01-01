package wct.background;

import javax.swing.JButton;
import javax.swing.JComboBox;
import wct.resourses.Color;
import wct.resourses.Coordinate;

/**
 *
 * @author xuanlinhha
 */
public class CommonParams {

    protected Coordinate onTaskbarCoordinate;
    protected Color selectedColor;
    protected Coordinate imageCoordinate1;
    protected Coordinate imageCoordinate2;
    private JComboBox optionJComboBox;
    private JButton startJButton;
    private JButton stopJButton;

    public Coordinate getOnTaskbarCoordinate() {
        return onTaskbarCoordinate;
    }

    public void setOnTaskbarCoordinate(Coordinate onTaskbarCoordinate) {
        this.onTaskbarCoordinate = onTaskbarCoordinate;
    }

    public Color getSelectedColor() {
        return selectedColor;
    }

    public void setSelectedColor(Color selectedColor) {
        this.selectedColor = selectedColor;
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

    public JComboBox getOptionJComboBox() {
        return optionJComboBox;
    }

    public void setOptionJComboBox(JComboBox optionJComboBox) {
        this.optionJComboBox = optionJComboBox;
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
