package wct.invcopy;

import java.awt.Toolkit;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author xuanlinhha
 */
public class InvCopier {

    private File[] files;
    private int next;
    private int no;

    public void copy() {
        List<File> selectedFiles = new ArrayList<File>();
        for (int i = 0; i < no; i++) {
            if (i < files.length) {
                selectedFiles.add(files[next]);
                next++;
            }
        }
        FileSelection fs = new FileSelection();
        fs.setFiles(selectedFiles);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(fs, fs);
    }

    public File[] getFiles() {
        return files;
    }

    public void setFiles(File[] files) {
        this.files = files;
    }

    public int getNext() {
        return next;
    }

    public void setNext(int next) {
        this.next = next;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }
    
}
