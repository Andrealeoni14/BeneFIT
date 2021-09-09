package com.fit.benefit.repositories;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.fit.benefit.data.model.LoggedInUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.NotNull;

import static com.fit.benefit.utils.Constants.FAVORITE_EXERCISE;
import static com.fit.benefit.utils.Constants.FIREBASE_DB;
import static com.fit.benefit.utils.Constants.USER;


public class PreferencesRepository implements EPreferencesRepository {

    private static final String TAG = "PreferencesRepository";

    private final DatabaseReference db;
    private final MutableLiveData<Boolean> response;
    private final MutableLiveData<LoggedInUser> exerciseResponse;

    public PreferencesRepository() {
        db = FirebaseDatabase.getInstance(FIREBASE_DB).getReference();
        this.response = new MutableLiveData<>();
        this.exerciseResponse = new MutableLiveData<>();
    }


    @Override
    public MutableLiveData<Boolean> saveFavoriteExercise(String userId, String exercise) {
        db.child(USER).child(userId).child(FAVORITE_EXERCISE).setValue(exercise).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                response.setValue(true);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Log.d(TAG, e.getMessage());
                response.setValue(false);
            }
        });

        return response;
    }

    @Override
    public MutableLiveData<LoggedInUser> getFavExercise(String userId) {
        db.child("users").child(userId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, String.valueOf(task.getResult().getValue(LoggedInUser.class)));
                    LoggedInUser user = new LoggedInUser();
                    user.setDisplayName(task.getResult().getValue(LoggedInUser.class).getDisplayName());
                    user.setFavExercise(task.getResult().getValue(LoggedInUser.class).getFavExercise());
                    exerciseResponse.postValue(user);
                }
                else {
                    Log.e(TAG, "Error getting data", task.getException());
                    exerciseResponse.postValue(null);
                }
            }
        });
        return exerciseResponse;
    }
}