package app.fit.fitndflow.ui.features.categories;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import app.fit.fitndflow.domain.common.arq.FitObserver;
import app.fit.fitndflow.domain.model.CategoryModel;
import app.fit.fitndflow.domain.usecase.GetCategoriesUseCase;

public class CategoriesViewModel extends ViewModel {

    private MutableLiveData<List<CategoryModel>> mutableCategory = new MutableLiveData<>();

    private MutableLiveData<Boolean> mutableError = new MutableLiveData<>(false);

    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);

    /*Getters*
     *
     * */

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public MutableLiveData<List<CategoryModel>> getMutableCategory() {
        return mutableCategory;
    }

    public MutableLiveData<Boolean> getMutableError() {
        return mutableError;
    }

    /*End Getters*
     *
     * */
    public void requestCategoriesFromModel(Context context) {

        new GetCategoriesUseCase(context).execute(new FitObserver<List<CategoryModel>>() {
            @Override
            protected void onStart() {
                super.onStart();
                isLoading.setValue(true);
                mutableCategory.setValue(new ArrayList<>());
            }

            @Override
            public void onSuccess(List<CategoryModel> categoryModels) {
                mutableCategory.setValue(categoryModels);
                mutableError.setValue(false);
                isLoading.setValue(false);
            }

            @Override
            public void onError(Throwable e) {
                mutableError.setValue(true);
                isLoading.setValue(false);
            }
        });
    }
}
