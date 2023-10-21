package app.fit.fitndflow.domain.model;

import java.io.Serializable;

public class SerieModel implements Serializable {
    int reps;
    double kg;
    ExerciseModel exercise;
    public SerieModel(int reps, double kg, ExerciseModel exercise) {
        this.reps = reps;
        this.kg = kg;
        this.exercise = exercise;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public double getKg() {
        return kg;
    }

    public void setKg(double kg) {
        this.kg = kg;
    }

    public ExerciseModel getExercise() {
        return exercise;
    }

    public void setExercise(ExerciseModel exercise) {
        this.exercise = exercise;
    }
}
