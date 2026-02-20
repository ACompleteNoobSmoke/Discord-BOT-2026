package spotify;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import config.BotConfig;
import model.SpotifyResponse;
import model.SpotifyResponses;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;


public class SpotifyClient {

    private final String spotifyToken = BotConfig.getSpotifyToken();
    private static final Logger LOGGER = Logger.getLogger(SpotifyClient.class.getName());

    private String searchSpotify(String search) {
        try {
            search = search.replace(" ", "+");
            LOGGER.info("Searching Spotify For Track: " + search);
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.spotify.com/v1/search?q=" + search + "&type=track&market=EN"))
                    .header("Authorization", "Bearer " + spotifyToken)
                    .build();
            return client.send(request,HttpResponse.BodyHandlers.ofString()).body();
        } catch (IOException | InterruptedException | IllegalArgumentException e) {
            LOGGER.warning("There was an issue with the request");
            LOGGER.warning(e.getMessage());
        }
        return "https://open.spotify.com/track/1R28m5eWk1EV9FQ3puWrUp";
    }




    static void main() throws JsonProcessingException {
        SpotifyClient spotifyClient = new SpotifyClient();
        ObjectMapper objectMapper = new ObjectMapper();
        String response = spotifyClient.searchSpotify("Tame Impala");
        Map<String, Map<String, Map<String, Object>>> map = objectMapper.readValue(response, Map.class);
        for(Map.Entry<String, Map<String, Map<String, Object>>> entry : map.entrySet()) {
            System.out.println("KEY: " + entry.getKey());
            System.out.println("VALUE BELOW");
            for (Map.Entry<String, Map<String, Object>> e : entry.getValue().entrySet()) {
                if (!e.getKey().equals("items")) continue;
                System.out.println(e.getKey());
                System.out.println("---------------------");
                System.out.println(e.getValue());
                e.getValue().entrySet().stream().forEach((k) -> {
                    System.out.println(k.getKey());
                    System.out.println(k.getValue());
                    System.out.println();
                });
                System.out.println();
            }
            System.out.println();
        }
    }

}
