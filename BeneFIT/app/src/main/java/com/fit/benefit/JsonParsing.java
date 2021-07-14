package com.fit.benefit;

import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.fit.benefit.data.ExerciseRecyclerViewAdapter;
import com.fit.benefit.models.Exercise;
import com.fit.benefit.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonParsing {

    public Context context;
    public ExerciseRecyclerViewAdapter mAdapter;

    public void parseJSON(int category, RecyclerView mRecyclerView, ArrayList<Exercise> mExerciseList,
                          RequestQueue mRequestQueue, ProgressBar mProgress, ExerciseRecyclerViewAdapter.OnExerciseClickListener mListener) {
        String url;
        if (category == 0) { // means the user wants to see the saved workouts
            url = Constants.EXERCISES_API_BASE_URL;
        }
        else { // request url with language set to English (there's German too with id = 1)
            url = Constants.EXERCISES_API_CATEGORY_URL + category;
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
                                String img_url = imageJSON(img, mRequestQueue);

                                mExerciseList.add(new Exercise(id, name, description, img_url));
                                mProgress.setVisibility(View.GONE);
                            }

                            mAdapter = new ExerciseRecyclerViewAdapter(context, mExerciseList, mListener);
                            mRecyclerView.setAdapter(mAdapter);
                            mAdapter.setOnExerciseClickListener((ExerciseRecyclerViewAdapter.OnExerciseClickListener) JsonParsing.this);

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

    private String imageJSON(int img, RequestQueue mRequestQueue) {
        final String[] img_url = new String[1];
        String new_url = Constants.EXERCISES_IMAGE_API_URL + img;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, new_url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("results");

                            if(jsonArray != null & jsonArray.getJSONObject(0) != null) {
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
}


