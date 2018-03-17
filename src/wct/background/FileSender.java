package wct.background;

import java.text.MessageFormat;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import org.apache.commons.lang3.RandomStringUtils;
import wct.fileprocessing.FileProcessor;
import wct.multilanguage.LanguageHandler;
import wct.resourses.Keyboard;
import wct.resourses.Mouse;
import wct.resourses.SystemClipboard;

/**
 *
 * @author xuanlinhha
 */
public class FileSender extends SwingWorker<Void, Void> {

    private static final Long SWITCH_TIME = 1000L;
    private static final int RAMDOM_LENGTH = 20;
    private FileSenderParams fsParams;
    private int counter;

    @Override
    protected Void doInBackground() {
        fsParams.getStartJButton().setEnabled(false);
        fsParams.getStopJButton().setEnabled(true);
        topdownSend();
        fsParams.getStartJButton().setEnabled(true);
        fsParams.getStopJButton().setEnabled(false);
        return null;
    }

    private void topdownSend() {
        ResourceBundle bundle = LanguageHandler.getInstance().getBundle();
        try {
            // click to WeChat app
            Mouse.getInstance().click(fsParams.getOnTaskbarCoordinate());
            Thread.sleep(SWITCH_TIME);

            // run
            String randString = RandomStringUtils.random(RAMDOM_LENGTH);
            counter = 0;
            while (counter < fsParams.getNoOfGroups()) {
                FileProcessor.changeHashcode(fsParams.getInputFolder(), randString + counter);
                SystemClipboard.getInstance().copyFiles(FileProcessor.getFiles(fsParams.getInputFolder()));
                Keyboard.getInstance().pasteWithEnter();
                Keyboard.getInstance().down(1);
                counter++;
                if (counter < fsParams.getNoOfGroups()) {
                    Thread.sleep(fsParams.getSendingTime());
                }
                if (isCancelled()) {
                    break;
                }
            }
            JOptionPane.showMessageDialog(null, MessageFormat.format(bundle.getString("result_message"), fsParams.getNoOfGroups()), bundle.getString("result_title"), JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public FileSenderParams getFsParams() {
        return fsParams;
    }

    public void setFsParams(FileSenderParams fsParams) {
        this.fsParams = fsParams;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

}
