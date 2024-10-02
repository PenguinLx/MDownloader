package br.juliano.mdownloader.spotify;

import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.ClientCredentials;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;
import se.michaelthelin.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;

import java.io.IOException;
import java.net.URI;

public class Auth {
    //authorization code request: https://github.com/spotify-web-api-java/spotify-web-api-java/blob/master/examples/authorization/authorization_code/AuthorizationCodeExample.java
    private static final String ClientId = ApiKeys.CLIENT_ID;
    private static final String ClientSecret = ApiKeys.CLIENT_SECRET;
    private static final URI redirectUri = SpotifyHttpManager.makeUri("https://localhost/callback/");
    private static final String code = ApiKeys.CODE;

  protected static final SpotifyApi Sapi = new SpotifyApi.Builder().setClientId(ClientId).setClientSecret(ClientSecret).setRedirectUri(redirectUri).build();
    private static final AuthorizationCodeRequest authorizationCodeRequest = Sapi.authorizationCode(code).build();
    private static final AuthorizationCodeUriRequest authorizationCodeUriRequest = Sapi.authorizationCodeUri().show_dialog(true).response_type("code").scope("user-read-private,user-read-email").build();

    public static void authorizationCodeUri_Sync(){
        final URI uri = authorizationCodeUriRequest.execute();

        System.out.println("URI: " + uri.toString());
    }
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

    public static void main(String[] args) {
        clientCredentials_Sync();
        authorizationCodeUri_Sync();

        System.out.println(Sapi.getAccessToken());
    }
}
