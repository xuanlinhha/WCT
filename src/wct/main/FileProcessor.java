package wct.main;

import java.io.File;
import java.io.FileInputStream;
import org.apache.commons.lang3.RandomStringUtils;

/**
 *
 * @author workshop
 */
public class FileProcessor {

    private static String inputFolder = "C:\\Users\\workshop\\Documents\\WCT\\input";
    private static String outputFolder = "C:\\Users\\workshop\\Documents\\WCT\\output";
    private static int RAMDOM_LENGTH = 50;

    public static void main(String[] args) throws Exception {
        f();
    }

    private static void f() throws Exception {
        File folder = new File(inputFolder);
        File[] files = folder.listFiles();
        for (int i = 0; i < 3; i++) {
            String randString = RandomStringUtils.random(RAMDOM_LENGTH);
            for (File f : files) {
                if (f.isFile() && f.getName().endsWith(".mp3")) {
                } else if (f.isFile() && (f.getName().endsWith(".mp4"))) {
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
