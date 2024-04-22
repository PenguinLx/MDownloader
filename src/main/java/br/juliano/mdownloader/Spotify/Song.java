package br.juliano.mdownloader.Spotify;

import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.data.tracks.GetTrackRequest;

import java.io.IOException;

public class Song extends Auth {

    private static final String accessToken = getToken() ;
    private static final String id = "6pHmbsZETMKKzKsnJPWyFy";

    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setAccessToken(accessToken)
            .build();
    private static final GetTrackRequest getTrackRequest = spotifyApi.getTrack(id)
            .build();
    public static void getTrack_Sync() {
        try {
            final Track track = getTrackRequest.execute();

            System.out.println("Name: " + track.getName());
        } catch (IOException | ParseException | SpotifyWebApiException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    public static String getToken(){
        clientCredentials_Sync();
        return Sapi.getAccessToken();
    }
    public static void main(String[] args) {
        getTrack_Sync();
    }
}
