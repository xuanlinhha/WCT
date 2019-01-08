package wct.resourses;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import wct.background.CommonParams;

/**
 *
 * @author xuanlinhha
 */
public class Screen {

    private static final double DIFF_THRESHOLD = 0.8;
    private CommonParams cp;
    private Robot r;
    private List<Map<String, Integer>> prev;
    private List<Map<String, Integer>> current;

    public Screen(CommonParams cp) throws AWTException {
        this.cp = cp;
        this.r = new Robot();
        this.prev = null;
        this.current = null;
    }

    public Coordinate getFirstUnsentGroup(List<Map<String, Integer>> sentGroups) throws IOException {
        Coordinate c1 = cp.getImageCoordinate1(), c2 = cp.getImageCoordinate2();
        int len = c2.getX() - c1.getX();
        int regionHeigth = c2.getY() - c1.getY();
        BufferedImage regionImg = r.createScreenCapture(new Rectangle(c1.getX(), c1.getY(), len, regionHeigth));
        float space = (float) (regionHeigth - cp.getGroupsInRegion() * len) / (cp.getGroupsInRegion() - 1);

        // reset current
        prev = current;
        current = new ArrayList<Map<String, Integer>>();
        for (int i = 0; i < cp.getGroupsInRegion(); i++) {
            int tmpY = Math.round((len + space) * i);
            BufferedImage avatar = regionImg.getSubimage(0, tmpY, len, len);
            Map<String, Integer> data = extractData(avatar);
            current.add(data);

            // check sent or not
            boolean isSent = false;
            for (Map<String, Integer> m : sentGroups) {
                if (isSame(data, m)) {
                    isSent = true;
                    break;
                }
            }
            if (!isSent) {
                sentGroups.add(data);
                // return unsent coordinate
                Coordinate coor = new Coordinate();
                coor.setX(c1.getX());
                coor.setY(c1.getX() + tmpY);
                return coor;
            }
        }
        return null;
    }

    public BufferedImage captureRegion(Coordinate c1, Coordinate c2) {
        int len = c2.getX() - c1.getX();
        int regionHeigth = c2.getY() - c1.getY();
        BufferedImage regionImg = r.createScreenCapture(new Rectangle(c1.getX(), c1.getY(), len, regionHeigth));
        return regionImg;
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

    public boolean isSame(Map<String, Integer> data1, Map<String, Integer> data2) {
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

    public void saveImg(BufferedImage image, String name) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", baos);
        ImageIO.write(image, "jpg", baos);
        ImageIO.write(image, "jpg", new File(name + ".jpg"));
    }

    public boolean isBatchChanged() {
        if (prev == null) {
            return true;
        }
        if (current.size() != prev.size()) {
            return true;
        }
        for (int i = 0; i < prev.size(); i++) {
            if (!isSame(prev.get(i), current.get(i))) {
                return true;
            }
        }
        return false;
    }

}
