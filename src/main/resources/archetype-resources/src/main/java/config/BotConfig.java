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
            if (inputStream == null) LOGGER.error("Configuration file {} not found!", CONFIG_FILE_PATH);
            else {
                properties.load(inputStream);
                LOGGER.info("Configuration file {} loaded successfully", CONFIG_FILE_PATH);
            }
        } catch(IOException ex) {
            LOGGER.error("Failed to load configuration file: {}", CONFIG_FILE_PATH, ex);
        }
    }

    public static String getBOTToken() {
        return properties.getProperty("botToken");
    }
}