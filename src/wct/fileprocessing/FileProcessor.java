package wct.fileprocessing;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author xuanlinhha
 */
public class FileProcessor {

    public static void changeHashcode(String inputFolder, String randString) {
        try {
            File folder = new File(inputFolder);
            File[] files = folder.listFiles();
            for (File f : files) {
                if (f.isFile() && (f.getName().endsWith(".mp3"))) {
                    Mp3Meta.changeComment(f, randString);
                } else if (f.isFile() && f.getName().endsWith(".mp4")) {
                    Mp4Meta.writeRandomMetadata(f, randString);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static List<File> getFiles(String inputFolder) {
        File folder = new File(inputFolder);
        File[] files = folder.listFiles();
        List<File> selectedFiles = new ArrayList<File>();
        Arrays.sort(files, new WEFileComparator());
        for (File f : files) {
            if (f.isFile() && (f.getName().endsWith(".mp3") || f.getName().endsWith(".mp4"))) {
                selectedFiles.add(f);
            }
        }
        return selectedFiles;
    }

    public static void copyToOutput(int iterator, String inputFolder, String outputFolder) {
        try {
            File folder = new File(inputFolder);
            File[] files = folder.listFiles();
            for (File f : files) {
                if (f.isFile() && (f.getName().endsWith(".mp3") || f.getName().endsWith(".mp4"))) {
                    Files.copy(Paths.get(f.toURI()), Paths.get(outputFolder + File.separator + iterator + "_" + f.getName()), REPLACE_EXISTING);
                }
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
