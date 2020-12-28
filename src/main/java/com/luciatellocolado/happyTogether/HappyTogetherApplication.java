package com.luciatellocolado.happyTogether;

import com.luciatellocolado.happyTogether.utils.playlist.PlaylistUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HappyTogetherApplication implements CommandLineRunner {


    private final PlaylistUtils playlistUtils;

    public HappyTogetherApplication(PlaylistUtils playlistUtils) {
        this.playlistUtils = playlistUtils;
    }

    public static void main(String[] args) {
        SpringApplication.run(HappyTogetherApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        playlistUtils.getListOfCurrentUsersPlaylists();

    }

}
