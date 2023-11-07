package app.fit.fitndflow.ui.features.home;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import app.fit.fitndflow.data.repository.FitnFlowRepositoryImpl;
import app.fit.fitndflow.domain.common.arq.FitObserver;
import app.fit.fitndflow.domain.model.CategoryModel;
import app.fit.fitndflow.domain.model.UserModel;
import app.fit.fitndflow.domain.repository.FitnFlowRepository;
import app.fit.fitndflow.domain.usecase.GetTrainingUseCase;
import app.fit.fitndflow.domain.usecase.RegisterUserUseCase;

public class HomeViewModel extends ViewModel {

    private FitnFlowRepository fitnFlowRepository = new FitnFlowRepositoryImpl();
    private MutableLiveData<Boolean> mutableError = new MutableLiveData<>(false);
    private MutableLiveData<Date> actualDate = new MutableLiveData<>(new Date());

    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private MutableLiveData<List<CategoryModel>> mutableCategory = new MutableLiveData<>();

    /*
    getters
     */
    public MutableLiveData<Date> getActualDate() {
        return actualDate;
    }

    public MutableLiveData<Boolean> getMutableError() {
        return mutableError;
    }
    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }
    public MutableLiveData<List<CategoryModel>> getMutableCategory() {
        return mutableCategory;
    }

    //end getters

    public void dayBefore(){
        Date date = actualDate.getValue();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, -1);

        // Obtener la nueva fecha
        date = calendar.getTime();
        actualDate.setValue(date);
    }
    public void dayAfter(){
        Date date = actualDate.getValue();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, +1);

        date = calendar.getTime();
        actualDate.setValue(date);
    }

    public void requestRegisterEmptyUser(Context context) {
        UserModel emptyUserModel = new UserModel(null, null, null, null);
        new RegisterUserUseCase(emptyUserModel, context, fitnFlowRepository).execute(new FitObserver<UserModel>() {

            protected void onStart() {
                super.onStart();
                isLoading.setValue(true);
            }
            @Override
            public void onSuccess(UserModel apiRegisterResponse) {
                isLoading.setValue(false);
                mutableError.setValue(false);

            }

            @Override
            public void onError(Throwable e) {
                isLoading.setValue(false);
                mutableError.setValue(true);
            }
        });
    }

    public void requestTrainingFromModel(Context context){
        GetTrainingUseCase getTrainingUseCase = new GetTrainingUseCase(context, fitnFlowRepository);

        getTrainingUseCase.execute(new FitObserver<List<CategoryModel>>() {
            @Override
            public void onSuccess(List<CategoryModel> categoryModelList) {
                mutableCategory.setValue(categoryModelList);
                isLoading.setValue(false);
                mutableError.setValue(false);
            }

            @Override
            public void onError(Throwable e) {
                isLoading.setValue(false);
                mutableError.setValue(true);
            }
        });
    }

}
