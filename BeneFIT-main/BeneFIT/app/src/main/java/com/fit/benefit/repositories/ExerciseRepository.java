package com.fit.benefit.repositories;

import android.app.Application;
import android.util.Log;

import com.fit.benefit.database.ExerciseDao;
import com.fit.benefit.database.ExerciseRoomDatabase;
import com.fit.benefit.models.Exercise;
import com.fit.benefit.models.Response;
import com.fit.benefit.services.ExercisesService;
import com.fit.benefit.utils.Constants;
import com.fit.benefit.utils.ResponseCallback;
import com.fit.benefit.utils.ServiceLocator;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

import static android.content.ContentValues.TAG;

public class ExerciseRepository implements IExerciseRepository {

    private final ExercisesService exercisesService;
    private final ResponseCallback responseCallback;
    private final ExerciseDao exerciseDao;

    public ExerciseRepository(ResponseCallback responseCallback, Application application) {
        this.exercisesService = ServiceLocator.getInstance().getExercisesServiceWithRetrofit();
        this.responseCallback = responseCallback;
        ExerciseRoomDatabase db = ServiceLocator.getInstance().getExerciseDao(application);
        this.exerciseDao = db.exerciseDao();
    }

    @Override
    public void fetchExercises(long lastUpdate) {

        long currentTime = System.currentTimeMillis();

        if (currentTime - lastUpdate > Constants.FRESH_TIMEOUT) {

            Call<Response> call = exercisesService.getExercise(2, 300, 0, Constants.EXERCISES_API_KEY);

            call.enqueue(new Callback<Response>() {
                @Override
                public void onResponse(@NotNull Call<Response> call, @NotNull retrofit2.Response<Response> response) {
                    if (response.body() != null && response.isSuccessful()) {
                        long lastUpdate = System.currentTimeMillis();
                        List<Exercise> exerciseList = response.body().getExerciseList();
                        saveDataInDatabase(exerciseList);
                        responseCallback.onResponse(exerciseList, lastUpdate);
                    }
                }

                @Override
                public void onFailure(@NotNull Call<Response> call, @NotNull Throwable t) {
                    responseCallback.onFailure(t.getMessage());
                }
            });
        }
        else {
            Log.d(TAG, "Data read from the local database");
            readDataFromDatabase(lastUpdate);
        }
    }

    private void readDataFromDatabase(long lastUpdate) {
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