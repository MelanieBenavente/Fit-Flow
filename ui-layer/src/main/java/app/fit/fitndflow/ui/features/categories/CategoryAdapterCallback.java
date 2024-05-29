package app.fit.fitndflow.ui.features.categories;

import app.fit.fitndflow.domain.model.CategoryModel;

public interface CategoryAdapterCallback {

    void showExercises(CategoryModel category);

    void showCreationDialog ();

    void showModifyDialog(int id, String name);

    void showDeleteDialog(int id);
}
