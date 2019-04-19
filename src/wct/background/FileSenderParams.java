package wct.background;

import lc.kra.system.keyboard.GlobalKeyboardHook;

/**
 *
 * @author xuanlinhha
 */
public class FileSenderParams extends CommonParams {

    private String inputFolder;
    private int totalGroups;
    private int topGroups;
    private long sendingTime;
    private boolean oneByOne;
    private boolean shutdownAfterFinish;
    private GlobalKeyboardHook keyboardHook;

    public String getInputFolder() {
        return inputFolder;
    }

    public void setInputFolder(String inputFolder) {
        this.inputFolder = inputFolder;
    }

    public int getTotalGroups() {
        return totalGroups;
    }

    public void setTotalGroups(int totalGroups) {
        this.totalGroups = totalGroups;
    }

    public int getTopGroups() {
        return topGroups;
    }

    public void setTopGroups(int topGroups) {
        this.topGroups = topGroups;
    }

    public long getSendingTime() {
        return sendingTime;
    }

    public void setSendingTime(long sendingTime) {
        this.sendingTime = sendingTime;
    }

    public boolean isOneByOne() {
        return oneByOne;
    }

    public void setOneByOne(boolean oneByOne) {
        this.oneByOne = oneByOne;
    }

    public boolean isShutdownAfterFinish() {
        return shutdownAfterFinish;
    }

    public void setShutdownAfterFinish(boolean shutdownAfterFinish) {
        this.shutdownAfterFinish = shutdownAfterFinish;
    }

    public GlobalKeyboardHook getKeyboardHook() {
        return keyboardHook;
    }

    public void setKeyboardHook(GlobalKeyboardHook keyboardHook) {
        this.keyboardHook = keyboardHook;
    }

}
