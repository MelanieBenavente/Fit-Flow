package app.fit.fitndflow.ui.features.categories;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

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
        category1.setExerciseList(exerciseList);
        categories.add(category1);

        ItemModel category2 = new ItemModel("Espalda");
        List<ItemModel> exercise2 = new ArrayList<>();
        exercise2.add(new ItemModel("darme un masaje"));
        category2.setExerciseList(exercise2);
        categories.add(category2);


        ItemModel category3 = new ItemModel("Cervicales");
        categories.add(category3);


        mutableCategory.setValue(categories);
    }


}
