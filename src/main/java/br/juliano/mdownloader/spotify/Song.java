package br.juliano.mdownloader.spotify;

import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.ArtistSimplified;
import se.michaelthelin.spotify.model_objects.specification.AudioFeatures;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.data.tracks.GetAudioFeaturesForTrackRequest;
import se.michaelthelin.spotify.requests.data.tracks.GetTrackRequest;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Song extends Auth {
    //TODO:mudar essa classe inteira para object instance based
    private static final String accessToken = getToken();
    private String linkSong;
    public Song(String linkSong){
        this.linkSong = linkSong;
    }

    //public static String linkC = "https://open.spotify.com/intl-pt/track/0TI8TP4FitVPoEHPTySx48?si=0db42669ee7d42c2";

    public void setLinkSong(String linkSong) {
        this.linkSong = linkSong;
    }

    //    public static String getLink(){
//        Scanner kb = new Scanner(System.in);
//         String linkC;
//         linkC = kb.next();
//         return linkC;
//
//
//    }
    public String getIdLinkSong(String link) {
        Pattern pattern = Pattern.compile("/track/([^?]+)");
        Matcher matcher = pattern.matcher(link);
        String id;
        if (matcher.find()) {
             id = matcher.group(1);
            return id;
        }
        return "Música não encontrada";
    }
    public String getArtistFromTrack(String trackId){
        GetTrackRequest getTrackRequest = spotifyApi.getTrack(trackId).build();

        try {
            Track track = getTrackRequest.execute();
            ArtistSimplified[] artists = track.getArtists();
            StringBuilder artistNames = new StringBuilder();
            for (int i = 0; i < artists.length; i++) {
                /*exemplo:2 artistas:nervosa e gary holt = 2 - 1= 1,
                quando o loop for 0, ele colocará uma virgula pois
                o é < que 1 ou seja uma virgula depois de nervosa, quando o loop for 1 já a condição vai ser falsa pois 1 não é < 1
                */
                artistNames.append(artists[i].getName());
                if (i < artists.length - 1) {
                    artistNames.append(", ");
                }
            }
            return artistNames.toString();

        } catch (IOException | ParseException | SpotifyWebApiException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return "";
    }
    //private static final String id = "2aoo2jlRnM3A0NyLQqMN2f";

     SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setAccessToken(accessToken)
            .build();
    /////////////////////////////////////////////////////////
    public void getTrack_Sync() {
        String id = getIdLinkSong(linkSong);
        final GetTrackRequest getTrackRequest = spotifyApi.getTrack(id)
                .build();
        try {
            final Track track = getTrackRequest.execute();
            System.out.println("Name: " + track.getName());
            System.out.println("Álbum: " + track.getAlbum().getName());
            System.out.println("Artista(s): " + getArtistFromTrack(id));
            System.out.println("Preview Url: " + track.getPreviewUrl());
            System.out.println("Href: " + track.getHref());
        } catch (IOException | ParseException | SpotifyWebApiException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    public void getAudioFeaturesForTrack_Sync() {
         final GetAudioFeaturesForTrackRequest getAudioFeaturesForTrackRequest = spotifyApi
                .getAudioFeaturesForTrack(getIdLinkSong(linkSong))
                .build();
        try {
            final AudioFeatures audioFeatures = getAudioFeaturesForTrackRequest.execute();

            System.out.println("ID: " + audioFeatures.getId());
            System.out.println("Teste: " + audioFeatures.getTrackHref());
            System.out.println("Analysis URL: " + audioFeatures.getAnalysisUrl());
            System.out.println("URI: " + audioFeatures.getUri());
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static String getToken(){
        clientCredentials_Sync();
        return Sapi.getAccessToken();
    }
    public static void main(String[] args) {
//        getTrack_Sync();
//        getAudioFeaturesForTrack_Sync();
//        Scanner kb = new Scanner(System.in);
//        String L;
//        System.out.println("Link da música: ");
//        linkSong = kb.next();
//        getTrack_Sync();
        Song song = new Song("https://open.spotify.com/intl-pt/track/2e3g8go386Zn6EyIz60Ci9?si=f5e266eeaa3c41b6");
        song.getTrack_Sync();
        song.getAudioFeaturesForTrack_Sync();
    }
}
