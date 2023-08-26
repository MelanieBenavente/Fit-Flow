package app.fit.fitndflow.ui.features.categories;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import app.fit.fitndflow.domain.common.arq.FitObserver;
import app.fit.fitndflow.domain.model.ItemModel;
import app.fit.fitndflow.domain.usecase.GetCategoriesUseCase;

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
    public void requestCategoriesFromModel(Context context) {
        new GetCategoriesUseCase(context).execute(new FitObserver<List<ItemModel>>() {
            @Override
            public void onSuccess(List<ItemModel> categoryModels) {
                mutableCategory.setValue(categoryModels);
                mutableError.setValue(false);
            }

            @Override
            public void onError(Throwable e) {
                mutableError.setValue(true);
            }
        });
    }
}
