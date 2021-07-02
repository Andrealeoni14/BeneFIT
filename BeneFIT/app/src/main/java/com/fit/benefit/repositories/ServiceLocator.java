package com.fit.benefit.repositories;

import android.app.Application;

import com.fit.benefit.database.ExerciseRoomDatabase;
import com.fit.benefit.services.ExercisesService;
import com.fit.benefit.utils.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceLocator {

    private static ServiceLocator instance = null;

    private ServiceLocator() {}

    public static ServiceLocator getInstance() {
        if (instance == null) {
            synchronized(ServiceLocator.class) {
                instance = new ServiceLocator();
            }
        }
        return instance;
    }

    public ExercisesService getExercisesServiceWithRetrofit() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.EXERCISES_API_BASE_URL).
                addConverterFactory(GsonConverterFactory.create()).build();
        return retrofit.create(ExercisesService.class);
    }

    public ExerciseRoomDatabase getExerciseDao(Application application) {
        return ExerciseRoomDatabase.getDatabase(application);
    }

}
