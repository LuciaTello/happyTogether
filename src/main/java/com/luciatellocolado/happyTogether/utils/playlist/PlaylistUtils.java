package com.luciatellocolado.happyTogether.utils.playlist;

import com.luciatellocolado.happyTogether.authorization.UserAuthorization;
import com.wrapper.spotify.SpotifyApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.PlaylistSimplified;
import com.wrapper.spotify.requests.data.playlists.GetListOfCurrentUsersPlaylistsRequest;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;

@Component
public class PlaylistUtils {

    private static Logger logger = LoggerFactory.getLogger(PlaylistUtils.class);

    private final UserAuthorization userAuthorization;

    public PlaylistUtils(UserAuthorization userAuthorization) {
        this.userAuthorization = userAuthorization;
    }

    public void getListOfCurrentUsersPlaylists() {

        SpotifyApi spotifyApi = userAuthorization.getSpotifyApiLogged();

        GetListOfCurrentUsersPlaylistsRequest getListOfCurrentUsersPlaylistsRequest = spotifyApi
                .getListOfCurrentUsersPlaylists()
                .build();


        try {
            final Paging<PlaylistSimplified> playlistSimplifiedPaging = getListOfCurrentUsersPlaylistsRequest.execute();

            logger.debug("Total: " + playlistSimplifiedPaging.getTotal());
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            logger.error("Error: " + e.getMessage(), e);
        }
    }

}
