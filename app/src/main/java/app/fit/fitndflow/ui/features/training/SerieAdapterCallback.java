package app.fit.fitndflow.ui.features.training;

import app.fit.fitndflow.domain.model.ExerciseModel;

public interface SerieAdapterCallback {

    void showSeries(ExerciseModel exercise);

    void showCreationDialog ();

    void showDeleteDialog(int id);

    void showModifyDialog(int exerciseId, String exerciseName);
}
