package wct.background;

import javax.swing.JButton;

/**
 *
 * @author xuanlinhha
 */
public class CopiesGeneratorParams {

    private String inputFolder;
    private String outputFolder;
    private int noOfCopies;
    private JButton generateJButton;
    private JButton stopJButton;

    public String getInputFolder() {
        return inputFolder;
    }

    public void setInputFolder(String inputFolder) {
        this.inputFolder = inputFolder;
    }

    public String getOutputFolder() {
        return outputFolder;
    }

    public void setOutputFolder(String outputFolder) {
        this.outputFolder = outputFolder;
    }

    public int getNoOfCopies() {
        return noOfCopies;
    }

    public void setNoOfCopies(int noOfCopies) {
        this.noOfCopies = noOfCopies;
    }

    public JButton getGenerateJButton() {
        return generateJButton;
    }

    public void setGenerateJButton(JButton generateJButton) {
        this.generateJButton = generateJButton;
    }

    public JButton getStopJButton() {
        return stopJButton;
    }

    public void setStopJButton(JButton stopJButton) {
        this.stopJButton = stopJButton;
    }

}
