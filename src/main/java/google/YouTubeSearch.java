package google;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import config.BotConfig;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Random;

public class YouTubeSearch {
    private static final String APPLICATION_NAME = BotConfig.getApplicationName();
    private static final String API_KEY = BotConfig.getYouTubeToken();
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(YouTubeSearch.class);
    private static final Long MAX_RESULTS = 20L;
    private static final String DEFAULT_VIDEO_ID = "dwLCjZVEtpE";


    private static YouTube getYouTube() {
        try {
            final var httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            return new YouTube.Builder(httpTransport, JacksonFactory.getDefaultInstance(), httpRequest -> {})
                    .setApplicationName(APPLICATION_NAME)
                    .build();
        } catch (GeneralSecurityException | IOException generalSecurityException) {
            log.error("Something went wrong with the YouTube object {}", generalSecurityException);
        }
        return null;
    }

    public static String searchVideo(String searchQuery) {
        try {
            YouTube.Search.List searchRequest = getYouTube().search().list("snippet");
            log.info("Searching for video with query: {}", searchQuery);
            SearchListResponse response = searchRequest.setKey(API_KEY)
                    .setQ(searchQuery)
                    .setMaxResults(MAX_RESULTS)
                    .execute();
            //int random = new Random().nextInt(0, (int) (MAX_RESULTS + 1));
            String videoId = response.getItems().stream().filter(res -> res.getId().getVideoId() != null)
                    .map(res -> res.getId().getVideoId())
                    .findFirst()
                    .orElse(DEFAULT_VIDEO_ID);
            log.info("Selected video ID: {}", videoId);
            return videoId;
        } catch (IOException e) {
            log.error("Something went wrong with the YouTube search {}", e);
        }
        return DEFAULT_VIDEO_ID;
    }

    static void main() throws IOException {
        YouTubeSearch youTubeSearch = new YouTubeSearch();
        String videoId = searchVideo("MF DOOM");
        System.out.println(videoId);
    }
}
