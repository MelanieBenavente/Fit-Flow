package app.fit.fitndflow.ui.features.common.component;

import app.fit.fitndflow.domain.model.ExcerciseModel;

public interface CategoryEditableListener {

    void onClickAdd(ExcerciseModel excercise);

    void onClickDelete(int position);
}
