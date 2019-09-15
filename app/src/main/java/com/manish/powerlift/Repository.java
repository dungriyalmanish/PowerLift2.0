package com.manish.powerlift;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.manish.powerlift.db.Exercise;
import com.manish.powerlift.db.ExerciseDao;
import com.manish.powerlift.db.ExerciseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Repository {
    private static final String TAG = "Repository";
    static Repository instance;
    static private ExerciseDao exerciseDao;
    static MutableLiveData<List<Exercise>> exercises;
    // static MutableLiveData<Boolean> isDataInserted;

    public static Repository getInstance(Context context) {
        if (instance == null) {
            instance = new Repository();
            exerciseDao = ExerciseDatabase.getInstance(context).getExerciseDao();
        }
        return instance;
    }

    public void insertData(Exercise exercise) {
        //isDataInserted.setValue(false);
        new InsertTask(exerciseDao).execute(exercise);
    }

    public MutableLiveData<List<Exercise>> getLastExercises() {
        return exercises;
    }

    public LiveData<List<Exercise>> getData(String date) {
        return exerciseDao.getExerciseData(date);
    } ///install sqlite in check the data

    public void getLastExercises(Integer[] types) {
        new GetLastExTask(exerciseDao).execute(types);
    }

    private static class InsertTask extends AsyncTask<Exercise, Void, Void> {

        ExerciseDao dao;

        private InsertTask(ExerciseDao exerciseDao) {
            dao = exerciseDao;
        }

        @Override
        protected Void doInBackground(Exercise... exercises) {
            Log.v(TAG, "Updating data :" + exercises[0]);
            dao.insertExerciseData(exercises[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            //isDataInserted.setValue(true);
            Log.v(TAG, "Data updated :)");
        }
    }

    private static class GetLastExTask extends AsyncTask<Integer, Void, Void> {
        List<Exercise> list = new ArrayList<>();
        ExerciseDao ed;

        GetLastExTask(ExerciseDao dao) {
            ed = dao;
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            list.add(ed.getLastDataOf((int) integers[0]));
            list.add(ed.getLastDataOf((int) integers[1]));
            list.add(ed.getLastDataOf((int) integers[2]));
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            exercises.setValue(list);
        }
    }
}
