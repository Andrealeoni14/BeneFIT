package com.fit.benefit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fit.benefit.data.ExerciseRecyclerViewAdapter;
import com.fit.benefit.models.Exercise;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ExerciseActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ExerciseRecyclerViewAdapter mAdapter;
    private ArrayList<Exercise> mExerciseList;
    private RequestQueue mRequestQueue;
    private ProgressBar mProgress;
    private ExerciseRecyclerViewAdapter.OnExerciseClickListener listener;
    //private JsonParsing json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercises);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(getTitle());
        mProgress = findViewById(R.id.loading);

        mRecyclerView = findViewById(R.id.exercises_list);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mExerciseList = new ArrayList<>();

        mRequestQueue = Volley.newRequestQueue(this);
        Intent intent = getIntent();
        int category = intent.getIntExtra("category", 0);
        //json.parseJSON(category, mRecyclerView, mAdapter, mExerciseList, mRequestQueue, mProgress, listener);
        parseJSON(category);
    }

    private void parseJSON(int category) {
        String url;
        if (category == 0) { // means the user wants to see the saved workouts
            url = "https://wger.de/api/v2/exercise/?language=2&limit=300&offset=0";
        }
        else { // request url with language set to English (there's German too with id = 1)
            url = "https://wger.de/api/v2/exercise/?language=2&limit=300&offset=0&category=" + category;
        }
        mProgress.setVisibility(View.VISIBLE);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("results");

                            for(int i = 0; i < jsonArray.length(); i++) {
                                JSONObject result = jsonArray.getJSONObject(i);

                                String name = result.getString("name");
                                String description = result.getString("description");
                                int id = result.getInt("id");

                                mExerciseList.add(new Exercise(id, name, description));
                                mProgress.setVisibility(View.GONE);
                            }

                            mAdapter = new ExerciseRecyclerViewAdapter(ExerciseActivity.this, mExerciseList, listener);
                            mRecyclerView.setAdapter(mAdapter);
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

        mRequestQueue.add(request);
    }
}