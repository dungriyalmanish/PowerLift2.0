package com.manish.powerlift;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.manish.powerlift.db.Exercise;
import com.manish.powerlift.db.ExerciseDao;
import com.manish.powerlift.db.ExerciseDatabase;
import com.manish.powerlift.interfaces.IObserver;
import com.manish.powerlift.interfaces.IToday;
import com.manish.powerlift.utils.DataConstants;

import java.util.ArrayList;
import java.util.List;

public class Repository {
    private static final String TAG = "Repository";
    static Repository instance;
    private static IObserver callback;
    private static IToday toadyCallback;
    static private ExerciseDao exerciseDao;
    private static SharedPreferences sharedPreferences;

    public static <T> Repository getInstance(T obj, Context context) {
        if (instance == null) {
            instance = new Repository();
            exerciseDao = ExerciseDatabase.getInstance(context).getExerciseDao();
            sharedPreferences = context.getSharedPreferences(DataConstants.SHARED_PREF, Context.MODE_PRIVATE);
        }
        if (obj instanceof IToday) {
            toadyCallback = (IToday) obj;
        } else if (obj instanceof IObserver) {
            callback = (IObserver) obj;
        }
        return instance;
    }

    public void insertData(Exercise exercise) {
        new InsertTask(exerciseDao).execute(exercise);
    }

    public void setLatestExercise(int num, int type) {
        sharedPreferences.edit().putInt(String.valueOf(num), type).apply();
    }

    public void getData(String date) {
        new GetDataTask(exerciseDao).execute(date);
        //return exerciseDao.getExerciseData(date);
    }

    public void getLastExercises() {
        Integer[] i = new Integer[3];
        i[0] = 1;
        i[1] = sharedPreferences.getInt("1", 2);
        i[2] = sharedPreferences.getInt("2", 3);
        if ((i[1] == 2 && i[2] == 3) || (i[1] == 3 && i[2] == 2)) {
            i[1] = 4;
            i[2] = 5;
        } else {
            i[1] = 2;
            i[2] = 3;
        }
        Log.v(TAG, "getLastExercises: exercises are " + i);
        new GetLastExTask(exerciseDao).execute(i);
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
            if (toadyCallback != null) {
                toadyCallback.insertedData();
            }
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
            Exercise e;
            for (int i = 0; i < 3; i++) {
                e = ed.getLastDataOf(integers[i]);
                if (e != null) {
                    Log.v(TAG, "GetLastExTask: doInBackground : ex=" + e.toString());
                    list.add(e);
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            callback.updateFutureExercises(list);
        }
    }

    private static class GetDataTask extends AsyncTask<String, Void, Void> {

        ExerciseDao ed;
        List<Exercise> exercises;

        GetDataTask(ExerciseDao dao) {
            ed = dao;
        }

        @Override
        protected Void doInBackground(String... strings) {
            exercises = ed.getExerciseData(strings[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (callback != null) {
                callback.updateExercises(exercises);
            }
        }
    }
}
