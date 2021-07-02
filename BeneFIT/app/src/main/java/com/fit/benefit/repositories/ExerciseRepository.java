package com.fit.benefit.repositories;

import android.app.Application;

import com.fit.benefit.database.ExerciseDao;
import com.fit.benefit.database.ExerciseRoomDatabase;
import com.fit.benefit.models.Exercise;
import com.fit.benefit.models.Response;
import com.fit.benefit.services.ExercisesService;
import com.fit.benefit.utils.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class ExerciseRepository implements IExerciseRepository {

    private final ExercisesService exercisesService;
    private final ResponseCallback responseCallback;
    private final ExerciseDao exerciseDao;
    private long lastUpdate = 0;

    public ExerciseRepository(ResponseCallback responseCallback, Application application) {
        this.exercisesService = ServiceLocator.getInstance().getExercisesServiceWithRetrofit();
        this.responseCallback = responseCallback;
        ExerciseRoomDatabase db = ServiceLocator.getInstance().getExerciseDao(application);
        this.exerciseDao = db.exerciseDao();
    }

    @Override
    public void fetchExercises() {

        long currentTime = System.currentTimeMillis();

        if (currentTime - lastUpdate > Constants.FRESH_TIMEOUT) {

            Call<Response> call = exercisesService.getExercise("en", Constants.EXERCISES_API_KEY);

            call.enqueue(new Callback<Response>() {
                @Override
                public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                    if (response.body() != null && response.isSuccessful()) {
                        lastUpdate = System.currentTimeMillis();
                        List<Exercise> exerciseList = response.body().getExerciseList();
                        saveDataInDatabase(exerciseList);
                        responseCallback.onResponse(response.body().getExerciseList(), lastUpdate);
                    }
                }

                @Override
                public void onFailure(Call<Response> call, Throwable t) {
                    responseCallback.onFailure(t.getMessage());
                }
            });
        }
    }

    private void readDataFromDatabase() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                responseCallback.onResponse(exerciseDao.getAllExercises(), lastUpdate);
            }
        };
        new Thread(runnable).start();
    }

    public void saveDataInDatabase (List<Exercise> exerciseList){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
               exerciseDao.deleteAll();
                exerciseDao.insertExercise(exerciseList);
            }
        };
        new Thread(runnable).start();
    }
}