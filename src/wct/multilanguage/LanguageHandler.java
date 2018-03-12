package wct.multilanguage;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 *
 * @author xuanlinhha
 */
public class LanguageHandler {

    private ResourceBundle bundle;
    private static LanguageHandler instance;

    private LanguageHandler() {
    }

    public static LanguageHandler getInstance() {
        if (instance == null) {
            instance = new LanguageHandler();
            instance.bundle = ResourceBundle.getBundle("wct/Message", Locale.getDefault());
        }
        return instance;
    }

    public ResourceBundle getBundle() {
        return bundle;
    }

    public void setBundle(ResourceBundle bundle) {
        this.bundle = bundle;
    }
    
}
