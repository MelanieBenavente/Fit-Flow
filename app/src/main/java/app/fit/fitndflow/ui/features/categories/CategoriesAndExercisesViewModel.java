package app.fit.fitndflow.ui.features.categories;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import app.fit.fitndflow.data.repository.FitnFlowRepositoryImpl;
import app.fit.fitndflow.domain.common.arq.FitObserver;
import app.fit.fitndflow.domain.model.CategoryModel;
import app.fit.fitndflow.domain.repository.FitnFlowRepository;
import app.fit.fitndflow.domain.usecase.AddCategoryUseCase;
import app.fit.fitndflow.domain.usecase.DeleteCategoryUseCase;
import app.fit.fitndflow.domain.usecase.GetCategoriesUseCase;

public class CategoriesAndExercisesViewModel extends ViewModel {
    private FitnFlowRepository fitnFlowRepository = new FitnFlowRepositoryImpl();
    private MutableLiveData<CategoryModel> actualCategory = new MutableLiveData<>();
    private MutableLiveData<List<CategoryModel>> mutableCategory = new MutableLiveData<>();

    private MutableLiveData<Boolean> mutableSlideError = new MutableLiveData<>(false);

    private MutableLiveData<Boolean> mutableFullScreenError = new MutableLiveData<>(false);

    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);

    private MutableLiveData<Boolean> isSuccess = new MutableLiveData<>(false);

    /*Getters*
     *
     * */

    public MutableLiveData<CategoryModel> getActualCategory() {
        return actualCategory;
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public MutableLiveData<Boolean> getIsSuccess() {return isSuccess; }

    public MutableLiveData<List<CategoryModel>> getMutableCategory() {
        return mutableCategory;
    }

    public MutableLiveData<Boolean> getMutableSlideError() {
        return mutableSlideError;
    }

    public MutableLiveData<Boolean> getMutableFullScreenError() {
        return mutableFullScreenError;
    }

    /*End Getters*
     *
     * */
    public void requestCategoriesFromModel(Context context) {
            GetCategoriesUseCase getCategoriesUseCase = new GetCategoriesUseCase(context, fitnFlowRepository);
        getCategoriesUseCase.execute(new FitObserver<List<CategoryModel>>() {
            @Override
            protected void onStart() {
                super.onStart();
                mutableFullScreenError.setValue(false);
                isLoading.setValue(true);
                mutableCategory.setValue(new ArrayList<>());
            }

            @Override
            public void onSuccess(List<CategoryModel> categoryModels) {
                mutableCategory.setValue(categoryModels);
                mutableFullScreenError.setValue(false);
                isLoading.setValue(false);
            }

            @Override
            public void onError(Throwable e) {
                mutableFullScreenError.setValue(true);
                isLoading.setValue(false);
            }
        });
    }

    public void saveCategory(Context context, CategoryModel categoryModel){
        new AddCategoryUseCase(context, categoryModel, fitnFlowRepository). execute(new FitObserver<Boolean>() {

            @Override
            protected void onStart() {
                super.onStart();
                isLoading.setValue(true);
                mutableSlideError.setValue(false);
            }

            @Override
            public void onSuccess(Boolean aBoolean) {
                isLoading.setValue(false);
                isSuccess.setValue(true);
                mutableSlideError.setValue(false);

            }

            @Override
            public void onError(Throwable e) {
                mutableSlideError.setValue(true);
                isLoading.setValue(false);
            }
        });
    }

    public void deleteCategory(CategoryModel categoryModel, Context context){
        new DeleteCategoryUseCase(categoryModel, context, fitnFlowRepository).execute(new FitObserver<Boolean>() {

            @Override
            protected void onStart(){
                super.onStart();
                isLoading.setValue(true);
                mutableSlideError.setValue(false);
            }
            @Override
            public void onSuccess(Boolean aBoolean) {
                isLoading.setValue(false);
                isSuccess.setValue(true);
                mutableSlideError.setValue(false);
            }

            @Override
            public void onError(Throwable e) {
                mutableSlideError.setValue(true);
                isLoading.setValue(false);

            }
        });
    }
}
