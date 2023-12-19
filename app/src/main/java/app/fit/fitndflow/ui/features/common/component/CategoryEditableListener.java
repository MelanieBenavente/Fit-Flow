package app.fit.fitndflow.ui.features.common.component;

import app.fit.fitndflow.domain.model.ExerciseModel;

@Deprecated
public interface CategoryEditableListener {

    void onClickAdd(ExerciseModel exercise);

    void onClickDelete(int position);
}
