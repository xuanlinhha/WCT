package wct.configuration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author xuanlinhha
 */
public class ConfigurationHandler {

    private static String CONFIGURATION_PATH = "config.json";
    private static Configuration config;

    public static void save(Configuration config) throws IOException {
        ConfigurationHandler.config = config;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        FileUtils.write(new File(CONFIGURATION_PATH), gson.toJson(config), "UTF-8");
    }

    public static void loadConfig() throws IOException {
        File f = new File(CONFIGURATION_PATH);
        Gson gson = new Gson();
        config = gson.fromJson(FileUtils.readFileToString(f, "UTF-8"), Configuration.class);
    }

    public static Configuration getConfig() throws IOException {
        if (config == null) {
            loadConfig();
        }
        return config;
    }
}
