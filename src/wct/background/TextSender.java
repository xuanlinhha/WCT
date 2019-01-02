package wct.background;

import java.awt.image.BufferedImage;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import wct.fileprocessing.TextReaderWriter;
import wct.multilanguage.LanguageHandler;
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

            // click to WeChat app
            Mouse.getInstance().click(tsParams.getOnTaskbarCoordinate());
            Thread.sleep(SWITCH_TIME);

            // run
            Map<String, Integer> prev = null;
            boolean isDown = true;
            counter = 0;
            while (counter < tsParams.getNoOfGroups()) {
                if (isCancelled()) {
                    break;
                }
                BufferedImage avatar = Screen.getInstance().captureAvatar(tsParams.getSelectedColor(), tsParams.getImageCoordinate1(), tsParams.getImageCoordinate2());

                if (avatar != null) {
                    Map<String, Integer> current = Screen.getInstance().extractData(avatar);
                    if (prev != null && Screen.getInstance().isSame(current, prev)) {
                        break;
                    } else {
                        // check if the new avatar has been processed or not
                        boolean exist = false;
                        for (Map<String, Integer> m : sentGroups) {
                            if (Screen.getInstance().isSame(current, m)) {
                                exist = true;
                                break;
                            }
                        }
                        if (!exist) {
                            // send text
                            SystemClipboard.getInstance().copyString(tsParams.getText());
                            Keyboard.getInstance().pasteWithEnter();
                            counter++;
                            sentGroups.add(current);
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
