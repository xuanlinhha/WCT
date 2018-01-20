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
    private int nextGroup;
    private int noOfFiles;

    public void copy() {
        List<File> selectedFiles = new ArrayList<File>();
        int selectedFile = nextGroup * noOfFiles;
        for (int i = 0; i < noOfFiles; i++) {
            if (selectedFile < files.length) {
                selectedFiles.add(files[selectedFile]);
                selectedFile++;
            }
        }
        nextGroup++;
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

    public int getNextGroup() {
        return nextGroup;
    }

    public void setNextGroup(int nextGroup) {
        this.nextGroup = nextGroup;
    }

    public int getNoOfFiles() {
        return noOfFiles;
    }

    public void setNoOfFiles(int noOfFiles) {
        this.noOfFiles = noOfFiles;
    }

}
