package com.example.hasantarek.firebase_crud;

/**
 * Created by HASAN TAREK on 1/27/2018.
 */
public class Track {

    String trackName;
    String trackId;
    int trackRating;

    public Track()
    {

    }

    public Track(String trackName, String trackId, int trackRating) {
        this.trackName = trackName;
        this.trackId = trackId;
        this.trackRating = trackRating;
    }

    public String getTrackName() {
        return trackName;
    }

    public String getTrackId() {
        return trackId;
    }

    public int getTrackRating() {
        return trackRating;
    }
}
