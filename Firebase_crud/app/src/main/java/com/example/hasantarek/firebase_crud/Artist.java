package com.example.hasantarek.firebase_crud;

/**
 * Created by HASAN TAREK on 1/26/2018.
 */
public class Artist {

    String artistId;
    String artistName;
    String artistGenre;

    public Artist()
    {

    }

    public Artist(String artistId, String artistName, String artistGenre) {
        this.artistId = artistId;
        this.artistName = artistName;
        this.artistGenre = artistGenre;
    }

    public String getArtistId() {
        return artistId;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getArtistGenre() {
        return artistGenre;
    }
}
