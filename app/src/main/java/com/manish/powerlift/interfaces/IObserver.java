package com.manish.powerlift.interfaces;

import com.manish.powerlift.db.Exercise;

import java.util.List;

public interface IObserver {
    void updateFutureExercises(List<Exercise> exercises);

    void updateExercises(List<Exercise> exercises);
}
