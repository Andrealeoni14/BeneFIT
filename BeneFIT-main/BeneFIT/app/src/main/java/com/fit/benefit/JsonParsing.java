package com.fit.benefit;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonParsing {

    public void jsonParse(TextView mTextViewResult, RequestQueue mQueue, ProgressBar mProgress, int category) {
        String url;
        mProgress.setVisibility(View.VISIBLE);
        if (category == 0) { // means the user wants to see the saved workouts
            url = "https://wger.de/api/v2/exercise/?language=2&limit=300&offset=0";
        }
        else { // request url with language set to English (there's German too with id = 1)
            url = "https://wger.de/api/v2/exercise/?language=2&limit=300&offset=0&category=" + category;
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("results");

                            for (int i = 0; i < jsonArray.length(); i++){
                                JSONObject exercise = jsonArray.getJSONObject(i);

                                //JSONObject lang = exercise.getJSONObject("language");

                                int id = exercise.getInt("id");
                                String name = exercise.getString("name");
                                //String language = lang.getString("short_name");

                                mTextViewResult.append(name + "\n\n");
                                mProgress.setVisibility(View.GONE);
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
