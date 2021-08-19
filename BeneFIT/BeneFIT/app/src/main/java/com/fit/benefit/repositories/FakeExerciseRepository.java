package com.fit.benefit.repositories;

import android.app.Activity;

import com.fit.benefit.models.Exercise;
import com.fit.benefit.models.Response;
import com.fit.benefit.utils.ResponseCallback;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class FakeExerciseRepository implements IExerciseRepository{

    private static final String TAG = "FakeExerciseRepository";
    private final ResponseCallback responseCallback;
    private final Activity activity;

    public FakeExerciseRepository(ResponseCallback responseCallback, Activity activity) {
        this.responseCallback = responseCallback;
        this.activity = activity;
    }

    @Override
    public void fetchExercises(long lastUpdate) {
        responseCallback.onResponse(getExercisesWithGson(), System.currentTimeMillis());

    }

    private List<Exercise> getExercisesWithGson() {
        InputStream fileInputStream = null;
        JsonReader jsonReader = null;
        try {
            fileInputStream = activity.getAssets().open("news.json");
            jsonReader = new JsonReader(new InputStreamReader(fileInputStream, "UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
        Response response = new Gson().fromJson(bufferedReader, Response.class);

        return response.getExerciseList();
    }
}
