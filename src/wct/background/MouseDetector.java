package wct.background;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.util.List;
import javax.swing.SwingWorker;

/**
 *
 * @author xuanlinhha
 */
public class MouseDetector extends SwingWorker<Void, String> {

    private MouseDetectorParams mdParams;

    @Override
    protected Void doInBackground() {
        mdParams.getStartJButton().setEnabled(false);
        mdParams.getStopJButton().setEnabled(true);
        try {
            mdParams.getjTextArea().setText("");
            while (!isCancelled()) {
                PointerInfo pi = MouseInfo.getPointerInfo();
                Point p = pi.getLocation();
                String s = p.x + " " + p.y + "\n";
                publish(s);
                Thread.sleep(1000);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            mdParams.getStartJButton().setEnabled(true);
            mdParams.getStopJButton().setEnabled(false);
        }
        return null;
    }

    @Override
    protected void process(List<String> ss) {
        if (!ss.isEmpty()) {
            this.mdParams.getjTextArea().append(ss.get(ss.size() - 1));
        }
    }

    public MouseDetectorParams getMdParams() {
        return mdParams;
    }

    public void setMdParams(MouseDetectorParams mdParams) {
        this.mdParams = mdParams;
    }
}
