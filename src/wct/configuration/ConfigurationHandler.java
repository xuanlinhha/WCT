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

    public static void save(Configuration config) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        FileUtils.write(new File(CONFIGURATION_PATH), gson.toJson(config), "UTF-8");
    }

    public static Configuration loadConfig() throws IOException {
        File f = new File(CONFIGURATION_PATH);
        Gson gson = new Gson();
        Configuration config = gson.fromJson(FileUtils.readFileToString(f, "UTF-8"), Configuration.class);
        return config;
    }
}
