package model;

public record SpotifyResponse(
        String artistName,
        String trackName,
        String trackID,
        SPOTIFY_RESPONSE_TYPE type
) {
}
