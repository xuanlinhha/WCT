package wct.background;

import lc.kra.system.keyboard.GlobalKeyboardHook;

/**
 *
 * @author xuanlinhha
 */
public class FileSenderParams extends CommonParams {

    private String inputFolder;
    private int downTimes;
    private int upTimes;
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

    public int getDownTimes() {
        return downTimes;
    }

    public void setDownTimes(int downTimes) {
        this.downTimes = downTimes;
    }

    public int getUpTimes() {
        return upTimes;
    }

    public void setUpTimes(int upTimes) {
        this.upTimes = upTimes;
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
