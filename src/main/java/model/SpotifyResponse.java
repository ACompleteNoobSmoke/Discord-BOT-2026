package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SpotifyResponse(
        String artist,
        String name,
        String id,
        SPOTIFY_RESPONSE_TYPE type
) {
}
