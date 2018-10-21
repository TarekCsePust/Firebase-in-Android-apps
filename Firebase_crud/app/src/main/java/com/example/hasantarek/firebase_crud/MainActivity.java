package com.example.hasantarek.firebase_crud;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public final static String ArtistName = "artist_name";
    public final static  String ArtistId = "artist_id";
    EditText editText;
    Spinner spinner;
    DatabaseReference databaseArtist;
    ListView listView;
    ArtistList artistList;
    List <Artist>arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText)findViewById(R.id.name);
        spinner = (Spinner)findViewById(R.id.spinner);
        databaseArtist = FirebaseDatabase.getInstance().getReference("artists");
        listView = (ListView)findViewById(R.id.artistList);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Artist artist = artistList.getItem(position);
                Intent intent = new Intent(MainActivity.this, AddTrackActivity.class);
                intent.putExtra(ArtistName, artist.getArtistName());
                intent.putExtra(ArtistId, artist.getArtistId());
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Artist artist = artistList.getItem(position);
                showUpdateDialoge(artist.getArtistId(), artist.getArtistName());
                return false;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseArtist.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                arrayList.clear();
                for (DataSnapshot arrtistSnapshot : dataSnapshot.getChildren()) {
                    Artist artist = arrtistSnapshot.getValue(Artist.class);
                    arrayList.add(artist);
                }
                artistList = new ArtistList(MainActivity.this, R.layout.list_item_layout, arrayList);
                listView.setAdapter(artistList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void showUpdateDialoge(final String Id,String artistName)
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_dialoge,null);
        dialogBuilder.setView(dialogView);
        final EditText editTextArtist = (EditText)dialogView.findViewById(R.id.new_name);
        final Spinner spinnerGenres = (Spinner)dialogView.findViewById(R.id.spinnerGenre);
        final Button updatebtn = (Button)dialogView.findViewById(R.id.buttonUpdate);
        final Button deletebtn = (Button)dialogView.findViewById(R.id.buttonDelete);
        dialogBuilder.setTitle("Updating Artist" + artistName);
        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String editname = editTextArtist.getText().toString();
                if (TextUtils.isEmpty(editname)) {
                    editTextArtist.setError("Name requerd");
                    return;
                }
                updateInfo(Id, editname, spinnerGenres.getSelectedItem().toString());
                alertDialog.dismiss();
            }
        });

        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteInfo(Id);
                alertDialog.dismiss();
            }
        });

    }

    public boolean updateInfo(String id,String name,String genre)
    {
        DatabaseReference databaseReference =FirebaseDatabase.getInstance().getReference("artists").child(id);
        Artist artist = new Artist(id,name,genre);
        databaseReference.setValue(artist);
        Toast.makeText(MainActivity.this,"Updated sucessfully",Toast.LENGTH_LONG).show();
        return true;
    }

    public boolean deleteInfo(String id)
    {
        DatabaseReference databaseReference =FirebaseDatabase.getInstance().getReference("artists").child(id);
        DatabaseReference databaseReferenceTrack =FirebaseDatabase.getInstance().getReference("tracks").child(id);
        databaseReference.removeValue();
        databaseReferenceTrack.removeValue();
        Toast.makeText(MainActivity.this,"Deleted Artist",Toast.LENGTH_LONG).show();
        return true;
    }

    public void add_artist(View view)
    {
        String name = editText.getText().toString();
        String genre = spinner.getSelectedItem().toString();
        if(!TextUtils.isEmpty(name))
        {
            String id = databaseArtist.push().getKey();
            Artist artist = new Artist(id,name,genre);
            databaseArtist.child(id).setValue(artist);
            Toast.makeText(this,"Artist Added",Toast.LENGTH_LONG).show();
            editText.setText("");

        }
        else
        {
            Toast.makeText(this,"You should enter a name",Toast.LENGTH_SHORT).show();
        }
    }
}
