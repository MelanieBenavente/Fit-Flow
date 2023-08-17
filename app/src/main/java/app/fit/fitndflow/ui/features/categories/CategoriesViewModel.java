package app.fit.fitndflow.ui.features.categories;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import app.fit.fitndflow.domain.model.ItemModel;
import app.fit.fitndflow.domain.model.ItemModel;

public class CategoriesViewModel extends ViewModel {

    private MutableLiveData<List<ItemModel>> mutableCategory = new MutableLiveData<>();

    private MutableLiveData<Boolean> mutableError = new MutableLiveData<>(false);

    /*Getters*
    *
    * */
    public MutableLiveData<List<ItemModel>> getMutableCategory() {
        return mutableCategory;
    }

    public MutableLiveData<Boolean> getMutableError() {
        return mutableError;
    }
    /*End Getters*
    *
    * */
    public void requestCategoriesFromModel(){
        List<ItemModel> categories = new ArrayList<>();
        ItemModel category1 = new ItemModel("Pecho");
        List<ItemModel> exerciseList = new ArrayList<>();
        exerciseList.add(new ItemModel("rascarme el toto"));
        category1.setItemModelList(exerciseList);
        categories.add(category1);

        List<ItemModel> categories2 = new ArrayList<>();
        ItemModel category2 = new ItemModel("Espalda");
        List<ItemModel> exercise2 = new ArrayList<>();
        exercise2.add(new ItemModel("darme un masaje"));
        category2.setItemModelList(exercise2);
        categories2.add(category2);

        List<ItemModel> categories3 = new ArrayList<>();
        ItemModel category3 = new ItemModel("Cervicales");
        List<ItemModel> exercise3 = new ArrayList<>();
        exercise3.add(new ItemModel(null));
        category3.setItemModelList(exercise3);
        categories3.add(category3);


        mutableCategory.setValue(categories);
    }


}
