package wct.resourses;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

/**
 *
 * @author xuanlinhha
 */
public class Screen {

    private static final int GRAY_THRESHOLD = 50;
    private static final double DIFF_THRESHOLD = 0.8;
    private Robot r;
    private static Screen instance;

    private Screen() {
    }

    public static Screen getInstance() throws AWTException {
        if (instance == null) {
            instance = new Screen();
            instance.r = new Robot();
        }
        return instance;
    }

    public BufferedImage captureAvatar(Color selectedColor, Coordinate c1, Coordinate c2) throws Exception {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int len = c2.getX() - c1.getX();
        int regionX = c1.getX() - len / 6;
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
            int diff = Math.abs(tmpRed - selectedColor.getRed()) + Math.abs(tmpGreen - selectedColor.getGreen()) + Math.abs(tmpBlue - selectedColor.getBlue());
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

    public Map<String, Integer> extractData(BufferedImage img) {
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

}
