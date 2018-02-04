package wct.background;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.util.List;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

/**
 *
 * @author xuanlinhha
 */
public class MouseDetector extends SwingWorker<Void, String> {

    private JTextArea positionJTextArea;

    public JTextArea getPositionJTextArea() {
        return positionJTextArea;
    }

    public void setPositionJTextArea(JTextArea positionJTextArea) {
        this.positionJTextArea = positionJTextArea;
    }

    @Override
    protected Void doInBackground() {
        try {

            System.out.println("Background task is running ...");
            while (!isCancelled()) {
                PointerInfo pi = MouseInfo.getPointerInfo();
                Point p = pi.getLocation();
                String s = p.x + " " + p.y + "\n";
                publish(s);
                Thread.sleep(1000);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    protected void process(List<String> ss) {
        if (!ss.isEmpty()) {
            this.positionJTextArea.append(ss.get(ss.size() - 1));
        }
    }
}
