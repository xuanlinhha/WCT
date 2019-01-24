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

            // initial data
            Screen screen = new Screen(tsParams);
            counter = 0;
            int len = tsParams.getCorner2().getX() - tsParams.getCorner1().getX();
            Coordinate top1 = new Coordinate(tsParams.getCorner2().getX(), tsParams.getCorner1().getY() + len);
            Coordinate top2 = new Coordinate(tsParams.getCorner2().getX(), tsParams.getCorner1().getY());

            // click on wechat & select the top
            Mouse.getInstance().click(tsParams.getOnTaskbarCoordinate());
            Thread.sleep(1000);
            Mouse.getInstance().click(top1);
            Thread.sleep(500);
            Mouse.getInstance().click(top2);
            Thread.sleep(1000);

//            // run
//            counter = 0;
//            while (counter < tsParams.getNoOfGroups()) {
//                if (isCancelled()) {
//                    break;
//                }
//                // find unsent group
//                Coordinate unsentGroup = screen.getFirstUnsentGroup(sentGroups);
//
//                if (unsentGroup == null) {
//                    // check to stop
//                    if (!screen.isBatchChanged()) {
//                        break;
//                    } else {
//                        Mouse.getInstance().click(tsParams.getScrollingCoordinate());
//                        Thread.sleep(1000);
//                        Mouse.getInstance().click(top1);
//                        Thread.sleep(500);
//                        Mouse.getInstance().click(top2);
//                        Thread.sleep(1000);
//                    }
//                } else {
//                    // reset down count
//                    Mouse.getInstance().click(unsentGroup);
//                    Thread.sleep(1000);
//                    // send text
//                    SystemClipboard.getInstance().copyString(tsParams.getText());
//                    Keyboard.getInstance().pasteWithEnter();
//                    counter++;
//                }
//            }
            tsParams.getKeyboardHook().shutdownHook();
            JOptionPane.showMessageDialog(null, MessageFormat.format(bundle.getString("result_message"), sentGroups.size()), bundle.getString("result_title"), JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            TextReaderWriter.saveSentFileGroups(SENT_TEXT_GROUPS, sentGroups);
            tsParams.getOptionJComboBox().setSelectedIndex(1);
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
