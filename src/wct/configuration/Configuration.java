package wct.configuration;

import java.util.List;

/**
 *
 * @author xuanlinhha
 */
public class Configuration {

    private String inputFolder;
    private String outputFolder;

    private String onTaskbar;
    private String scroll;
    private Long scrollTime;
    private String alternativeMsg;
    private List<String> imagePositions;

    private Long kbPRWaiting;
    private Long kbFinishWaiting;
    private Long mousePRWaiting;
    private Long mouseMoveWaiting;

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

    public String getOnTaskbar() {
        return onTaskbar;
    }

    public void setOnTaskbar(String onTaskbar) {
        this.onTaskbar = onTaskbar;
    }

    public List<String> getImagePositions() {
        return imagePositions;
    }

    public void setImagePositions(List<String> imagePositions) {
        this.imagePositions = imagePositions;
    }

    public String getScroll() {
        return scroll;
    }

    public void setScroll(String scroll) {
        this.scroll = scroll;
    }

    public Long getScrollTime() {
        return scrollTime;
    }

    public void setScrollTime(Long scrollTime) {
        this.scrollTime = scrollTime;
    }

    public Long getKbPRWaiting() {
        return kbPRWaiting;
    }

    public void setKbPRWaiting(Long kbPRWaiting) {
        this.kbPRWaiting = kbPRWaiting;
    }

    public Long getKbFinishWaiting() {
        return kbFinishWaiting;
    }

    public void setKbFinishWaiting(Long kbFinishWaiting) {
        this.kbFinishWaiting = kbFinishWaiting;
    }

    public Long getMousePRWaiting() {
        return mousePRWaiting;
    }

    public void setMousePRWaiting(Long mousePRWaiting) {
        this.mousePRWaiting = mousePRWaiting;
    }

    public Long getMouseMoveWaiting() {
        return mouseMoveWaiting;
    }

    public void setMouseMoveWaiting(Long mouseMoveWaiting) {
        this.mouseMoveWaiting = mouseMoveWaiting;
    }

    public String getAlternativeMsg() {
        return alternativeMsg;
    }

    public void setAlternativeMsg(String alternativeMsg) {
        this.alternativeMsg = alternativeMsg;
    }

}
