package com.example.hasantarek.firebase_crud;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddTrackActivity extends AppCompatActivity {

    TextView textViewArtistName;
    EditText editTextTrackName;
    SeekBar seekBarRating;
    ListView listViewTrack;
    DatabaseReference databaseTracks;
    List<Track> tracks = new ArrayList<>();
    TrackList adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_track);
        textViewArtistName = (TextView)findViewById(R.id.artistNameId);
        editTextTrackName = (EditText)findViewById(R.id.tracknameId);
        seekBarRating = (SeekBar)findViewById(R.id.seekbarId);
        listViewTrack = (ListView)findViewById(R.id.trackListId);
        Intent intent = getIntent();
        String id = intent.getStringExtra(MainActivity.ArtistId);
        String name = intent.getStringExtra(MainActivity.ArtistName);
        textViewArtistName.setText(name);
        databaseTracks = FirebaseDatabase.getInstance().getReference("tracks").child(id);
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseTracks.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tracks.clear();
                for (DataSnapshot trackSnapshot : dataSnapshot.getChildren())
                {
                    Track track = trackSnapshot.getValue(Track.class);
                    tracks.add(track);
                }

                adapter = new TrackList(AddTrackActivity.this,R.layout.track_list_layout,tracks);
                listViewTrack.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void add_track(View view)
    {
        String trackName = editTextTrackName.getText().toString();
        int rating = seekBarRating.getProgress();
        if(!TextUtils.isEmpty(trackName))
        {
            String id = databaseTracks.push().getKey();
            Track track = new Track(trackName,id,rating);
            databaseTracks.child(id).setValue(track);
            Toast.makeText(getApplicationContext(),"Track saved sucessfully",Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(getApplicationContext(),"track name should not be empty.",Toast.LENGTH_LONG).show();
        }
    }
}
