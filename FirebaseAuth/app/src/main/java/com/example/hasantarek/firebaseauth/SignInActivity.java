package com.example.hasantarek.firebaseauth;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editTextEmail;
    EditText editTextPassword;
    FirebaseAuth firebaseAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        editTextEmail = (EditText)findViewById(R.id.signInEmailId);
        editTextPassword = (EditText)findViewById(R.id.signInPasswordId);
        firebaseAuth = FirebaseAuth.getInstance();
        progressBar = (ProgressBar)findViewById(R.id.signInProgID);
        findViewById(R.id.buttonsignin).setOnClickListener(this);
        findViewById(R.id.textSignUp).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.buttonsignin:
                String email = editTextEmail.getText().toString();
                String pass = editTextPassword.getText().toString();
                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(pass))
                {
                    Toast.makeText(getApplicationContext(),"you can not empty email or password",Toast.LENGTH_LONG).show();
                    return;
                }
                signIn(email,pass);
                return;
            case R.id.textSignUp:
                finish();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                break;
        }
    }

    public void signIn(String email,String pass)
    {
            progressBar.setVisibility(View.VISIBLE);
            firebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(Task<AuthResult> task) {
                    progressBar.setVisibility(View.GONE);
                    if(task.isSuccessful())
                    {
                        finish();
                        Intent intent = new Intent(getApplicationContext(), Profile.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
            });
    }
}
