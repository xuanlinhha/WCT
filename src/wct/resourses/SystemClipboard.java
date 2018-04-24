package wct.resourses;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author xuanlinhha
 */
public class SystemClipboard {

    private static SystemClipboard instance;

    private SystemClipboard() {
    }

    public static SystemClipboard getInstance() {
        if (instance == null) {
            instance = new SystemClipboard();
        }
        return instance;
    }

    public void copyFiles(List<File> files) {
        FileSelection fs = new FileSelection();
        fs.setFiles(files);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(fs, fs);
    }

    public void copyFile(File file) {
        FileSelection fs = new FileSelection();
        fs.setFiles(Arrays.asList(file));
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(fs, fs);
    }

    public void copyString(String s) {
        StringSelection stringSelection = new StringSelection(s);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(
                stringSelection, null);
    }

    public void clear() {
        StringSelection stringSelection = new StringSelection("");
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(
                stringSelection, null);
    }
}
