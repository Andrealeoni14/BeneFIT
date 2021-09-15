package com.fit.benefit.repositories;

import android.app.Application;
import android.util.Log;

import com.fit.benefit.data.ExerciseListFragmentRecyclerView;
import com.fit.benefit.database.ExerciseDao;
import com.fit.benefit.database.ExerciseRoomDatabase;
import com.fit.benefit.models.Exercise;
import com.fit.benefit.models.Response;
import com.fit.benefit.services.ExercisesService;
import com.fit.benefit.utils.Constants;
import com.fit.benefit.utils.ServiceLocator;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

public class ExerciseRepository implements IExerciseRepository {

    private final ExercisesService exerciseService;
    private final ExerciseDao mExerciseDao;

    private final Application application;
    private final MutableLiveData<Response> mResponseLiveData;

    public ExerciseRepository(ExerciseDao dao, Application application) {
        this.application = application;
        this.exerciseService = ServiceLocator.getInstance().getExercisesServiceWithRetrofit();
        ExerciseRoomDatabase db = ServiceLocator.getInstance().getExerciseDao(application);
        dao = db.exerciseDao();
        this.mExerciseDao = dao;
        this.mResponseLiveData = new MutableLiveData<>();
    }
    public ExerciseRepository(ExerciseListFragmentRecyclerView exerciseListFragmentRecyclerView, Application application) {
        this.application = application;
        this.exerciseService = ServiceLocator.getInstance().getExercisesServiceWithRetrofit();
        ExerciseRoomDatabase db = ServiceLocator.getInstance().getExerciseDao(application);
        this.mExerciseDao = db.exerciseDao();
        this.mResponseLiveData = new MutableLiveData<>();
    }

    @Override
    public MutableLiveData<Response> fetchExercise(long lastUpdate) {
        long currentTime = System.currentTimeMillis();

        if (currentTime - lastUpdate > Constants.FRESH_TIMEOUT) {
            Call<Response> call = exerciseService(2, 300, 0, Constants.EXERCISES_API_KEY);

            call.enqueue(new Callback<Response>() {
                @Override
                public void onResponse(@NonNull Call<Response> call, @NonNull retrofit2.Response<Response> response) {
                    if (response.body() != null && response.isSuccessful()) {
                        long setLastUpdate = System.currentTimeMillis();
                        List<Exercise> exerciseList = response.body().getExerciseList();
                        saveDataInDatabase(exerciseList);
                        mResponseLiveData.postValue(response.body());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Response> call, @NonNull Throwable t) {
                    mResponseLiveData.postValue(new Response(t.getMessage(), -1, null, false));
                }
            });
        } else {
            Log.d(TAG, "Data read from the local database");
            readDataFromDatabase();
        }

        return mResponseLiveData;
    }

    @Override
    public void fetchExercises(long lastUpdate) {
    }

    public void readDataFromDatabase() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                List<Exercise> exerciseList = mExerciseDao.getAllExercises();
                mResponseLiveData.postValue(new Response(null, exerciseList.size(), exerciseList, false));
            }
        };
        new Thread(runnable).start();
    }

    public void saveDataInDatabase(List<Exercise> exerciseList) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                mExerciseDao.deleteAll();
                mExerciseDao.insertExercise(exerciseList);
            }
        };
        new Thread(runnable).start();
    }

    private void addExercise(List<Exercise> exerciseList) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                mExerciseDao.insertExercise(exerciseList);
            }
        };
        new Thread(runnable).start();
    }

    private Call<Response> exerciseService(int i, int i1, int i2, String exercisesApiKey) {
        return null;
    }
}