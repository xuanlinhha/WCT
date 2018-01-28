package wct.background;

import java.io.File;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.SystemUtils;

/**
 *
 * @author xuanlinhha
 */
public class CopiesGenerator extends SwingWorker<Void, Void> {

    // gui
    private JFrame parent;
    private JButton generateButton;
    private JTextField noOfFilesTextField;

    // task
    private int noOfCopies;
    private String kid3Path;
    private String inputFolder;
    private String outputFolder;

    @Override
    protected Void doInBackground() throws Exception {
        long startTime = System.currentTimeMillis();
        generateButton.setEnabled(false);
        File folder = new File(inputFolder);
        File[] files = folder.listFiles();

        Integer noOfFiles = 0;
        for (File f : files) {
            if (f.isFile() && (f.getName().endsWith(".mp3") || f.getName().endsWith(".mp4"))) {
                noOfFiles++;
            }
        }

        boolean isError = false;
        for (int i = 1; i <= noOfCopies; i++) {
            String comment = RandomStringUtils.random(10) + i;
            for (File f : files) {
                if (f.isFile() && (f.getName().endsWith(".mp3") || f.getName().endsWith(".mp4"))) {
                    try {
                        // change md5
                        Process p = Runtime.getRuntime().exec(new String[]{kid3Path, "-c", "set comment " + comment, f.getAbsolutePath()});
                        p.waitFor();
                        // copy to output
                        String newName = outputFolder + File.separator + i + "_" + f.getName();
                        if (SystemUtils.IS_OS_MAC || SystemUtils.IS_OS_UNIX) {
                            p = Runtime.getRuntime().exec(new String[]{"cp", f.getAbsolutePath(), newName});
                        } else if (SystemUtils.IS_OS_WINDOWS) {
                            p = Runtime.getRuntime().exec(new String[]{"cmd", "/c", "copy", "/y", f.getAbsolutePath(), newName});
                        }
                        p.waitFor();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(parent, "Cannot generate copies", "Error", JOptionPane.ERROR_MESSAGE);
                        ex.printStackTrace();
                        isError = true;
                    }
                }
                if (isCancelled() || isError) {
                    break;
                }
            }
            if (isCancelled() || isError) {
                break;
            }
        }
        if (!isCancelled() && !isError) {
            long runningTime = System.currentTimeMillis() - startTime;
            JOptionPane.showMessageDialog(parent, "Time: " + runningTime, "Success", JOptionPane.INFORMATION_MESSAGE);
            noOfFilesTextField.setText(noOfFiles.toString());
        }
        generateButton.setEnabled(true);
        return null;
    }

    public JFrame getParent() {
        return parent;
    }

    public void setParent(JFrame parent) {
        this.parent = parent;
    }

    public JButton getGenerateButton() {
        return generateButton;
    }

    public void setGenerateButton(JButton generateButton) {
        this.generateButton = generateButton;
    }

    public JTextField getNoOfFilesTextField() {
        return noOfFilesTextField;
    }

    public void setNoOfFilesTextField(JTextField noOfFilesTextField) {
        this.noOfFilesTextField = noOfFilesTextField;
    }

    public int getNoOfCopies() {
        return noOfCopies;
    }

    public void setNoOfCopies(int noOfCopies) {
        this.noOfCopies = noOfCopies;
    }

    public String getKid3Path() {
        return kid3Path;
    }

    public void setKid3Path(String kid3Path) {
        this.kid3Path = kid3Path;
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
