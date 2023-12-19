package app.fit.fitndflow.ui.features.categories;

import app.fit.fitndflow.domain.model.CategoryModel;

public interface CategoryAdapterCallback {

    void showExercises(CategoryModel category);

    void showCreationDialog ();

    //todo crear metodo para mostrar dialogo de borrar y pasarle un int id por parametro
    void showDeleteDialog(int id);
}
