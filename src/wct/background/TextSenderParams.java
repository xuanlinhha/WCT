package wct.background;

/**
 *
 * @author xuanlinhha
 */
public class TextSenderParams extends CommonParams {

    private String text;
    private int noOfGroups;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getNoOfGroups() {
        return noOfGroups;
    }

    public void setNoOfGroups(int noOfGroups) {
        this.noOfGroups = noOfGroups;
    }

}
