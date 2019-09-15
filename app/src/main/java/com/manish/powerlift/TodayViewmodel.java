package com.manish.powerlift;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.manish.powerlift.db.Exercise;

import java.util.List;

public class TodayViewmodel extends ViewModel {

    private Repository repo;
    private MutableLiveData<Boolean> isInProgress = new MutableLiveData<>();
    private LiveData<List<Exercise>> mExercises;

    public void init(Context context, String date) {
        repo = Repository.getInstance(context);
        mExercises = repo.getData(date);
        /*repo.dataInserted().observe(, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean) {
                    isInProgress.setValue(false);
                }
            }
        });*/
    }

    public MutableLiveData<Boolean> getProgress() {
        return isInProgress;
    }

    public void updateDB(Exercise exercise) {
        isInProgress.setValue(true);
        repo.insertData(exercise);
        isInProgress.setValue(false);
    }


}
