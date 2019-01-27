package wct.background;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import wct.fileprocessing.TextReaderWriter;
import wct.multilanguage.LanguageHandler;
import wct.resourses.Coordinate;
import wct.resourses.Keyboard;
import wct.resourses.Mouse;
import wct.resourses.Screen;
import wct.resourses.SystemClipboard;

/**
 *
 * @author workshop
 */
public class TextSender extends SwingWorker<Void, Void> {

    private static final int SLEEP_TIME = 900;
    private static final int SCROLL_TIMES = 10;
    private static final String SENT_TEXT_GROUPS = "sent_groups_TEXT.txt";
    private TextSenderParams tsParams;
    private int counter;
    private static List<Map<String, Integer>> sentGroups = new ArrayList<Map<String, Integer>>();

    @Override
    protected Void doInBackground() {
        tsParams.getStartJButton().setEnabled(false);
        tsParams.getStopJButton().setEnabled(true);
        send();
        tsParams.getStartJButton().setEnabled(true);
        tsParams.getStopJButton().setEnabled(false);
        return null;
    }

    private void send() {
        ResourceBundle bundle = LanguageHandler.getInstance().getBundle();
        try {
            // clear if start from beginning
            if (tsParams.getOptionJComboBox().getSelectedIndex() == 0) { // from beginning
                sentGroups.clear();
            } else {
                sentGroups = TextReaderWriter.loadSentFileGroups(SENT_TEXT_GROUPS);
            }

            counter = 0;
            sendDown(false);
            sendUp();

            // click on wechat & select the top
            Mouse.getInstance().click(tsParams.getOnTaskbarCoordinate());
            Thread.sleep(1000);

            tsParams.getKeyboardHook().shutdownHook();
            JOptionPane.showMessageDialog(null, MessageFormat.format(bundle.getString("result_message"), sentGroups.size()), bundle.getString("result_title"), JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            TextReaderWriter.saveSentFileGroups(SENT_TEXT_GROUPS, sentGroups);
            tsParams.getOptionJComboBox().setSelectedIndex(1);
        }
    }

    private void sendDown(boolean isOnce) throws Exception {
        int maxTimes = isOnce ? 1 : tsParams.getDownTimes();
        Screen screen = new Screen(tsParams);
        int times = 0;
        while (times < maxTimes) {
            if (isCancelled()) {
                break;
            }
            Mouse.getInstance().click(tsParams.getCorner1());
            Thread.sleep(SLEEP_TIME);
            Coordinate unsentGroup = screen.getFirstUnsentGroupDown(sentGroups);
            if (unsentGroup == null) {
                times++;
                Mouse.getInstance().click(tsParams.getScroll1());
                Thread.sleep(SLEEP_TIME);
            } else {
                Mouse.getInstance().click(unsentGroup);
                Thread.sleep(SLEEP_TIME);
                // send text
                SystemClipboard.getInstance().copyString(tsParams.getText());
                Keyboard.getInstance().pasteWithEnter();
                counter++;
                times = 0;
            }
        }
    }

    private void sendUp() throws Exception {
        Screen screen = new Screen(tsParams);
        int times = 0;
        scrollToBottom(tsParams.getTimesToBottom(), tsParams.getScroll1(), tsParams.getCorner3());
        while (times < tsParams.getUpTimes()) {
            if (isCancelled()) {
                break;
            }
            Mouse.getInstance().click(tsParams.getCorner3());
            Thread.sleep(1000);
            Coordinate unsentGroup = screen.getFirstUnsentGroupUp(sentGroups);
            if (unsentGroup == null) {
                times++;
                Mouse.getInstance().click(tsParams.getScroll2());
                Thread.sleep(1000);
            } else {
                Mouse.getInstance().click(unsentGroup);
                Thread.sleep(1000);
                // send text
                SystemClipboard.getInstance().copyString(tsParams.getText());
                Keyboard.getInstance().pasteWithEnter();
                counter++;
                times = 0;

                // send down
                sendDown(true);

                scrollToBottom(tsParams.getTimesToBottom(), tsParams.getScroll1(), tsParams.getCorner3());
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

    public TextSenderParams getTsParams() {
        return tsParams;
    }

    public void setTsParams(TextSenderParams tsParams) {
        this.tsParams = tsParams;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public static List<Map<String, Integer>> getSentGroups() {
        return sentGroups;
    }

    public static void setSentGroups(List<Map<String, Integer>> sentGroups) {
        TextSender.sentGroups = sentGroups;
    }

}
