package wct.test;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import wct.resourses.Coordinate;
import wct.resourses.Keyboard;
import wct.resourses.Mouse;

/**
 *
 * @author threenine333
 */
public class DynCapture {

    int red = 197, green = 196, blue = 195;
    Coordinate corner1 = new Coordinate(73, 990);
    Coordinate corner2 = new Coordinate(110, 1024);
    private static final int GRAY_THRESHOLD = 50;
    private static final double DIFF_THRESHOLD = 0.8;

    public static void main(String[] args) throws Exception {
        DynCapture dc = new DynCapture();
        dc.sampleRun();
    }

    private void sampleRun() throws Exception {
        Mouse.getInstance().click(new Coordinate(169, 1054));
        Thread.sleep(1000);

        // load existing data from file
        boolean isDown = true;
        List<Map<String, Integer>> database = new ArrayList<Map<String, Integer>>();
        Map<String, Integer> prev = null;
        while (true) {
            BufferedImage avatar = captureAvatar();
            if (avatar != null) {
                Map<String, Integer> current = extractData(avatar);
                if (prev != null && isSame(current, prev)) {
                    break;
                } else {
                    // check if the new avatar has been processed or not
                    boolean exist = false;
                    for (Map<String, Integer> m : database) {
                        if (isSame(current, m)) {
                            exist = true;
                            break;
                        }
                    }
                    if (!exist) {
                        database.add(current);
                        // processing
                        Keyboard.getInstance().pasteWithEnter();
                        // wait for processing
                        Thread.sleep(1000);
                    }
                }
            }
            if (isDown) {
                Keyboard.getInstance().down(1);
            } else {
                Keyboard.getInstance().up(1);
            }
            Thread.sleep(1000);
        }
        
        // save database
        System.out.println(database.size());
    }

    private BufferedImage captureAvatar() throws Exception {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Robot r = new Robot();
        int len = corner2.getX() - corner1.getX();
        int regionX = corner1.getX() - len / 6;
        int regionY = len;
        int regionWidth = 7 * len / 6;
        int regionHeigth = screenSize.height - len;
        // capture region
        BufferedImage regionImg = r.createScreenCapture(new Rectangle(regionX, regionY, regionWidth, regionHeigth));
        // extract selected image
        int tmpY = 0;
        while (tmpY < regionImg.getHeight()) {
            int clr = regionImg.getRGB(0, tmpY);
            int tmpRed = (clr & 0x00ff0000) >> 16;
            int tmpGreen = (clr & 0x0000ff00) >> 8;
            int tmpBlue = clr & 0x000000ff;
            int diff = Math.abs(tmpRed - red) + Math.abs(tmpGreen - green) + Math.abs(tmpBlue - blue);
            if (diff < GRAY_THRESHOLD) {
                break;
            } else {
                tmpY++;
            }
        }

        int imgX = len / 6;
        int imgY = tmpY + len / 3;
        // if cannot find any selected 
        if (imgY + len > regionImg.getHeight()) {
            return null;
        } else {
            return regionImg.getSubimage(imgX, imgY, len, len);
        }
    }

    private String dataToString(Map<String, Integer> data) {
        StringBuilder sb = new StringBuilder();
        for(String k:data.keySet()){
            sb.append(k);
            sb.append(":");
            sb.append(data.get(k));
            sb.append(" ");
        }
        return sb.toString();
    }
    
    private Map<String, Integer> extractData(BufferedImage img) {
        Map<String, Integer> data = new HashMap<String, Integer>();
        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                int clr = img.getRGB(i, j);
                int red = (clr & 0x00ff0000) >> 16;
                int green = (clr & 0x0000ff00) >> 8;
                int blue = clr & 0x000000ff;
                String key = Integer.toString(red) + Integer.toString(green) + Integer.toString(blue);
                data.put(key, data.containsKey(key) ? (data.get(key) + 1) : 1);
            }
        }
        return data;
    }

    private boolean isSame(Map<String, Integer> data1, Map<String, Integer> data2) {
        double count = 0;
        for (String s : data2.keySet()) {
            if (data1.containsKey(s)) {
                count += (int) Math.min(data1.get(s), data2.get(s));
            }
        }
        double total = 0;
        for (int i : data1.values()) {
            total += i;
        }
        DecimalFormat df = new DecimalFormat("#.##");
        return (Double.valueOf(df.format(count / total)) >= DIFF_THRESHOLD);
    }

    private void printColor() throws Exception {
        Mouse.getInstance().click(new Coordinate(169, 1054));
        Thread.sleep(1000);

        Robot r = new Robot();
        while (true) {
            Color tmp = r.getPixelColor(0, 0);
            System.out.println("Color = " + tmp);

        }
    }
}
