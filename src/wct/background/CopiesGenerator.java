package wct.background;

import java.io.File;
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

    private CopiesGeneratorParams cgParams;

    @Override
    protected Void doInBackground() {
        long startTime = System.currentTimeMillis();
        cgParams.getGenerateJButton().setEnabled(false);
        cgParams.getStopJButton().setEnabled(true);
        try {
            File folder = new File(cgParams.getInputFolder());
            File[] files = folder.listFiles();

            for (int i = 1; i <= cgParams.getNoOfCopies(); i++) {
                String randString = RandomStringUtils.random(RAMDOM_LENGTH) + i;
                FileProcessor.changeFilesHashcode(cgParams.getInputFolder(), randString);
                FileProcessor.copyToOutput(i, cgParams.getInputFolder(), cgParams.getOutputFolder());
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
        cgParams.getGenerateJButton().setEnabled(true);
        cgParams.getStopJButton().setEnabled(false);
        return null;

    }

    public CopiesGeneratorParams getCgParams() {
        return cgParams;
    }

    public void setCgParams(CopiesGeneratorParams cgParams) {
        this.cgParams = cgParams;
    }

}
