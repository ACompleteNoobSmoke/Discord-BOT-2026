package config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

public class BotConfig {

    private static final Logger LOGGER = Logger.getLogger(BotConfig.class.getName());
    private static final String CONFIG_FILE_PATH = "config.properties";
    private static final Properties properties = new Properties();

    static {
        try(InputStream inputStream = BotConfig.class.getClassLoader().getResourceAsStream(CONFIG_FILE_PATH)) {
            if (inputStream == null) LOGGER.warning("Configuration file {} not found!");
            else {
                properties.load(inputStream);
                LOGGER.info("Configuration file {} loaded successfully");
            }
        } catch(IOException ex) {
            LOGGER.warning("Failed to load configuration file: {}");
        }
    }

    public static String getBOTToken() {
        return properties.getProperty("botToken");
    }
}