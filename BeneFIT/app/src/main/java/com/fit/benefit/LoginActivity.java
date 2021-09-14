// activity for logging in the user
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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    FirebaseAuth auth;
    ProgressBar progress;
    Button loginButton;
    EditText email, password;
    public static final int NCat = 7; // number of categories
    public static final int fCat = 8; // first category ID
    public static ArrayList<Integer>[] favorites = new ArrayList[NCat]; // saved workouts arrayList

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.login);
        progress = findViewById(R.id.loading);
        auth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull @NotNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) { // if the user is already logged in then it goes directly to the Category Activity
                    getFavorites(user);
                    updateUI(user);
                }

                // checking for errors in user inserted Email or Password
                loginButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String strEmail = email.getText().toString().trim();
                        String strPassword = password.getText().toString().trim();

                        if (TextUtils.isEmpty(strEmail)) {
                            email.setError("Email is required");
                        }
                        if (TextUtils.isEmpty(strPassword)) {
                            password.setError("Password is required");
                        }

                        // login via firebase
                        progress.setVisibility(View.VISIBLE);
                        auth.signInWithEmailAndPassword(strEmail, strPassword).addOnCompleteListener(
                                new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        progress.setVisibility(View.GONE);
                                        if (task.isSuccessful()) {
                                            FirebaseUser user = auth.getCurrentUser();
                                            getFavorites(user);
                                            updateUI(user);
                                        } else {
                                            Toast.makeText(LoginActivity.this, "Error ! " +
                                                            task.getException().getMessage(),
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                });
            }
        });
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            Intent intent = new Intent(LoginActivity.this, CategoryActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_SHORT).show();
        }
    }

    // gets favourites workouts saved in firebase by the user
    private void getFavorites(FirebaseUser user) {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        CollectionReference collRef = database.collection(user.getUid());
        try {
            collRef.get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                QuerySnapshot collection = task.getResult();
                                List<DocumentSnapshot> categories = collection.getDocuments();
                                for (int i=0; i<NCat; i++) {
                                    favorites[i] = new ArrayList<Integer>();
                                    int catIndex = findCatIndex(categories, i);
                                    if (catIndex == -1) {
                                        Log.e("ERROR", "Out of bounds");
                                    } else {
                                        DocumentSnapshot category = categories.get(catIndex);
                                        long size = (Long) category.get("Size");
                                        int j = 0;
                                        int cont = 0;
                                        while ((j < size) && (j < 100)) {
                                            try {
                                                long tmp = (long) category.get(Integer.toString(cont));
                                                favorites[i].add((int) tmp);
                                                j++;
                                                cont++;
                                            } catch (NullPointerException e) {
                                                cont++;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    });
        } catch(NullPointerException e) {
            Log.e("null", "ERROR: " + Arrays.toString(e.getStackTrace()));
        }
    }

    // sorts categories of workouts on firebase
    public static int findCatIndex(List<DocumentSnapshot> categories, int offset) {
        int catIndex;
        if (!categories.isEmpty()) {
            int k = 0;
            catIndex = -1;
            while ((k < NCat) && (catIndex == -1)) {
                if (Integer.parseInt(categories.get(k).getId()) == fCat + offset) {
                    catIndex = k;
                }
                k++;
            }
        }
        else {
            catIndex = -1;
        }
        return catIndex;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent (this, MainActivity.class);
        startActivity(intent);
    }
}