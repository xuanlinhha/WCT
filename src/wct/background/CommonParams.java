package wct.background;

import javax.swing.JButton;
import javax.swing.JComboBox;
import wct.resourses.Coordinate;

/**
 *
 * @author xuanlinhha
 */
public class CommonParams {

    protected Coordinate onTaskbarCoordinate;
    protected int groupsInRegion;
    protected Coordinate scroll1;
    protected Coordinate scroll2;
    protected Coordinate corner1;
    protected Coordinate corner2;
    protected Coordinate corner3;
    protected Coordinate corner4;
    
    private JComboBox optionJComboBox;
    private JButton startJButton;
    private JButton stopJButton;

    public Coordinate getOnTaskbarCoordinate() {
        return onTaskbarCoordinate;
    }

    public void setOnTaskbarCoordinate(Coordinate onTaskbarCoordinate) {
        this.onTaskbarCoordinate = onTaskbarCoordinate;
    }

    public int getGroupsInRegion() {
        return groupsInRegion;
    }

    public void setGroupsInRegion(int groupsInRegion) {
        this.groupsInRegion = groupsInRegion;
    }

    public Coordinate getScroll1() {
        return scroll1;
    }

    public void setScroll1(Coordinate scroll1) {
        this.scroll1 = scroll1;
    }

    public Coordinate getScroll2() {
        return scroll2;
    }

    public void setScroll2(Coordinate scroll2) {
        this.scroll2 = scroll2;
    }

    public Coordinate getCorner1() {
        return corner1;
    }

    public void setCorner1(Coordinate corner1) {
        this.corner1 = corner1;
    }

    public Coordinate getCorner2() {
        return corner2;
    }

    public void setCorner2(Coordinate corner2) {
        this.corner2 = corner2;
    }

    public Coordinate getCorner3() {
        return corner3;
    }

    public void setCorner3(Coordinate corner3) {
        this.corner3 = corner3;
    }

    public Coordinate getCorner4() {
        return corner4;
    }

    public void setCorner4(Coordinate corner4) {
        this.corner4 = corner4;
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
