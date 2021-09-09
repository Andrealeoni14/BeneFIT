package com.fit.benefit.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.fit.benefit.models.Exercise;
import com.fit.benefit.utils.Constants;

@Database(entities = {Exercise.class}, version = Constants.DATABASE_VERSION)
public abstract class ExerciseRoomDatabase extends RoomDatabase {

    private static volatile ExerciseRoomDatabase INSTANCE;

    public abstract ExerciseDao exerciseDao();

    public static ExerciseRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ExerciseRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            ExerciseRoomDatabase.class, Constants.DATABASE_NAME).build();
                }
            }
        }
        return INSTANCE;
    }
}
