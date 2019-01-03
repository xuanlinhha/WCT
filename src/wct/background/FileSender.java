package wct.background;

import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import org.apache.commons.lang3.RandomStringUtils;
import wct.fileprocessing.FileProcessor;
import wct.fileprocessing.TextReaderWriter;
import wct.multilanguage.LanguageHandler;
import wct.resourses.Coordinate;
import wct.resourses.Keyboard;
import wct.resourses.Mouse;
import wct.resourses.Screen;
import wct.resourses.SystemClipboard;

/**
 *
 * @author xuanlinhha
 */
public class FileSender extends SwingWorker<Void, Void> {

    private static final Long SWITCH_TIME = 1000L;
    private static final int RAMDOM_LENGTH = 20;
    private static final String SENT_FILE_GROUPS = "sent_groups_FILE.txt";

    private FileSenderParams fsParams;
    private int counter;
    private static List<Map<String, Integer>> sentGroups = new ArrayList<Map<String, Integer>>();

    @Override
    protected Void doInBackground() {
        fsParams.getStartJButton().setEnabled(false);
        fsParams.getStopJButton().setEnabled(true);
        send();
        fsParams.getStartJButton().setEnabled(true);
        fsParams.getStopJButton().setEnabled(false);
        return null;
    }

    private void send() {
        ResourceBundle bundle = LanguageHandler.getInstance().getBundle();
        try {
            // clear if start from beginning
            if (fsParams.getOptionJComboBox().getSelectedIndex() == 0) { // from beginning
                sentGroups.clear();
            } else {
                sentGroups = TextReaderWriter.loadSentFileGroups(SENT_FILE_GROUPS);
            }

            // click to WeChat app
            Mouse.getInstance().click(fsParams.getOnTaskbarCoordinate());
            Thread.sleep(SWITCH_TIME);

            // run
            String randString = RandomStringUtils.random(RAMDOM_LENGTH);
            counter = 0;
            int loop = 0;
            while (counter < fsParams.getNoOfGroups()) {
                if (isCancelled()) {
                    break;
                }
                // find unsent group
                Coordinate unsentGroup = Screen.getInstance().getFirstUnsentGroup(fsParams, sentGroups);

                if (unsentGroup == null) {
                    Mouse.getInstance().click(fsParams.getScrollingCoordinate());
                    loop++;
                    if (loop == 50) {
                        break;
                    }
                    Thread.sleep(500);
                } else {
                    Mouse.getInstance().click(unsentGroup);
                    if (fsParams.isOneByOne()) {
                        List<File> files = FileProcessor.getFiles(fsParams.getInputFolder());
                        for (int i = 0; i < files.size(); i++) {
                            File f = files.get(i);
                            FileProcessor.changeFileHashcode(f, randString + counter);
                            SystemClipboard.getInstance().copyFile(f);
                            Keyboard.getInstance().pasteWithEnter();
                            if (i < files.size() - 1) {
                                Thread.sleep(fsParams.getSendingTime() * 1000);
                            }
                        }
                        System.out.println("Sent");
                    } else {
                        FileProcessor.changeFilesHashcode(fsParams.getInputFolder(), randString + counter);
                        SystemClipboard.getInstance().copyFiles(FileProcessor.getFiles(fsParams.getInputFolder()));
                        Keyboard.getInstance().pasteWithEnter();
                    }
                    counter++;
                }
            }
            // reset hook
            fsParams.getKeyboardHook().shutdownHook();
            if (fsParams.isShutdownAfterFinish() && !isCancelled()) {
                String shutdownCommand = "shutdown.exe -s -t 180";
                Runtime.getRuntime().exec(shutdownCommand);
            }
            JOptionPane.showMessageDialog(null, MessageFormat.format(bundle.getString("result_message"), sentGroups.size()), bundle.getString("result_title"), JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            TextReaderWriter.saveSentFileGroups(SENT_FILE_GROUPS, sentGroups);
            fsParams.getOptionJComboBox().setSelectedIndex(1);
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
