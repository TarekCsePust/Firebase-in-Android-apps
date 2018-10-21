package com.example.hasantarek.firebasepushnotification;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class notificationActivity extends AppCompatActivity {

    TextView textView;
    String message="no new message";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        textView = (TextView)findViewById(R.id.msg);
        if(getIntent().getExtras()!=null)
        {
            message = getIntent().getExtras().getString("message");
            if(message==null)
            {
                message = "no new message";
            }
        }

        textView.setText(message);

    }

}
