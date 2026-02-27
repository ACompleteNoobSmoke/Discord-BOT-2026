package spotify;

import com.fasterxml.jackson.core.JsonProcessingException;
import config.BotConfig;
import kotlin.text.UStringsKt;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.logging.Logger;


public class SpotifyClient {

    private static final Logger LOGGER = Logger.getLogger(SpotifyClient.class.getName());
    private static final String CLIENT_ID = BotConfig.getClientID();
    private static final String CLIENT_SECRET = BotConfig.getClientSecret();
    private static final HttpClient client = HttpClient.newHttpClient();
    private static long expiresSeconds;
    private static long expirationTimMillis;
    private static String spotifyToken;

    private static String getAPIKey() throws IOException {
        try {
            LOGGER.info("Generating Token");
            String authorizationValue = CLIENT_ID + ":" + CLIENT_SECRET;
            byte[] originalBytes = authorizationValue.getBytes(StandardCharsets.UTF_8);
            Base64.Encoder encoder = Base64.getEncoder();
            String encodedString = encoder.encodeToString(originalBytes);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://accounts.spotify.com/api/token"))
                    .header("Authorization", "Basic " + encodedString)
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString("grant_type=client_credentials"))
                    .build();
            JSONObject root = new JSONObject(client.send(request, HttpResponse.BodyHandlers.ofString()).body());
            String token = root.getString("access_token");
            expiresSeconds = root.getLong("expires_in");
            expirationTimMillis = System.currentTimeMillis() + (expiresSeconds * 1000L);
            return token;
        } catch (IOException | InterruptedException | IllegalArgumentException e) {
            LOGGER.warning("Error generating token");
            LOGGER.warning(e.getMessage());
            throw new IOException();
        }
    }

    public static String searchSpotify(String artist, String title) {
        try {
            if (expirationTimMillis == 0 || System.currentTimeMillis() >= expirationTimMillis) {
                LOGGER.info("Search Spotfy -- Generating Token");
                spotifyToken = getAPIKey();
            }
            String searchQueryArtist = artist.replace(" ", "+");
            String searchQueryTitle = title.replace(" ", "+");
            LOGGER.info("Searching Spotify For Track: " + title + "By The Artist: " + artist);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.spotify.com/v1/search?q=" + searchQueryArtist.concat("+" + searchQueryTitle) + "&type=track%2Cartist"))
                    .header("Authorization", "Bearer " + spotifyToken)
                    .build();
            String requestBody = client.send(request, HttpResponse.BodyHandlers.ofString()).body();
            return getMusicID(requestBody, title.toLowerCase());
        } catch (IOException | InterruptedException | IllegalArgumentException e) {
            LOGGER.warning("There was an issue with the request");
            LOGGER.warning(e.getMessage());
        }
        return null;
    }

    private static String getMusicID(String responseBody, String title) {
        LOGGER.info("Search Response Below ");
        LOGGER.info(responseBody);
        JSONObject root = new JSONObject(responseBody);
        JSONObject tracks = root.getJSONObject("tracks");
        JSONArray items = tracks.getJSONArray("items");
        for (int i = 0; i < items.length(); i++) {
            JSONObject track = items.getJSONObject(i);
            String name = track.getString("name");
            LOGGER.info("Searching Through Response: " + name);
            if (!name.toLowerCase().equals(title)) continue;
            return track.getString("id");
        }
        return null;
    }

    private static String getAccessToken(String responseBody) {
        JSONObject root = new JSONObject(responseBody);
        return root.getString("access_token");
    }




    static void main() throws JsonProcessingException {
        SpotifyClient spotifyClient = new SpotifyClient();

        String response = spotifyClient.searchSpotify("Eminem", "Stan");
        System.out.println(response);


    }

}
