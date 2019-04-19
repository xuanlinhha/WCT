package wct.background;

import lc.kra.system.keyboard.GlobalKeyboardHook;

/**
 *
 * @author xuanlinhha
 */
public class TextSenderParams extends CommonParams {

    private String text;
    private int totalGroups;
    private int topGroups;
    private GlobalKeyboardHook keyboardHook;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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

    public GlobalKeyboardHook getKeyboardHook() {
        return keyboardHook;
    }

    public void setKeyboardHook(GlobalKeyboardHook keyboardHook) {
        this.keyboardHook = keyboardHook;
    }

}
