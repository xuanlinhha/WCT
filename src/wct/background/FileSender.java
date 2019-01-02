package wct.background;

import java.awt.image.BufferedImage;
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
            Map<String, Integer> prev = null;
            boolean isDown = true;
            int stop = 0;
            String randString = RandomStringUtils.random(RAMDOM_LENGTH);
            counter = 0;
            while (counter < fsParams.getNoOfGroups()) {
                if (isCancelled()) {
                    break;
                }
                if (stop == 2) {
                    break;
                }
                BufferedImage avatar = Screen.getInstance().captureAvatar(fsParams.getSelectedColor(), fsParams.getImageCoordinate1(), fsParams.getImageCoordinate2());

                if (avatar != null) {
                    Map<String, Integer> current = Screen.getInstance().extractData(avatar);
                    // check if the new avatar has been processed or not
                    boolean exist = false;
                    for (Map<String, Integer> m : sentGroups) {
                        if (Screen.getInstance().isSame(current, m)) {
                            exist = true;
                            break;
                        }
                    }
                    if (exist) {
                        if (prev != null && Screen.getInstance().isSame(current, prev)) {
                            // toggle
                            isDown = !isDown;
                            stop++;
                        } else {
                            // save to check the next one
                            prev = current;
                        }
                    } else {

                        // send video
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
                        sentGroups.add(current);
                        if (counter < fsParams.getNoOfGroups()) {
                            Thread.sleep(fsParams.getSendingTime() * 1000);
                        }
                    }
                }
                if (isDown) {
                    Keyboard.getInstance().down(1);
                } else {
                    Keyboard.getInstance().up(1);
                }
                Thread.sleep(1000);
            }
            // reset hook
            fsParams.getKeyboardHook().shutdownHook();
            if (fsParams.isShutdownAfterFinish() && !isCancelled()) {
                String shutdownCommand = "shutdown.exe -s -t 60";
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
