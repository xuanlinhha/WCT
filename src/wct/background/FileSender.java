package wct.background;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
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
    private static final Long CLICK_WAITING = 1000L;
    private static final Long GO_TOP_WAITING = 2000L;
    private static final String SENT_FILE_GROUPS = "sent_groups_FILE.txt";

    private FileSenderParams fsParams;
    private int counter;
    private static Set<String> sentGroups = new HashSet<String>();

    @Override
    protected Void doInBackground() {
        fsParams.getStartJButton().setEnabled(false);
        fsParams.getStopJButton().setEnabled(true);
        if (fsParams.getImageRecognitionJCheckBox().isSelected()) {
            bottomUpSendWithImageRecognition();
        } else {
            bottomUpSend();
        }
        fsParams.getStartJButton().setEnabled(true);
        fsParams.getStopJButton().setEnabled(false);
        return null;
    }

    private void bottomUpSend() {
        ResourceBundle bundle = LanguageHandler.getInstance().getBundle();
        try {
            // click to WeChat app
            Mouse.getInstance().click(fsParams.getOnTaskbarCoordinate());
            Thread.sleep(SWITCH_TIME);

            // run
            String randString = RandomStringUtils.random(RAMDOM_LENGTH);
            counter = 0;
            while (counter < fsParams.getNoOfGroups()) {
                Mouse.getInstance().press(fsParams.getScrollingCoordinate(), fsParams.getScrollingTime());
                FileProcessor.changeHashcode(fsParams.getInputFolder(), randString + counter);
                SystemClipboard.getInstance().copyFiles(FileProcessor.getFiles(fsParams.getInputFolder()));
                Mouse.getInstance().click(fsParams.getImageCoordinate1());
                Thread.sleep(CLICK_WAITING);
                Keyboard.getInstance().pasteWithEnter();
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

    private void bottomUpSendWithImageRecognition() {
        ResourceBundle bundle = LanguageHandler.getInstance().getBundle();
        try {
            Screen sc = Screen.getInstance();
            List<Coordinate> coors = new ArrayList<Coordinate>();
            coors.add(fsParams.getImageCoordinate1());
            coors.add(fsParams.getImageCoordinate2());
            sc.setPositions(coors);
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
            while (counter < fsParams.getNoOfGroups()) {
                Mouse.getInstance().press(fsParams.getScrollingCoordinate(), fsParams.getScrollingTime());
                Mouse.getInstance().click(fsParams.getImageCoordinate1());
                Thread.sleep(CLICK_WAITING);

                // identify group is sent or not
                boolean isAdded = false;
                String color = "";
                while (true) {
                    if (isCancelled()) {
                        break;
                    }
                    color = sc.getColorData();
                    if (sentGroups.contains(color)) {
                        SystemClipboard.getInstance().copyString("U");
                        Keyboard.getInstance().pasteWithoutEnter();
                        Mouse.getInstance().click(fsParams.getSecondLastCoordinate());
                        Thread.sleep(GO_TOP_WAITING);
                    } else {
                        sentGroups.add(color);
                        isAdded = true;
                        break;
                    }
                }
                if (isCancelled()) {
                    if (isAdded) {
                        sentGroups.remove(color);
                    }
                    break;
                }
                // send video
                FileProcessor.changeHashcode(fsParams.getInputFolder(), randString + counter);
                SystemClipboard.getInstance().copyFiles(FileProcessor.getFiles(fsParams.getInputFolder()));
                Keyboard.getInstance().pasteWithEnter();
                counter++;
                if (counter < fsParams.getNoOfGroups()) {
                    Thread.sleep(fsParams.getSendingTime());
                }

            }
            JOptionPane.showMessageDialog(null, MessageFormat.format(bundle.getString("result_message"), sentGroups.size()), bundle.getString("result_title"), JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            TextReaderWriter.saveSentFileGroups(SENT_FILE_GROUPS, sentGroups);
            fsParams.getOptionJComboBox().setSelectedIndex(1);
        }
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
