// interface used for ExerciseRepository
package com.fit.benefit.repositories;

import androidx.lifecycle.MutableLiveData;

import com.fit.benefit.models.Response;

public interface IExerciseRepository {
    MutableLiveData<Response> fetchExercise(long lastUpdate);
}
