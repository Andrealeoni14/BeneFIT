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
        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(getTitle());

        //mTextViewResult = findViewById(R.id.text_view_result);
        //mQueue = Volley.newRequestQueue(this);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        //int workout = intent.getIntExtra("workout", 0);
        String imageUrl = extras.getString("EXTRA_IMAGE");
        String name = extras.getString("EXTRA_NAME");
        String description = extras.getString("EXTRA_DESC");

        ImageView im = findViewById(R.id.exe_image);
        TextView n = findViewById(R.id.exe_name);
        TextView d = findViewById(R.id.exe_description);

        n.setText(name);
        d.setText(description);
        Picasso.with(this).load(imageUrl).fit().centerInside().into(im);


        //jsonParse(workout);
    }

    /*private void jsonParse(int workout) {
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
    }*/
}