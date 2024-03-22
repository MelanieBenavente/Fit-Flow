package app.fit.fitndflow.ui.features.categories;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import app.fit.fitndflow.data.repository.FitnFlowRepositoryImpl;
import app.fit.fitndflow.domain.common.arq.FitRxObserver;
import app.fit.fitndflow.domain.model.CategoryModel;
import app.fit.fitndflow.domain.model.ExerciseModel;
import app.fit.fitndflow.domain.repository.FitnFlowRepository;
import app.fit.fitndflow.domain.usecase.AddExerciseUseCase;
import app.fit.fitndflow.domain.usecase.DeleteCategoryUseCase;
import app.fit.fitndflow.domain.usecase.DeleteExerciseUseCase;
import app.fit.fitndflow.domain.usecase.ModifyCategoryUseCase;
import app.fit.fitndflow.domain.usecase.ModifyExerciseUseCase;

public class CategoriesAndExercisesViewModelJava extends ViewModel {
    private FitnFlowRepository fitnFlowRepository = FitnFlowRepositoryImpl.getInstance();
    private MutableLiveData<CategoryModel> actualCategory = new MutableLiveData<>();
    private MutableLiveData<List<CategoryModel>> mutableCategoryList = new MutableLiveData<>();
    private MutableLiveData<Boolean> mutableSlideError = new MutableLiveData<>(false);

    private MutableLiveData<Boolean> mutableFullScreenError = new MutableLiveData<>(false);

    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);

    private MutableLiveData<Boolean> isSaveSuccess = new MutableLiveData<>(false);

    private MutableLiveData<Boolean> isDeleteSuccess = new MutableLiveData<>(false);

    private String lastName;

    /*Getters*
     *
     * */


    public MutableLiveData<Boolean> getIsDeleteSuccess() {return isDeleteSuccess;}

    public MutableLiveData<CategoryModel> getActualCategory() {
        return actualCategory;
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public MutableLiveData<Boolean> getIsSaveSuccess() {return isSaveSuccess; }

    public MutableLiveData<List<CategoryModel>> getMutableCategoryList() {
        return mutableCategoryList;
    }
    public MutableLiveData<Boolean> getMutableSlideError() {
        return mutableSlideError;
    }

    public MutableLiveData<Boolean> getMutableFullScreenError() {
        return mutableFullScreenError;
    }


    public String getLastName() {
        return lastName;
    }

    /*End Getters*
     *
     * */

    public void modifyExercise(Context context, int exerciseId, String language, String exerciseName){
        new ModifyExerciseUseCase(context, exerciseId, language, exerciseName, actualCategory.getValue().getId(), fitnFlowRepository).execute(new FitRxObserver<List<ExerciseModel>>() {
            @Override
            protected void onStart() {
                super.onStart();
                isLoading.setValue(true);
                mutableSlideError.setValue(false);
                isSaveSuccess.setValue(false);
            }
            @Override
            public void onSuccess(List<ExerciseModel> exerciseModelList) {
                CategoryModel category = actualCategory.getValue();
                category.setExerciseList(exerciseModelList);
                actualCategory.setValue(category);
                isLoading.setValue(false);
                isSaveSuccess.setValue(true);
                mutableSlideError.setValue(false);
            }

            @Override
            public void onError(Throwable e) {
                mutableSlideError.setValue(true);
                isLoading.setValue(false);
                isSaveSuccess.setValue(false);
            }
        });
    }


    public void addNewExercise(Context context, String language, String nameExercise){
        new AddExerciseUseCase(context, language, nameExercise, actualCategory.getValue().getId(), fitnFlowRepository).execute(new FitRxObserver<List<ExerciseModel>>() {
            @Override
            protected void onStart() {
                super.onStart();
                isLoading.setValue(true);
                isSaveSuccess.setValue(false);
                mutableSlideError.setValue(false);
            }
            @Override
            public void onSuccess(List<ExerciseModel> exerciseModels) {
                CategoryModel category = actualCategory.getValue();
                category.setExerciseList(exerciseModels);
                actualCategory.setValue(category);
                isLoading.setValue(false);
                isSaveSuccess.setValue(true);
                mutableSlideError.setValue(false);
                lastName = null;
            }

            @Override
            public void onError(Throwable e) {
                mutableSlideError.setValue(true);
                isLoading.setValue(false);
                isSaveSuccess.setValue(false);
                lastName = nameExercise;
            }
        });
    }

    public void deleteCategory(int id, Context context){
        new DeleteCategoryUseCase(id, context, fitnFlowRepository).execute(new FitRxObserver<List<CategoryModel>>() {

            @Override
            protected void onStart(){
                super.onStart();
                isLoading.setValue(true);
                mutableSlideError.setValue(false);
            }

            @Override
            public void onSuccess(List<CategoryModel> categoryModelList) {
                isLoading.setValue(false);
                isDeleteSuccess.setValue(true);
                mutableCategoryList.setValue(categoryModelList);
                mutableSlideError.setValue(false);
            }

            @Override
            public void onError(Throwable e) {
                mutableSlideError.setValue(true);
                isLoading.setValue(false);
            }
        });
    }
    public void deleteExercise(int exerciseId, Context context){
        new DeleteExerciseUseCase(exerciseId, context, fitnFlowRepository).execute(new FitRxObserver<List<ExerciseModel>>() {
            @Override
            protected void onStart(){
                super.onStart();
                isLoading.setValue(true);
                mutableSlideError.setValue(false);
            }
            @Override
            public void onSuccess(List<ExerciseModel> exerciseModels) {
                CategoryModel category = actualCategory.getValue();
                category.setExerciseList(exerciseModels);
                actualCategory.setValue(category);
                isLoading.setValue(false);
                isDeleteSuccess.setValue(true);
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
