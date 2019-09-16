package com.manish.powerlift;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.util.Log;

import com.manish.powerlift.db.Exercise;
import com.manish.powerlift.interfaces.IObserver;
import com.manish.powerlift.utils.ExUtils;

import java.util.List;

public class MainViewModel extends ViewModel implements IObserver {
    private static final String TAG = "MainViewModel";
    private MutableLiveData<List<Exercise>> mExercises;
    private MutableLiveData<Boolean> isChecking = new MutableLiveData<>();
    List<Exercise> data;
    private static String mDate;
    private String tempDate;
    Repository repository;

    public void init(Context context, String date) {
        Log.v(TAG, "init()");
        repository = Repository.getInstance(this, context);
        mExercises = new MutableLiveData<>();
        mDate = date;
        setIsChecking(true);
        repository.getData(mDate);
    }


    public LiveData<List<Exercise>> getExercises() {
        Log.v(TAG, "Update exercises");
        if (mExercises == null) {
            Log.v(TAG, "Null exercise");
        }
        return mExercises;
    }

    public MutableLiveData<Boolean> isChecking() {
        return isChecking;
    }

    public void setIsChecking(boolean check) {
        isChecking.setValue(check);
    }

    public void setDate(String date) {
        Log.v(TAG, "Selected Date=" + date + "  Today's date=" + mDate);
        tempDate = date;
        if (mDate.compareTo(date) < 0) {
            Log.v(TAG, "Selected Date is future");
            generateDataForFuture();
            return;
        }
        repository.getData(date);
    }

    @Override
    public void updateFutureExercises(List<Exercise> exercises) {
        mExercises.setValue(ExUtils.getNextToDo(exercises));
        setIsChecking(false);
    }

    @Override
    public void updateExercises(List<Exercise> exercises) {
        Log.v(TAG, "updateExercises: " + exercises);
        if ((exercises == null || exercises.size() == 0) && mDate.equals(tempDate)) {
            generateDataForFuture();
        } else {
            mExercises.setValue(exercises);
            setIsChecking(false);
        }
    }

    private void generateDataForFuture() {
        Log.v(TAG, "generateDataForDate:");
        repository.getLastExercises();
    }

}