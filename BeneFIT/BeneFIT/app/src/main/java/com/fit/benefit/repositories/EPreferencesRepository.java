package com.fit.benefit.repositories;

import androidx.lifecycle.MutableLiveData;

import com.fit.benefit.data.model.LoggedInUser;

public interface EPreferencesRepository {
    MutableLiveData<Boolean> saveFavoriteExercise(String userId, String exercise);
    MutableLiveData<LoggedInUser> getFavExercise(String userId);
}

