package app.fit.fitndflow.ui.features.categories;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import app.fit.fitndflow.domain.model.CategoryModel;

public class CategoriesViewModel extends ViewModel {

    private MutableLiveData<List<CategoryModel>> mutableCategory = new MutableLiveData<>();

    private MutableLiveData<Boolean> mutableError = new MutableLiveData<>(false);

    /*Getters*
    *
    * */
    public MutableLiveData<List<CategoryModel>> getMutableCategory() {
        return mutableCategory;
    }

    public MutableLiveData<Boolean> getMutableError() {
        return mutableError;
    }
    /*End Getters*
    *
    * */
    public void requestCategoriesFromModel(){
        List<CategoryModel> categoriesModel = new ArrayList<>();
        categoriesModel.add(new CategoryModel("Pecho"));
        categoriesModel.add(new CategoryModel("Espalda"));
        categoriesModel.add(new CategoryModel("Piernas"));
        categoriesModel.add(new CategoryModel("Gluteos"));

        mutableCategory.setValue(categoriesModel);
    }


}
