package br.juliano.mdownloader.Spotify;

import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.ClientCredentials;
import se.michaelthelin.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;

import java.io.IOException;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class Auth {
    private static final String ClientId = ApiKeys.CLIENT_ID;
    private static final String ClientSecret = ApiKeys.CLIENT_SECRET;

  protected static final SpotifyApi Sapi = new SpotifyApi.Builder().setClientId(ClientId).setClientSecret(ClientSecret).build();

    private static final ClientCredentialsRequest clientCredentialsRequest = Sapi.clientCredentials().build();

    public static void clientCredentials_Sync() {
        try {
            final ClientCredentials clientCredentials = clientCredentialsRequest.execute();

            // Set access token for further "spotifyApi" object usage
            Sapi.setAccessToken(clientCredentials.getAccessToken());
            //System.out.println("Expires in: " + clientCredentials.getExpiresIn());
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    public static void expiresToken(){

    }

//    public static void clientCredentials_Async() {
//        try {
//            final CompletableFuture<ClientCredentials> clientCredentialsFuture = clientCredentialsRequest.executeAsync();
//
//            // Thread free to do other tasks...
//
//            // Example Only. Never block in production code.
//            final ClientCredentials clientCredentials = clientCredentialsFuture.join();
//
//            // Set access token for further "spotifyApi" object usage
//            Sapi.setAccessToken(clientCredentials.getAccessToken());
//
//            //System.out.println("Expires in: " + clientCredentials.getExpiresIn());
//        } catch (CompletionException e) {
//            System.out.println("Error: " + e.getCause().getMessage());
//        } catch (CancellationException e) {
//            System.out.println("Async operation cancelled.");
//        }
//    }

//    public static void main(String[] args) {
//        clientCredentials_Sync();
//        clientCredentials_Async();
//
//        System.out.println(Sapi.getAccessToken());
//    }
}
