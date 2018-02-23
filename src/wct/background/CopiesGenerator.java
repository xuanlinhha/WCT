package wct.background;

import java.io.File;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import org.apache.commons.lang3.RandomStringUtils;
import wct.fileprocessing.FileProcessor;

/**
 *
 * @author xuanlinhha
 */
public class CopiesGenerator extends SwingWorker<Void, Void> {

    private static final int RAMDOM_LENGTH = 20;

    // gui
    private JButton generateButton;

    // task
    private int noOfCopies;
    private String inputFolder;
    private String outputFolder;

    @Override
    protected Void doInBackground() {
        long startTime = System.currentTimeMillis();
        generateButton.setEnabled(false);
        try {
            File folder = new File(inputFolder);
            File[] files = folder.listFiles();

            for (int i = 1; i <= noOfCopies; i++) {
                String randString = RandomStringUtils.random(RAMDOM_LENGTH) + i;
                FileProcessor.changeHashcode(inputFolder, randString);
                FileProcessor.copyToOutput(i, inputFolder, outputFolder);
                if (isCancelled()) {
                    break;
                }
            }
            if (!isCancelled()) {
                long runningTime = System.currentTimeMillis() - startTime;
                JOptionPane.showMessageDialog(null, "Time: " + runningTime, "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        generateButton.setEnabled(true);
        return null;

    }

    public JButton getGenerateButton() {
        return generateButton;
    }

    public void setGenerateButton(JButton generateButton) {
        this.generateButton = generateButton;
    }

    public int getNoOfCopies() {
        return noOfCopies;
    }

    public void setNoOfCopies(int noOfCopies) {
        this.noOfCopies = noOfCopies;
    }

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

}
