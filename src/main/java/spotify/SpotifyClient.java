package spotify;

import config.BotConfig;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Logger;

public class SpotifyClient {

    private final String spotifyToken = BotConfig.getSpotifyToken();
    private static final Logger LOGGER = Logger.getLogger(SpotifyClient.class.getName());

    private String searchSpotify(String search) {
        try {
            LOGGER.info("Searching Spotify For Track: " + search);
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(String.format("https://api.spotify.com/v1/search?q=%s&type=track", search)))
                    .header("Authorization", "Bearer " + spotifyToken)
                    .build();
            return client.send(request,HttpResponse.BodyHandlers.ofString()).body();
        } catch (IOException | InterruptedException e) {
            LOGGER.warning("There was an issue with the request");
            LOGGER.warning(e.getMessage());
        }
        return "https://open.spotify.com/track/1R28m5eWk1EV9FQ3puWrUp";
    }




    static void main() throws IOException, InterruptedException {
        SpotifyClient spotifyClient = new SpotifyClient();
        String response = spotifyClient.searchSpotify("Eminem");
        System.out.println(response);
    }

}
