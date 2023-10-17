package app.fit.fitndflow.ui.features.training;

import app.fit.fitndflow.domain.model.SerieModel;

public interface SerieEditableListener {

    void onClickAdd(SerieModel serie);

    void onClickDelete(int position);
}
