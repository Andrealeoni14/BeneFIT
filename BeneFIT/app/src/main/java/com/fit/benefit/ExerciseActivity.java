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

public class ExerciseActivity extends AppCompatActivity implements ExerciseRecyclerViewAdapter.OnExerciseClickListener {

    public static final String EXTRA_NAME = "name";
    public static final String EXTRA_DESC = "description";
    public static final String EXTRA_IMAGE = "image";

    private RecyclerView mRecyclerView;
    private ExerciseRecyclerViewAdapter mAdapter;
    private ArrayList<Exercise> mExerciseList;
    private RequestQueue mRequestQueue;
    private ProgressBar mProgress;
    private ExerciseRecyclerViewAdapter.OnExerciseClickListener mListener;
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
        //json.parseJSON(category, mRecyclerView, mExerciseList, mRequestQueue, mProgress, mListener);
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
                                int img = result.getInt("exercise_base");
                                String img_url = imageJSON(img);

                                mExerciseList.add(new Exercise(id, name, description, img_url));
                                mProgress.setVisibility(View.GONE);
                            }

                            mAdapter = new ExerciseRecyclerViewAdapter(ExerciseActivity.this, mExerciseList, mListener);
                            mRecyclerView.setAdapter(mAdapter);
                            mAdapter.setOnItemClickListener(ExerciseActivity.this);

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

    private String imageJSON(int img) {
        final String[] img_url = new String[1];
        String new_url = "https://wger.de/api/v2/exerciseimage/?is_main=true&exercise_base=" + img;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, new_url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("results");

                            if(jsonArray != null) {
                                JSONObject result = jsonArray.getJSONObject(0);
                                img_url[0] = result.getString("image");
                            }
                            else
                                img_url[0] = null;
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
        return img_url[0];
    }

    /*@Override
    public void onItemClick(int position) {
        Intent detail = new Intent(this, WorkoutActivity.class);
        Exercise clickedExercise = mExerciseList.get(position);
        detail.putExtra("EXTRA_NAME", clickedExercise.getName());
        detail.putExtra("EXTRA_DESC", clickedExercise.getDescription());
        detail.putExtra("EXTRA_IMAGE", clickedExercise.getImg());
        startActivity(detail);
    }*/

    @Override
    public void onExerciseClick(Exercise exercise) {
        Intent detail = new Intent(this, WorkoutActivity.class);
        //Exercise clickedExercise = mExerciseList.get(position);
        detail.putExtra("EXTRA_NAME", exercise.getName());
        detail.putExtra("EXTRA_DESC", exercise.getDescription());
        detail.putExtra("EXTRA_IMAGE", exercise.getImg());
        startActivity(detail);
    }

    public void logoutClick(android.view.View view) {
        LogoutActivity logout = new LogoutActivity();
        logout.logoutFunction(ExerciseActivity.this);
    }
}