package com.luciatellocolado.happyTogether.authorization;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.SpotifyHttpManager;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;
import org.apache.hc.core5.http.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;

@Component
public class UserAuthorization {

    private static Logger logger = LoggerFactory.getLogger(UserAuthorization.class);

    @Value("${spotify.clientId}")
    private String clientId;

    @Value("${spotify.clientSecret}")
    private String clientSecret;


    public SpotifyApi getSpotifyApiLogged() {


        SpotifyApi spotifyApi = new SpotifyApi.Builder()
                .setClientId(clientId)
                .setClientSecret(clientSecret)
                .setRedirectUri(SpotifyHttpManager.makeUri("https://example.com/spotify-redirect"))
                .build();

        AuthorizationCodeUriRequest authorizationCodeUriRequest = spotifyApi.authorizationCodeUri()
//          .state("x4xkmn9pu3j6ukrs8n")
//          .scope("user-read-birthdate,user-read-email")
//          .show_dialog(true)
                .build();

        final URI uri = authorizationCodeUriRequest.execute();

        spotifyApi = new SpotifyApi.Builder()
                .setClientId(clientId)
                .setClientSecret(clientSecret)
                .setRedirectUri(uri)
                .build();

        AuthorizationCodeRequest authorizationCodeRequest = spotifyApi.authorizationCode("")
                .build();

        try {
            final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRequest.execute();

            // Set access and refresh token for further "spotifyApi" object usage
            spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
            spotifyApi.setRefreshToken(authorizationCodeCredentials.getRefreshToken());

            System.out.println("Expires in: " + authorizationCodeCredentials.getExpiresIn());
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return spotifyApi;
    }


}
