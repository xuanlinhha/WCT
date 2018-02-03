package wct.main;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.util.Map;
import org.apache.commons.lang3.RandomStringUtils;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jcodec.containers.mp4.boxes.MetaValue;
import org.jcodec.movtool.MetadataEditor;

/**
 *
 * @author workshop
 */
public class FileProcessor {

    private static String inputFolder = "C:\\Users\\workshop\\Documents\\WCT\\input";
    private static String outputFolder = "C:\\Users\\workshop\\Documents\\WCT\\output";
    private static int RAMDOM_LENGTH = 50;

    public static void main(String[] args) throws Exception {
//        File folder = new File(inputFolder);
//        File[] files = folder.listFiles();
//        for (File f : files) {
//            System.out.println(f.getAbsoluteFile());
//            Files.copy(Paths.get(f.toURI()), Paths.get(outputFolder + File.separator + 1 + "_" + f.getName()), REPLACE_EXISTING);
//        }
f();
    }

    private static void f() throws Exception {
        File folder = new File(inputFolder);
        File[] files = folder.listFiles();
        for (int i = 0; i < 3; i++) {
            String comment = RandomStringUtils.random(RAMDOM_LENGTH);
            for (File f : files) {
                if (f.isFile() && f.getName().endsWith(".mp3")) {
                    AudioFile af = AudioFileIO.read(f);
                    Tag tag = af.getTag();
                    tag.setField(FieldKey.COMMENT, comment);
                    af.commit();
                } else if (f.isFile() && (f.getName().endsWith(".mp4"))) {
                    MetadataEditor mediaMeta = MetadataEditor.createFrom(f);
                    Map<String, MetaValue> keyedMeta = mediaMeta.getKeyedMeta();
                    keyedMeta.put("comment", MetaValue.createString(comment));
                    mediaMeta.save(true);
                }
            }
            // print md5
            for (File f : files) {
                if (f.isFile() && (f.getName().endsWith(".mp3") || f.getName().endsWith(".mp4"))) {
                    FileInputStream fis = new FileInputStream(f);
                    System.out.println(org.apache.commons.codec.digest.DigestUtils.md5Hex(fis));
                    fis.close();
                }
            }
            System.out.println("---");
        }
    }
}
