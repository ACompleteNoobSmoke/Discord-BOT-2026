package config;

import java.util.logging.Logger;

public class BotConfig {

    private BotConfig() {} // Prevent instantiation

    private static final Logger LOGGER = Logger.getLogger(BotConfig.class.getName());
    private static final String APPLICATION_NAME = "APPLICATION_NAME";
    private static final String CLIENT_ID = "CLIENT_ID";
    private static final String CLIENT_SECRET = "CLIENT_SECRET";
    private static final String DISCORD_BOT_TOKEN = "DISCORD_BOT_TOKEN";
    private static final String YOUTUBE_API_TOKEN = "YOUTUBE_API_TOKEN";

    public static String getApplicationName() {
        return requireEnv(APPLICATION_NAME);
    }

    public static String getClientID() {
        return requireEnv(CLIENT_ID);
    }

    public static String getClientSecret() {
        return requireEnv(CLIENT_SECRET);
    }

    public static String getBOTToken() {
        return requireEnv(DISCORD_BOT_TOKEN);
    }

    public static String getYouTubeToken() {
        return requireEnv(YOUTUBE_API_TOKEN);
    }

    private static String requireEnv(String key) {
        LOGGER.info("Getting Value For ENV: {" + key + "}");
        String value = System.getenv(key);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException("Missing required environment variable: " + key);
        }
        return value;
    }
}