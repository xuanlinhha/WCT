package wct.fileprocessing;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import wct.background.FileSender;

/**
 *
 * @author xuanlinhha
 */
public class TextReaderWriter {

    public static Set<String> loadSentFileGroups(String fileName) {
        try {
            List<String> lines = FileUtils.readLines(new File(fileName), "UTF-8");
            return new HashSet<>(lines);
        } catch (IOException ex) {
            Logger.getLogger(FileSender.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new HashSet<>();
    }

    public static void saveSentFileGroups(String fileName, Collection<String> sc) {
        try {
            FileUtils.writeLines(new File(fileName), "UTF-8", sc);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
