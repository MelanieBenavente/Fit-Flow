package app.fit.fitndflow.ui.features.categories;

import java.util.List;

import app.fit.fitndflow.domain.model.ExcerciseModel;

public interface CategoryAdapterCallback {

    void showExcercises(List<ExcerciseModel> excerciseList);

}
