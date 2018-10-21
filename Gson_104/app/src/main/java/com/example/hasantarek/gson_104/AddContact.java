package com.example.hasantarek.gson_104;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.Attributes;

public class AddContact extends AppCompatActivity {

    private TextView TextName;
    private TextView TextEmail;
    private EditText Name;
    private EditText Email;
    private ImageView imageView;
    private Bitmap bitmap;
    private Gson gson;
    private String server_url = "http:// 192.168.0.105/android_104/insert.php";
    private static final int img_request = 777;
    private Contact contact;
    private Button upload_btn;
    private Button select_prof_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        TextName = (TextView)findViewById(R.id.disp_name);
        TextEmail = (TextView)findViewById(R.id.disp_email);
        Name = (EditText)findViewById(R.id.name);
        Email = (EditText)findViewById(R.id.email);
        imageView = (ImageView)findViewById(R.id.img);
        upload_btn = (Button)findViewById(R.id.upload_btn);
        select_prof_btn = (Button)findViewById(R.id.select_pro_btn);
    }

    public void profile_pic(View view)
    {
        Intent intent = new Intent();
        intent.setType("image/+");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,img_request);
    }

    public void upload_contact(View view)
    {
        String image = imageToString();
        String name = Name.getText().toString();
        String email = Email.getText().toString();
        contact = new Contact(name,email,image);
        gson = new Gson();
        final String uploadJson = gson.toJson(contact);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        gson = new Gson();
                        contact = gson.fromJson(response,Contact.class);
                        displayAlert(contact);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("contact",uploadJson);
                return params;

            }
        };

        Mysingleton.getmInstance(this).addTorequest(stringRequest);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == img_request && resultCode==RESULT_OK && data!=null)
        {
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),path);
                imageView.setImageBitmap(bitmap);
                Name.setVisibility(View.GONE);
                Email.setVisibility(View.GONE);
                select_prof_btn.setVisibility(View.GONE);
                TextName.setVisibility(View.VISIBLE);
                TextEmail.setVisibility(View.VISIBLE);
                upload_btn.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.VISIBLE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String imageToString()
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imageArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imageArray,Base64.DEFAULT);
    }

    private void displayAlert(Contact contact)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("server response");
        builder.setMessage(contact.getResponse());
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.create();
        builder.show();

    }
}
