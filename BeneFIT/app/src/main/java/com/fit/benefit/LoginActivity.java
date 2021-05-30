package com.fit.benefit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import android.util.Log;

public class LoginActivity extends AppCompatActivity {

    FirebaseAuth auth;
    ProgressBar progress;
    Button loginButton;
    EditText email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.login);
        progress = findViewById(R.id.loading);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("test", "onClick");
                String strEmail = email.getText().toString().trim();
                String strPassword = password.getText().toString().trim();

                if (TextUtils.isEmpty(strEmail)) {
                    email.setError("Email is required");
                }
                if(TextUtils.isEmpty(strPassword)) {
                    password.setError("Password is required");
                }

                progress.setVisibility(View.VISIBLE);
                Log.i("test",  "Finished pre-login activities");
                auth.signInWithEmailAndPassword(strEmail, strPassword).addOnCompleteListener(
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progress.setVisibility(View.GONE);
                                Log.i("test", "Finished pre-check activities");
                                if(task.isSuccessful()) {
                                    FirebaseUser user = auth.getCurrentUser();
                                    Log.i("test", "Logged in");
                                    updateUI(user);
                                }
                                else {
                                    Log.i("test", "Failed login");
                                    Toast.makeText(LoginActivity.this, "Error ! " +
                                                    task.getException().getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }


    private void updateUI(FirebaseUser user) {
        Log.i("test", "UpdateUI");
        if(user != null) {
            Intent intent = new Intent (this, CategoryActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_SHORT).show();
        }
    }
}