package com.example.hasantarek.firebaseauth;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class MainActivity extends AppCompatActivity {

    TextView textViewLogin;
    EditText editTextUserEmail;
    EditText editTextPassword;
    Button buttonSignUp;
    FirebaseAuth firebaseAuth;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewLogin = (TextView)findViewById(R.id.buttonlogin);
        editTextPassword = (EditText)findViewById(R.id.signUpPasswordId);
        editTextUserEmail = (EditText)findViewById(R.id.signUpEmailId);
        buttonSignUp = (Button)findViewById(R.id.buttonsignup);
        firebaseAuth = FirebaseAuth.getInstance();
        progressBar = (ProgressBar)findViewById(R.id.signUpProgID);
        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(MainActivity.this,SignInActivity.class));
            }
        });
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String UserName = editTextUserEmail.getText().toString().trim();
                String Password = editTextPassword.getText().toString().trim();

                if(TextUtils.isEmpty(UserName) || TextUtils.isEmpty(Password))
                {
                    editTextUserEmail.setError("Email is required");
                    editTextPassword.setError("Password is required");
                    editTextPassword.requestFocus();
                    Toast.makeText(MainActivity.this,"You should not empty username or password",Toast.LENGTH_LONG).show();
                    return;

                }

                if(Password.length()<6)
                {
                    editTextPassword.setError("Minimum length is 6");
                    editTextPassword.requestFocus();
                    Toast.makeText(MainActivity.this,"You should not empty username or password",Toast.LENGTH_LONG).show();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                firebaseAuth.createUserWithEmailAndPassword(UserName,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            finish();
                            Toast.makeText(MainActivity.this, "User registration sucessfull", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(), Profile.class));

                        } else {
                            if(task.getException() instanceof FirebaseAuthUserCollisionException)
                            {
                                Toast.makeText(MainActivity.this, "You are already registered", Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                Toast.makeText(MainActivity.this,task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }

                        }
                    }
                });

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        if(firebaseAuth.getCurrentUser()!=null)
        {
            finish();
            startActivity(new Intent(getApplicationContext(),Profile.class));
        }
    }
}
