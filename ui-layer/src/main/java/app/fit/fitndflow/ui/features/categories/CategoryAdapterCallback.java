package app.fit.fitndflow.ui.features.categories;


import com.fit.fitndflow.app.domain.common.models.CategoryModel;

public interface CategoryAdapterCallback {

    void showExercises(CategoryModel category);

    void showCreationDialog ();

    void showModifyDialog(int id, String name);

    void showDeleteDialog(int id);
}
