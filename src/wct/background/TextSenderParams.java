package wct.background;

import lc.kra.system.keyboard.GlobalKeyboardHook;

/**
 *
 * @author xuanlinhha
 */
public class TextSenderParams extends CommonParams {

    private String text;
    private int downTimes;
    private int upTimes;
    private GlobalKeyboardHook keyboardHook;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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
    
    public GlobalKeyboardHook getKeyboardHook() {
        return keyboardHook;
    }

    public void setKeyboardHook(GlobalKeyboardHook keyboardHook) {
        this.keyboardHook = keyboardHook;
    }

}
