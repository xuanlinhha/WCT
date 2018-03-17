package wct.background;

import java.text.MessageFormat;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import wct.multilanguage.LanguageHandler;
import wct.resourses.Keyboard;
import wct.resourses.Mouse;
import wct.resourses.SystemClipboard;

/**
 *
 * @author workshop
 */
public class TextSender extends SwingWorker<Void, Void> {

    private static final Long SWITCH_TIME = 1000L;
    private static final Long PAUSE_TIME = 500L;
    private TextSenderParams tsParams;
    private int counter;

    @Override
    protected Void doInBackground() {
        tsParams.getStartJButton().setEnabled(false);
        tsParams.getStopJButton().setEnabled(true);
        topdownSend();
        tsParams.getStartJButton().setEnabled(true);
        tsParams.getStopJButton().setEnabled(false);
        return null;
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

}
