package wct.background;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
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
public class CopiesGenerator extends SwingWorker<Void, Void> {

    private static final int RAMDOM_LENGTH = 20;

    // gui
    private JFrame parent;
    private JButton generateButton;

    // task
    private int noOfCopies;
    private String inputFolder;
    private String outputFolder;

    @Override
    protected Void doInBackground() throws Exception {
        long startTime = System.currentTimeMillis();
        generateButton.setEnabled(false);
        File folder = new File(inputFolder);
        File[] files = folder.listFiles();

        for (int i = 1; i <= noOfCopies; i++) {
            String randString = RandomStringUtils.random(RAMDOM_LENGTH) + i;
            for (File f : files) {
                // change hash code
                if (f.isFile() && f.getName().endsWith(".mp3")) {
                    AudioFile mp3 = AudioFileIO.read(f);
                    Tag tag = mp3.getTag();
                    tag.setField(FieldKey.COMMENT, randString);
                    mp3.commit();
                } else if (f.isFile() && (f.getName().endsWith(".mp4"))) {
                    MetadataEditor mediaMeta = MetadataEditor.createFrom(f);
                    Map<String, MetaValue> keyedMeta = mediaMeta.getKeyedMeta();
                    keyedMeta.put("comment", MetaValue.createString(randString));
                    mediaMeta.save(true);
                }
                // copy
                Files.copy(Paths.get(f.toURI()), Paths.get(outputFolder + File.separator + i + "_" + f.getName()), REPLACE_EXISTING);
            }
            if (isCancelled()) {
                break;
            }
        }
        System.out.println("Here ---");
        if (!isCancelled()) {
            long runningTime = System.currentTimeMillis() - startTime;
            JOptionPane.showMessageDialog(parent, "Time: " + runningTime, "Success", JOptionPane.INFORMATION_MESSAGE);
        }
        generateButton.setEnabled(true);
        return null;
    }

    public JFrame getParent() {
        return parent;
    }

    public void setParent(JFrame parent) {
        this.parent = parent;
    }

    public JButton getGenerateButton() {
        return generateButton;
    }

    public void setGenerateButton(JButton generateButton) {
        this.generateButton = generateButton;
    }

    public int getNoOfCopies() {
        return noOfCopies;
    }

    public void setNoOfCopies(int noOfCopies) {
        this.noOfCopies = noOfCopies;
    }

    public String getInputFolder() {
        return inputFolder;
    }

    public void setInputFolder(String inputFolder) {
        this.inputFolder = inputFolder;
    }

    public String getOutputFolder() {
        return outputFolder;
    }

    public void setOutputFolder(String outputFolder) {
        this.outputFolder = outputFolder;
    }

}
