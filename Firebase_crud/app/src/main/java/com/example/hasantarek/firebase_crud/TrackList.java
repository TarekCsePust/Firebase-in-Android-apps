package com.example.hasantarek.firebase_crud;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by HASAN TAREK on 1/27/2018.
 */
public class TrackList extends ArrayAdapter<Track> {

    private Activity context;
    private List<Track> trackList;

    public TrackList(Activity context, int resource,List<Track> trackList) {
        super(context, resource,trackList);
        this.context = context;
        this.trackList = trackList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.track_list_layout, null, true);
        TextView trackName = (TextView)listViewItem.findViewById(R.id.item_track);
        TextView trackRating = (TextView)listViewItem.findViewById(R.id.item_trackRating);
        Track track = trackList.get(position);
        trackName.setText(track.getTrackName());
        trackRating.setText(String.valueOf(track.getTrackRating()));
        return listViewItem;
    }
}
