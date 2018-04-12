package wct.background;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
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

    private static final Long SWITCH_TIME = 1000L;
    private static final Long PAUSE_TIME = 500L;
    private static final Long CLICK_WAITING = 1000L;
    private static final Long GO_TOP_WAITING = 2000L;
    private static final Integer SCROLL_TIMES = 10;
    private static final String SENT_TEXT_GROUPS = "sent_groups_TEXT.txt";
    private TextSenderParams tsParams;
    private int counter;
    private static Set<String> sentGroups = new HashSet<String>();

    @Override
    protected Void doInBackground() {
        tsParams.getStartJButton().setEnabled(false);
        tsParams.getStopJButton().setEnabled(true);
        if (tsParams.getImageRecognitionJCheckBox().isSelected()) {
            bottomUpSendWithImageRecognition();
        } else {
            bottomUpSend();
        }
        tsParams.getStartJButton().setEnabled(true);
        tsParams.getStopJButton().setEnabled(false);
        return null;
    }

    private void bottomUpSend() {
        ResourceBundle bundle = LanguageHandler.getInstance().getBundle();
        try {
            Mouse.getInstance().click(tsParams.getOnTaskbarCoordinate());
            SystemClipboard.getInstance().copyString(tsParams.getText());
            Thread.sleep(SWITCH_TIME);
            counter = 0;
            while (counter < tsParams.getNoOfGroups()) {
                Mouse.getInstance().press(tsParams.getScrollingCoordinate(), tsParams.getScrollingTime());
                Mouse.getInstance().scrollDown(SCROLL_TIMES);
                Mouse.getInstance().click(tsParams.getImageCoordinate1());
                Thread.sleep(CLICK_WAITING);
                Keyboard.getInstance().pasteWithEnter();
                counter++;
                if (isCancelled()) {
                    break;
                }
            }
            JOptionPane.showMessageDialog(null, MessageFormat.format(bundle.getString("result_message"), tsParams.getNoOfGroups()), bundle.getString("result_title"), JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void bottomUpSendWithImageRecognition() {
        ResourceBundle bundle = LanguageHandler.getInstance().getBundle();
        try {
            Screen sc = Screen.getInstance();
            List<Coordinate> coors = new ArrayList<Coordinate>();
            coors.add(tsParams.getImageCoordinate1());
            coors.add(tsParams.getImageCoordinate2());
            sc.setPositions(coors);
            // clear if start from beginning
            if (tsParams.getOptionJComboBox().getSelectedIndex() == 0) { // from beginning
                sentGroups.clear();
            } else {
                sentGroups = TextReaderWriter.loadSentFileGroups(SENT_TEXT_GROUPS);
            }

            // click to WeChat app
            Mouse.getInstance().click(tsParams.getOnTaskbarCoordinate());

            counter = 0;
            while (counter < tsParams.getNoOfGroups()) {
                Mouse.getInstance().press(tsParams.getScrollingCoordinate(), tsParams.getScrollingTime());
                Mouse.getInstance().scrollDown(SCROLL_TIMES);
                Mouse.getInstance().click(tsParams.getImageCoordinate1());
                Thread.sleep(CLICK_WAITING);
                // identify group is sent or not
                boolean isAdded = false;
                boolean exceedLimit = false;
                int notSendingCounter = 0;
                String color = "";
                while (true) {
                    if (isCancelled()) {
                        break;
                    }
                    color = sc.getColorData();
                    if (sentGroups.contains(color)) {
                        notSendingCounter++;
                        if (notSendingCounter == tsParams.getLimitToStop()) {
                            exceedLimit = true;
                            break;
                        }
                        SystemClipboard.getInstance().copyString("-");
                        Keyboard.getInstance().pasteWithoutEnter();
                        Mouse.getInstance().click(tsParams.getSecondLastCoordinate());
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
                if (exceedLimit) {
                    break;
                }
                // send text
                SystemClipboard.getInstance().copyString(tsParams.getText());
                Keyboard.getInstance().pasteWithEnter();
                counter++;
            }
            JOptionPane.showMessageDialog(null, MessageFormat.format(bundle.getString("result_message"), sentGroups.size()), bundle.getString("result_title"), JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            TextReaderWriter.saveSentFileGroups(SENT_TEXT_GROUPS, sentGroups);
            tsParams.getOptionJComboBox().setSelectedIndex(1);
        }
    }

    private void topdownSend() {
        ResourceBundle bundle = LanguageHandler.getInstance().getBundle();
        try {
            Mouse.getInstance().click(tsParams.getOnTaskbarCoordinate());
            SystemClipboard.getInstance().copyString(tsParams.getText());
            Thread.sleep(SWITCH_TIME);
            counter = 0;
            while (counter < tsParams.getNoOfGroups()) {
                Keyboard.getInstance().pasteWithEnter();
                Thread.sleep(PAUSE_TIME);
                Keyboard.getInstance().down(1);
                Thread.sleep(PAUSE_TIME);
                counter++;
                if (isCancelled()) {
                    break;
                }
            }
            JOptionPane.showMessageDialog(null, MessageFormat.format(bundle.getString("result_message"), tsParams.getNoOfGroups()), bundle.getString("result_title"), JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
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

    public static Set<String> getSentGroups() {
        return sentGroups;
    }

    public static void setSentGroups(Set<String> sentGroups) {
        TextSender.sentGroups = sentGroups;
    }

}
