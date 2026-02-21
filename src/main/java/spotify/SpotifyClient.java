package spotify;

import com.fasterxml.jackson.core.JsonProcessingException;
import config.BotConfig;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Logger;


public class SpotifyClient {

    private static final String spotifyToken = BotConfig.getSpotifyToken();
    private static final Logger LOGGER = Logger.getLogger(SpotifyClient.class.getName());

    public static String searchSpotify(String artist, String title) {
        try {
            String searchQueryArtist = artist.replace(" ", "+");
            String searchQueryTitle = title.replace(" ", "+");
            LOGGER.info("Searching Spotify For Track: " + title + "By The Artist: " + artist);
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.spotify.com/v1/search?q=" + searchQueryArtist.concat("+" + searchQueryTitle) + "&type=track%2Cartist&market=EN"))
                    .header("Authorization", "Bearer " + spotifyToken)
                    .build();
            String requestBody = client.send(request,HttpResponse.BodyHandlers.ofString()).body();
            return getMusicID(requestBody, title.toLowerCase());
        } catch (IOException | InterruptedException | IllegalArgumentException e) {
            LOGGER.warning("There was an issue with the request");
            LOGGER.warning(e.getMessage());
        }
        return null;
    }

    private static String getMusicID(String responseBody, String title) {
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




    static void main() throws JsonProcessingException {
        SpotifyClient spotifyClient = new SpotifyClient();

        String response = spotifyClient.searchSpotify("Tame Impala", "Let It Happen");
        System.out.println(response);


    }

}
