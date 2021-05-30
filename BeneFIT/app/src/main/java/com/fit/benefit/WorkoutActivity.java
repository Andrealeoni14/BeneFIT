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

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WorkoutActivity extends AppCompatActivity {

    private TextView mTextViewResult;
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home); // da definire quando leti finisce
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(getTitle());

        mTextViewResult = findViewById(R.id.text_view_result);
        mQueue = Volley.newRequestQueue(this);
        Intent intent = getIntent();
        int workout = intent.getIntExtra("workout", 0);

        jsonParse(workout);
    }

    private void jsonParse(int workout) {
        String url;
        // request url with language set to English (there's German too with id = 1)
        url = "https://wger.de/api/v2/exercise/?language=2&limit=300&offset=0&workout=" + workout;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try { // TUTTA PARTE DA FARE A TENTATIVI PER PARSING DELL'OGGETTO
                            JSONArray jsonArray = response.getJSONArray("results");

                            for (int i = 0; i < jsonArray.length(); i++){
                                JSONObject exercise = jsonArray.getJSONObject(i);

                                int id = exercise.getInt("id");
                                String name = exercise.getString("name");
                                //String language = lang.getString("short_name");

                                mTextViewResult.append(name + "\n\n");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mQueue.add(request);
    }
}