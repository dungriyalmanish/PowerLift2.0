package com.manish.powerlift.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Exercise.class}, version = 1, exportSchema = false)
public abstract class ExerciseDatabase extends RoomDatabase {
    private static ExerciseDatabase instance;

    public static synchronized ExerciseDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), ExerciseDatabase.class, "exercise_db").fallbackToDestructiveMigration().build();
        }
        return instance;
    }

    public abstract ExerciseDao getExerciseDao();
}
