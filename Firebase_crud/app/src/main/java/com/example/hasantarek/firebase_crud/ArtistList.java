package com.example.hasantarek.firebase_crud;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by HASAN TAREK on 1/27/2018.
 */
public class ArtistList extends ArrayAdapter<Artist> {

    private Activity context;
    private List<Artist>artistList;

    public ArtistList(Activity context, int resource,List<Artist> artistList) {
        super(context, resource,artistList);
        this.context = context;
        this.artistList = artistList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_item_layout, null, true);
        TextView artistName = (TextView)listViewItem.findViewById(R.id.item_artist);
        TextView genre = (TextView)listViewItem.findViewById(R.id.item_genre);
        Artist artist = artistList.get(position);
        artistName.setText(artist.getArtistName());
        genre.setText(artist.getArtistGenre());
        return listViewItem;
    }
}
