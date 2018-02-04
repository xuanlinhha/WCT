package wct.fileprocessing;

import java.awt.Toolkit;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author xuanlinhha
 */
public class FileProcessor {

    public static void changeHashcode(String kid3Path, String inputFolder, String randString) {
        try {
            File folder = new File(inputFolder);
            File[] files = folder.listFiles();

            for (File f : files) {
                if (f.isFile() && (f.getName().endsWith(".mp3") || f.getName().endsWith(".mp4"))) {
                    Process p = Runtime.getRuntime().exec(new String[]{kid3Path, "-c", "set comment " + randString, f.getAbsolutePath()});
                    p.waitFor();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void copy(String inputFolder) {
        try {
            File folder = new File(inputFolder);
            File[] files = folder.listFiles();
            List<File> selectedFiles = new ArrayList<File>();
            Arrays.sort(files, new WEFileComparator());
            for (File f : files) {
                if (f.isFile() && (f.getName().endsWith(".mp3") || f.getName().endsWith(".mp4"))) {
                    selectedFiles.add(f);
                }
            }
            FileSelection fs = new FileSelection();
            fs.setFiles(Arrays.asList(files));
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(fs, fs);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
