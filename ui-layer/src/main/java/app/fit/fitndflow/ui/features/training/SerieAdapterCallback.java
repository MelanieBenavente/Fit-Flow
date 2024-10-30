package app.fit.fitndflow.ui.features.training;


import com.fit.fitndflow.app.domain.common.models.ExerciseModel;

public interface SerieAdapterCallback {

    void showSeries(ExerciseModel exercise);

    void showCreationDialog ();

    void showDeleteDialog(int id);

    void showModifyDialog(int exerciseId, String exerciseName);
}
