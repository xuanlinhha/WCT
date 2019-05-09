package wct.fileprocessing;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author xuanlinhha
 */
public class FileProcessor {

    public static void changeFilesHashcode(String inputFolder, String randString) throws IOException {
        File folder = new File(inputFolder);
        File[] files = folder.listFiles();
        for (File f : files) {
            if (f.isFile() && (f.getName().endsWith(".mp3") || f.getName().endsWith(".MP3"))) {
                Mp3Meta.changeComment(f, randString);
            } else if (f.isFile() && (f.getName().endsWith(".mp4") || f.getName().endsWith(".MP4"))) {
                Mp4Meta.writeRandomMetadata(f, randString);
            }
        }
    }

    public static void changeFileHashcode(File f, String randString) throws IOException {
        if (f.isFile() && (f.getName().endsWith(".mp3") || f.getName().endsWith(".MP3"))) {
            Mp3Meta.changeComment(f, randString);
        } else if (f.isFile() && (f.getName().endsWith(".mp4") || f.getName().endsWith(".MP4"))) {
            Mp4Meta.writeRandomMetadata(f, randString);
        }
    }

    public static List<File> getFiles(String inputFolder) {
        File folder = new File(inputFolder);
        File[] files = folder.listFiles();
        List<File> selectedFiles = new ArrayList<File>();
        Arrays.sort(files, new WEFileComparator());
        for (File f : files) {
            if (f.isFile() && (f.getName().endsWith(".mp3") || f.getName().endsWith(".mp4")
                    || f.getName().endsWith(".MP3") || f.getName().endsWith(".MP4"))) {
                selectedFiles.add(f);
            }
        }
        return selectedFiles;
    }

    public static void copyToOutput(int iterator, String inputFolder, String outputFolder) throws IOException {
        File folder = new File(inputFolder);
        File[] files = folder.listFiles();
        for (File f : files) {
            if (f.isFile() && (f.getName().endsWith(".mp3") || f.getName().endsWith(".mp4")
                    || f.getName().endsWith(".MP3") || f.getName().endsWith(".MP4"))) {
                Files.copy(Paths.get(f.toURI()), Paths.get(outputFolder + File.separator + iterator + "_" + f.getName()), REPLACE_EXISTING);
            }
        }
    }

    public static void cleanVideoFolder(String root) {
        File[] files = new File(root).listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.isDirectory() && !pathname.getName().equals("All Users");
            }
        });
        for (File f : files) {
            List<Path> paths = new ArrayList<>();
            Path p1 = Paths.get(f.getAbsolutePath() + File.separator + "Video");
            paths.add(p1);
            Path p2 = Paths.get(f.getAbsolutePath() + File.separator + "FileStorage" + File.separator + "Video");
            paths.add(p2);
            try {
                for (Path p : paths) {
                    if (Files.exists(p) && Files.isDirectory(p)) {
                        FileUtils.cleanDirectory(p.toFile());
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

}
