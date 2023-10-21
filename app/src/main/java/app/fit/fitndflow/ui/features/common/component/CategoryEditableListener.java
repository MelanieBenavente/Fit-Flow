package app.fit.fitndflow.ui.features.common.component;

import app.fit.fitndflow.domain.model.ExerciseModel;

public interface CategoryEditableListener {

    void onClickAdd(ExerciseModel exercise);

    void onClickDelete(int position);
}
