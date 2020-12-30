package com.luciatellocolado.happyTogether.controllers;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.PlaylistSimplified;
import com.wrapper.spotify.requests.data.playlists.GetListOfCurrentUsersPlaylistsRequest;
import org.apache.hc.core5.http.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Arrays;

@RestController
@RequestMapping("/services")
public class PlaylistController {

    Logger logger = LoggerFactory.getLogger(PlaylistController.class);

    private AuthController authController;
    private SpotifyApi spotifyApi;

    public PlaylistController(AuthController authController) {
        this.authController = authController;
    }


    @GetMapping("checkPlaylists")
    @ResponseBody
    public void checkPlaylists() {

        spotifyApi = authController.spotifyApi;

        GetListOfCurrentUsersPlaylistsRequest usersPlaylistsRequest = spotifyApi.getListOfCurrentUsersPlaylists().build();

        try {

            Paging<PlaylistSimplified> playlistPaging = usersPlaylistsRequest.execute();

            PlaylistSimplified[] playlistPagingItems = playlistPaging.getItems();

            Arrays.stream(playlistPagingItems).forEach(playlist -> logger.info(playlist.getName()));

        } catch (IOException | SpotifyWebApiException | ParseException e) {
            logger.error("CheckPlaylists" + e.getMessage(), e);
        }

    }


}
