// activity for the registration of the user
package com.fit.benefit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.fit.benefit.LoginActivity.NCat;
import static com.fit.benefit.LoginActivity.fCat;
import static com.fit.benefit.LoginActivity.favorites;

public class RegisterActivity extends AppCompatActivity {

    private EditText mEmail, mPassword, mPassword2;
    private Button mRegisterBtn;
    private FirebaseAuth fAuth;
    private ProgressBar mProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        mPassword2 = findViewById(R.id.confermPassword);
        mRegisterBtn = findViewById(R.id.register);
        mProgress = findViewById(R.id.loading);
        fAuth = FirebaseAuth.getInstance();

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // makes sure all data is inserted and the password is long enough
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                String password2 = mPassword2.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    mEmail.setError("Email is required");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    mPassword.setError("Password is required");
                    return;
                }
                if (password.length() < 8) {
                    mPassword.setError("Password is too short! Must be at least 8 characters");
                    return;
                }
                if (!password.equals(password2)){
                    mPassword.setError("Password does not match!");
                    return;
                }

                mProgress.setVisibility(View.VISIBLE); // hides loading animation

                // register the user on firebase
                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                mProgress.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    FirebaseUser user = fAuth.getCurrentUser();
                                    createDB(user);
                                    updateUI(user);
                                } else {
                                    Toast.makeText(RegisterActivity.this, "Error ! " +
                                            task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
            }
        });
    }

    // goes to the category activity
    private void updateUI(FirebaseUser user) {
        if(user != null) {
            fAuth.signInWithEmailAndPassword(mEmail.getText().toString().trim(),
                    mPassword.getText().toString().trim()).addOnCompleteListener(this,
                    new OnCompleteListener<AuthResult> () {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                FirebaseUser user = fAuth.getCurrentUser();
                            }
                            else {
                                Toast.makeText(RegisterActivity.this, "Error ! " + task.
                                        getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            Intent intent = new Intent (this, CategoryActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(RegisterActivity.this, "Error", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = fAuth.getCurrentUser();
    }

    // create the database on firestore
    private void createDB(FirebaseUser user) {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        for (int i = 0; i < NCat; i++) {
            Map<String, Object> size = new HashMap<>();
            size.put("Size", 0);
            database.collection(user.getUid()).document(Integer.toString(i+fCat)).set(size);
        }
        for (int i = 0; i < NCat; i++) {
            favorites[i] = new ArrayList<Integer>();
        }
    }
}