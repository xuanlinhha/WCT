package wct.fileprocessing;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import wct.background.FileSender;

/**
 *
 * @author xuanlinhha
 */
public class TextReaderWriter {

    public static List<Map<String, Integer>> loadSentFileGroups(String fileName) {
        List<Map<String, Integer>> savedGroups = new ArrayList<Map<String, Integer>>();
        try {
            List<String> lines = FileUtils.readLines(new File(fileName), "UTF-8");
            for (String line : lines) {
                Map<String, Integer> data = new HashMap<String, Integer>();
                String[] colors = line.split(" ");
                for (String color : colors) {
                    String[] comps = color.split(":");
                    data.put(comps[0], Integer.parseInt(comps[1]));
                }
                savedGroups.add(data);
            }
        } catch (IOException ex) {
            Logger.getLogger(FileSender.class.getName()).log(Level.SEVERE, null, ex);
        }
        return savedGroups;
    }

    public static void saveSentFileGroups(String fileName, List<Map<String, Integer>> sentGroups) {
        try {
            List<String> data = new ArrayList<String>();
            for (Map<String, Integer> m : sentGroups) {
                StringBuilder sb = new StringBuilder();
                for (String k : m.keySet()) {
                    sb.append(k);
                    sb.append(":");
                    sb.append(m.get(k));
                    sb.append(" ");
                }
                data.add(sb.toString());
            }
            FileUtils.writeLines(new File(fileName), "UTF-8", data);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
