package wct.background;

import lc.kra.system.keyboard.GlobalKeyboardHook;

/**
 *
 * @author xuanlinhha
 */
public class FileSenderParams extends CommonParams {

    private String inputFolder;
    private int noOfGroups;
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

    public int getNoOfGroups() {
        return noOfGroups;
    }

    public void setNoOfGroups(int noOfGroups) {
        this.noOfGroups = noOfGroups;
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
