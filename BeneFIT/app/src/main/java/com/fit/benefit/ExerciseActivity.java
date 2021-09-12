// lists the workouts of a given category or the user saved ones
package com.fit.benefit;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.fit.benefit.utils.Constants;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.*;
import java.util.ArrayList;

import static com.fit.benefit.LoginActivity.NCat;
import static com.fit.benefit.LoginActivity.fCat;
import static com.fit.benefit.LoginActivity.favorites;

public class ExerciseActivity extends AppCompatActivity implements ExerciseRecyclerViewAdapter.OnExerciseClickListener {

    public static final String EXTRA_NAME = "name";
    public static final String EXTRA_DESC = "description";
    public static final String EXTRA_IMAGE = "image";
    public static final String EXTRA_CAT = "category";
    public static final String EXTRA_INDEX = "index";

    private RecyclerView mRecyclerView;
    private final ArrayList<Exercise> mExerciseList = new ArrayList<>();
    private ExerciseRecyclerViewAdapter.OnExerciseClickListener listener;
    private ExerciseRecyclerViewAdapter mAdapter;
    private RequestQueue mRequestQueue;
    private ProgressBar mProgress;
    private int imgCont =0;
    private int i = 0;
    private int j = 0;
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
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);

        mRequestQueue = Volley.newRequestQueue(this);
        Intent intent = getIntent();
        int category = intent.getIntExtra("category", 0);
        //json.parseJSON(category, mRecyclerView, mExerciseList, mRequestQueue, mProgress, mListener);
        parseJSON(category);
    }

    // analyze the JSON response and fetches the data for the workouts
    private void parseJSON(int category) {
        String url;
        if (category == 0) { // means the user wants to see the saved workouts
            mProgress.setVisibility(View.VISIBLE);
            for(i = 0; i < NCat; i++) {
                if(!favorites[i].isEmpty()) {
                    url = Constants.EXERCISES_API_CATEGORY_URL + (i+fCat);
                    JsonObjectRequest request = new JsonObjectRequest(url, null,
                            new Response.Listener<JSONObject>(){
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        JSONArray jsonArray = response.getJSONArray(
                                                "results");
                                        int category = Integer.parseInt(jsonArray
                                                .getJSONObject(0).getString("category"));
                                        int catIndex = category - fCat;
                                        Log.e("tag_cat_en", Integer.toString(catIndex));
                                        for(int j = 0; j<favorites[catIndex].size(); j++) {
                                            JSONObject result = jsonArray.getJSONObject(
                                                    favorites[catIndex].get(j));
                                            String name = result.getString("name");
                                            String description = Jsoup.parse(result.getString(
                                                    "description")).text();
                                            int id =result.getInt("id");
                                            int img = result.getInt("exercise_base");
                                            imageJSON(img);
                                            mExerciseList.add(new Exercise(id, name, description,
                                                    null, category,
                                                    favorites[catIndex].get(j)));
                                            mProgress.setVisibility(View.GONE);
                                            Log.e("tag_cat_ex", Integer.toString(id));
                                        }
                                        mAdapter = new ExerciseRecyclerViewAdapter(
                                                ExerciseActivity.this, mExerciseList, listener);
                                        mRecyclerView.setAdapter(mAdapter);
                                        mAdapter.setOnExerciseClickListener(ExerciseActivity.this);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, null);
                    mRequestQueue.add(request);
                }
                mProgress.setVisibility(View.GONE);
            }
        }
        url = Constants.EXERCISES_API_CATEGORY_URL + category;
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
                                String description = Jsoup.parse(result.getString(
                                        "description")).text();
                                int id = result.getInt("id");
                                int img = result.getInt("exercise_base");

                                imageJSON(img);
                                mExerciseList.add(new Exercise(id, name, description, null,
                                        category, i));
                                mProgress.setVisibility(View.GONE);
                            }
                            mAdapter = new ExerciseRecyclerViewAdapter(ExerciseActivity.this,
                                    mExerciseList, listener);
                            mRecyclerView.setAdapter(mAdapter);
                            mAdapter.setOnExerciseClickListener(ExerciseActivity.this);

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


    // if available, it takes the workout image from the JSON file
    private void imageJSON(int img) {
        String new_url = Constants.EXERCISES_IMAGE_API_URL + img;
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, new_url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String url;
                        try {
                            JSONArray jsonArray = response.getJSONArray("results");

                            if (jsonArray.length() != 0 &&
                                    jsonArray.getJSONObject(0) != null) {
                                JSONObject result = jsonArray.getJSONObject(0);
                                url = result.getString("image");
                            } else
                                url = null;
                        } catch (JSONException e) {
                            url = null;
                        }
                        mExerciseList.get(imgCont).setImg(url);
                        imgCont++;
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mRequestQueue.add(request);
    }

    // when the user clicks on a workout it passes all the data to the workout activity
    @Override
    public void onExerciseClick(Exercise exercise) {
        Intent detail = new Intent(this, WorkoutActivity.class);
        detail.putExtra(EXTRA_NAME, exercise.getName());
        detail.putExtra(EXTRA_DESC, exercise.getDescription());
        detail.putExtra(EXTRA_IMAGE, exercise.getImg());
        detail.putExtra(EXTRA_CAT, exercise.getCategory());
        detail.putExtra(EXTRA_INDEX, exercise.getIndex());
        startActivity(detail);
    }

    public void logoutClick(android.view.View view) {
        LogoutActivity logout = new LogoutActivity();
        logout.logoutFunction(ExerciseActivity.this);
    }
}