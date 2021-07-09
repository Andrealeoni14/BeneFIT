/*package com.fit.benefit;

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

    public void parseJSON(int category, RecyclerView mRecyclerView, ExerciseRecyclerViewAdapter mAdapter, ArrayList<Exercise> mExerciseList,
                           RequestQueue mRequestQueue, ProgressBar mProgress, ExerciseRecyclerViewAdapter.OnExerciseClickListener listener) {
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
                            //problemi con adapter
                            mAdapter = new ExerciseRecyclerViewAdapter(context, mExerciseList, listener);
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
*/

