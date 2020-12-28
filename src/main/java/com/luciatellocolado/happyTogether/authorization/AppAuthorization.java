package com.luciatellocolado.happyTogether.authorization;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import org.apache.hc.core5.http.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AppAuthorization {

    private static Logger logger = LoggerFactory.getLogger(AppAuthorization.class);

    @Value("${spotify.clientId}")
    private String clientId;

    @Value("${spotify.clientSecret}")
    private String clientSecret;


    public SpotifyApi getSpotifyApi() {

        SpotifyApi spotifyApi = new SpotifyApi.Builder()
                .setClientId(clientId)
                .setClientSecret(clientSecret)
                .build();

        ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials()
                .build();

        try {
            final com.wrapper.spotify.model_objects.credentials.ClientCredentials clientCredentials = clientCredentialsRequest.execute();

            spotifyApi.setAccessToken(clientCredentials.getAccessToken());

            logger.debug("Expires in: " + clientCredentials.getExpiresIn());
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            logger.error("Error: " + e.getMessage(), e);
        }

        return spotifyApi;
    }

    public String getAccesToken() {

        String accesToken = null;

        SpotifyApi spotifyApi = new SpotifyApi.Builder()
                .setClientId(clientId)
                .setClientSecret(clientSecret)
                .build();

        ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials()
                .build();

        try {
            final com.wrapper.spotify.model_objects.credentials.ClientCredentials clientCredentials = clientCredentialsRequest.execute();

            accesToken = clientCredentials.getAccessToken();

            logger.debug("Expires in: " + clientCredentials.getExpiresIn());
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            logger.error("Error: " + e.getMessage(), e);
        }

        return accesToken;
    }
}
