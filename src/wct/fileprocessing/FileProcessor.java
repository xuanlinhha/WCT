package wct.fileprocessing;

import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jcodec.containers.mp4.boxes.MetaValue;
import org.jcodec.movtool.MetadataEditor;

/**
 *
 * @author xuanlinhha
 */
public class FileProcessor {

    public static void changeHashcodeAndCopy(String inputFolder, String randString) throws Exception {
        List<File> selectedFiles = new ArrayList<File>();
        File folder = new File(inputFolder);
        File[] files = folder.listFiles();
        Arrays.sort(files, new WEFileComparator());
        for (File f : files) {
            if (f.isFile() && f.getName().endsWith(".mp3")) {
                AudioFile mp3 = AudioFileIO.read(f);
                Tag tag = mp3.getTag();
                tag.setField(FieldKey.COMMENT, randString);
                mp3.commit();
                selectedFiles.add(f);
            } else if (f.isFile() && (f.getName().endsWith(".mp4"))) {
                MetadataEditor mediaMeta = MetadataEditor.createFrom(f);
                Map<String, MetaValue> keyedMeta = mediaMeta.getKeyedMeta();
                keyedMeta.put("comment", MetaValue.createString(randString));
                mediaMeta.save(true);
                selectedFiles.add(f);
            }
        }
        // copy  to clipboard
        FileSelection fs = new FileSelection();
        fs.setFiles(selectedFiles);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(fs, fs);
    }
}
