package com.fit.benefit;

import android.content.Intent;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import static com.fit.benefit.ExerciseActivity.EXTRA_DESC;
import static com.fit.benefit.ExerciseActivity.EXTRA_IMAGE;
import static com.fit.benefit.ExerciseActivity.EXTRA_NAME;

public class WorkoutActivity extends AppCompatActivity {

    //private TextView mTextViewResult;
    //private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //mTextViewResult = findViewById(R.id.text_view_result);
        //mQueue = Volley.newRequestQueue(this);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        //int workout = intent.getIntExtra("workout", 0);
        String imageUrl = extras.getString(EXTRA_IMAGE);
        String name = extras.getString(EXTRA_NAME);
        String description = extras.getString(EXTRA_DESC);

        ImageView im = findViewById(R.id.exe_image);
        TextView textName = findViewById(R.id.exe_name);
        TextView textDescription = findViewById(R.id.exe_description);

        textName.setText(name);
        textDescription.setText(description);
        Picasso.with(this).load(imageUrl).fit().centerInside().into(im);
    }

    public void logoutClick(android.view.View view) {
        LogoutActivity logout = new LogoutActivity();
        logout.logoutFunction(WorkoutActivity.this);
    }
}