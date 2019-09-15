package com.manish.powerlift;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.util.Log;

import com.manish.powerlift.db.Exercise;

import java.util.List;

public class MainViewModel extends ViewModel {
    private static final String TAG = "MainViewModel";
    private MutableLiveData<List<Exercise>> mExercises;
    List<Exercise> data;
    Repository repository;

    public void init(Context context, String date) {
        repository = Repository.getInstance(context);
        mExercises = new MutableLiveData<>();
        data = repository.getData(date).getValue();
        mExercises.setValue(data);
    }

    public LiveData<List<Exercise>> getExercises() {
        Log.v(TAG, "Update exercises");
        if (mExercises == null) {
            Log.v(TAG, "Null exercise");
        }
        return mExercises;
    }

    public void setDate(String date) {
        Log.v(TAG, "Date=" + date);
        data = repository.getData(date).getValue();
        if(data == null){
            Log.v(TAG,"Data is null");
        }
        mExercises.setValue(data);
    }

    public void updateDB(Exercise exercise) {
        repository.insertData(exercise);
    }
}