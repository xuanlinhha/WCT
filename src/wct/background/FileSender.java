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

    private static final int SLEEP_TIME = 900;
    private static final int SCROLL_TIMES = 10;
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

            // initial data
            Coordinate click1 = new Coordinate(fsParams.getCorner2().getX(), fsParams.getCorner1().getY());
            Coordinate click2 = fsParams.getCorner3();
            counter = 0;

            // click on wechat & select the top
            Mouse.getInstance().click(fsParams.getOnTaskbarCoordinate());
            Thread.sleep(1000);

            sendDown(fsParams.getDownTimes(), fsParams.getScroll1(), click1);
            sendUp(fsParams.getUpTimes(), fsParams.getTimesToBottom(), fsParams.getScroll1(), fsParams.getScroll2(), click2);
            sendDown(fsParams.getDownTimes(), fsParams.getScroll1(), click1);

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

    private void sendDown(int maxTimes, Coordinate scroll1, Coordinate click) throws Exception {
        Screen screen = new Screen(fsParams);
        String randString = RandomStringUtils.random(RAMDOM_LENGTH);
        int times = 0;
        while (times < maxTimes) {
            Mouse.getInstance().click(click);
            Thread.sleep(SLEEP_TIME);
            Coordinate unsentGroup = screen.getFirstUnsentGroupDown(sentGroups);
            if (unsentGroup == null) {
                times++;
                Mouse.getInstance().click(scroll1);
                Thread.sleep(SLEEP_TIME);
            } else {
                Mouse.getInstance().click(unsentGroup);
                Thread.sleep(SLEEP_TIME);
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
                } else {
                    FileProcessor.changeFilesHashcode(fsParams.getInputFolder(), randString + counter);
                    SystemClipboard.getInstance().copyFiles(FileProcessor.getFiles(fsParams.getInputFolder()));
                    Keyboard.getInstance().pasteWithEnter();
                }
                counter++;
                times = 0;
                Thread.sleep(fsParams.getSendingTime() * 1000);
            }
        }
    }

    private void sendUp(int maxTimes, int timesToBottom, Coordinate scroll1, Coordinate scroll2, Coordinate click) throws Exception {
        Screen screen = new Screen(fsParams);
        String randString = RandomStringUtils.random(RAMDOM_LENGTH);
        int times = 0;
        scrollToBottom(timesToBottom, scroll1, click);
        while (times < maxTimes) {
            Mouse.getInstance().click(click);
            Thread.sleep(1000);
            Coordinate unsentGroup = screen.getFirstUnsentGroupUp(sentGroups);
            if (unsentGroup == null) {
                times++;
                Mouse.getInstance().click(scroll1);
                Thread.sleep(1000);
            } else {
                Mouse.getInstance().click(unsentGroup);
                Thread.sleep(1000);
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
                } else {
                    FileProcessor.changeFilesHashcode(fsParams.getInputFolder(), randString + counter);
                    SystemClipboard.getInstance().copyFiles(FileProcessor.getFiles(fsParams.getInputFolder()));
                    Keyboard.getInstance().pasteWithEnter();
                }
                counter++;
                times = 0;
                Thread.sleep(fsParams.getSendingTime() * 1000);
                scrollToBottom(timesToBottom, scroll1, click);
            }
        }
    }

    private void scrollToBottom(int times, Coordinate c, Coordinate click) throws Exception {
        for (int i = 0; i < times; i++) {
            Mouse.getInstance().press(c, 1000L);
            if (i == times - 1) {
                Mouse.getInstance().scrollDown(SCROLL_TIMES);
            } else {
                Mouse.getInstance().click(click);
                Thread.sleep(1000);
            }
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
