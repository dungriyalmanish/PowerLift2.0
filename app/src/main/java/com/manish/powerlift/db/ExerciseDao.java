package com.manish.powerlift.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ExerciseDao {

    @Query("select * from exercise where date LIKE:date ORDER BY type")
    List<Exercise> getExerciseData(String date);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertExerciseData(Exercise exercise);

    @Query("select * from exercise where type LIKE:integer LIMIT 1")
    Exercise getLastDataOf(int integer);

    // @Update
    //void updateExerciseData(Exercise exercise);


}
