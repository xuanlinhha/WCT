package wct.background;

import javax.swing.JButton;
import javax.swing.JTextArea;

/**
 *
 * @author xuanlinhha
 */
public class MouseDetectorParams {

    private JTextArea jTextArea;
    private JButton startJButton;
    private JButton stopJButton;

    public JTextArea getjTextArea() {
        return jTextArea;
    }

    public void setjTextArea(JTextArea jTextArea) {
        this.jTextArea = jTextArea;
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
