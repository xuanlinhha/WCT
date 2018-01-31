package wct.configuration;

/**
 *
 * @author xuanlinhha
 */
public class Configuration {

    private String kid3Path;
    private String inputFolder;
    private String outputFolder;

    private String onTaskbar;
    private String lastHistory;
    private String scroll;
    private Long scrollTime;

    private Long kbPRWaiting;
    private Long kbFinishWaiting;
    private Long mousePRWaiting;
    private Long mouseMoveWaiting;

    public String getKid3Path() {
        return kid3Path;
    }

    public void setKid3Path(String kid3Path) {
        this.kid3Path = kid3Path;
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

    public String getOnTaskbar() {
        return onTaskbar;
    }

    public void setOnTaskbar(String onTaskbar) {
        this.onTaskbar = onTaskbar;
    }

    public String getLastHistory() {
        return lastHistory;
    }

    public void setLastHistory(String lastHistory) {
        this.lastHistory = lastHistory;
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

}
