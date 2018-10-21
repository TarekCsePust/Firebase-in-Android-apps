package com.example.hasantarek.firebaseauth;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import javax.microedition.khronos.egl.EGLDisplay;

public class Profile extends AppCompatActivity {

    ImageView imageViewProfileImg;
    EditText editTextUserName;
    Button buttonSave;
    ProgressBar progressBar;
    String ProfileImageUrl;
    FirebaseAuth firebaseAuth;
    TextView textViewVarified;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        editTextUserName = (EditText)findViewById(R.id.nameID);
        imageViewProfileImg = (ImageView)findViewById(R.id.imageID);
        buttonSave = (Button)findViewById(R.id.buttonSaveID);
        progressBar = (ProgressBar)findViewById(R.id.profileProgID);
        firebaseAuth = FirebaseAuth.getInstance();
        textViewVarified = (TextView)findViewById(R.id.textViewVarifiedID);

        final Uri imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                "://" + getResources().getResourcePackageName(R.drawable.tarek1)
                + '/' + getResources().getResourceTypeName(R.drawable.tarek1) + '/' + getResources().getResourceEntryName(R.drawable.tarek1));
        //Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.tarek1);
        loadUserInformation();
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String username = editTextUserName.getText().toString();
                if(TextUtils.isEmpty(username))
                {
                    Toast.makeText(getApplicationContext(),"Name is required",Toast.LENGTH_LONG).show();
                    return;
                }
                else
                {
                    progressBar.setVisibility(View.VISIBLE);
                    StorageReference storageReference = FirebaseStorage.getInstance().getReference("profilepics/"+System.currentTimeMillis()+".jpg");
                    if(imageUri!=null)
                    {
                        storageReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                progressBar.setVisibility(View.GONE);
                                ProfileImageUrl = taskSnapshot.getDownloadUrl().toString();
                                SaveUserInfromation(username,ProfileImageUrl);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(Exception e) {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }
            }
        });

    }
    public void  SaveUserInfromation(String username,String ProfileImageUrl)
    {
        FirebaseUser firebaseUser =firebaseAuth.getCurrentUser();
        if(firebaseUser!=null && ProfileImageUrl!=null)
        {
            UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder()
                    .setDisplayName(username)
                    .setPhotoUri(Uri.parse(ProfileImageUrl))
                    .build();
            firebaseUser.updateProfile(userProfileChangeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(Task<Void> task) {

                    if(task.isSuccessful())
                    {
                        Toast.makeText(getApplicationContext(),"profile updated",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(firebaseAuth.getCurrentUser()==null)
        {
            finish();
            startActivity(new Intent(getApplicationContext(),SignInActivity.class));
        }
    }

    public void loadUserInformation()
    {
        Toast.makeText(getApplicationContext(),"Load info",Toast.LENGTH_LONG).show();

        final FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser.getPhotoUrl()!=null)
        {
            Glide.with(this).load(firebaseUser.getPhotoUrl().toString())
            .into(imageViewProfileImg);
        }

        if(firebaseUser.getDisplayName()!=null)
        {
            editTextUserName.setText(firebaseUser.getDisplayName().toString());
        }

        if(firebaseUser.isEmailVerified())
        {
            textViewVarified.setText("Email verified");
        }
        else
        {
            textViewVarified.setText("Email not verified(click to verify)");
            textViewVarified.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(Task<Void> task) {
                            Toast.makeText(getApplicationContext(),"verification email sent",Toast.LENGTH_LONG).show();
                        }
                    });
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.logouMenutID:
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(getApplicationContext(),SignInActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
