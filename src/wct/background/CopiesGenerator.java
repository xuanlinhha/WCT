package wct.background;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import org.apache.commons.lang3.RandomStringUtils;

/**
 *
 * @author xuanlinhha
 */
public class CopiesGenerator extends SwingWorker<Void, Void> {

    private static final int RAMDOM_LENGTH = 20;

    // gui
    private JFrame parent;
    private JButton generateButton;

    // task
    private int noOfCopies;
    private String kid3Path;
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
                for (File f : files) {
                    Process p = Runtime.getRuntime().exec(new String[]{kid3Path, "-c", "set comment " + randString, f.getAbsolutePath()});
                    p.waitFor();
                    // copy
                    Files.copy(Paths.get(f.toURI()), Paths.get(outputFolder + File.separator + i + "_" + f.getName()), REPLACE_EXISTING);
                }
                if (isCancelled()) {
                    break;
                }
            }
            if (!isCancelled()) {
                long runningTime = System.currentTimeMillis() - startTime;
                JOptionPane.showMessageDialog(parent, "Time: " + runningTime, "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
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
