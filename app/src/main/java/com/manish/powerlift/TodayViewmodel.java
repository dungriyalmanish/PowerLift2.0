package com.manish.powerlift;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.manish.powerlift.db.Exercise;
import com.manish.powerlift.interfaces.IToday;

import java.util.List;

public class TodayViewmodel extends ViewModel implements IToday {

    private Repository repo;
    private MutableLiveData<Boolean> isInProgress = new MutableLiveData<>();
    private LiveData<List<Exercise>> mExercises;

    public void init(Context context, String date) {
        repo = Repository.getInstance(this, context);
        //mExercises = repo.getData(date);
    }

    public MutableLiveData<Boolean> getProgress() {
        return isInProgress;
    }

    public void updateDB(int num, Exercise exercise) {
        repo.setLatestExercise(num, exercise.getType());
        isInProgress.setValue(true);
        repo.insertData(exercise);
    }

    @Override
    public void insertedData() {
        isInProgress.setValue(false);
    }
}
