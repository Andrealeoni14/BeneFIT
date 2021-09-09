package com.fit.benefit.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.fit.benefit.models.Exercise;

import java.util.List;

@Dao
public interface ExerciseDao {

    @Insert
    void insertExercise(List<Exercise> exercises);

    @Query("SELECT * FROM exercises")
    List<Exercise> getAllExercises();

    @Query("DELETE FROM exercises")
    void deleteAll();

    @Delete
    void deleteAllWithoutQuery(Exercise... exercises);
}
