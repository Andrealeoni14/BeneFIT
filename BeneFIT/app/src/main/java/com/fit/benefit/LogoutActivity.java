package com.fit.benefit;

import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.fit.benefit.LoginActivity.favorites;

public class LogoutActivity extends AppCompatActivity {
    public FirebaseUser user;

    public LogoutActivity() {
        user = FirebaseAuth.getInstance().getCurrentUser();
    }

    public void logoutFunction(Context context) {
        if(user != null) {
            FirebaseAuth.getInstance().signOut();
			favorites = null;
        }
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }
}