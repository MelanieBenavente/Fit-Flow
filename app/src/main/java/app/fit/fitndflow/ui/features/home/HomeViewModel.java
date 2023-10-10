package app.fit.fitndflow.ui.features.home;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Calendar;
import java.util.Date;

import app.fit.fitndflow.domain.common.arq.FitObserver;
import app.fit.fitndflow.domain.model.UserModel;
import app.fit.fitndflow.domain.usecase.RegisterUserUseCase;

public class HomeViewModel extends ViewModel {
    private MutableLiveData<Boolean> mutableError = new MutableLiveData<>(false);
    private MutableLiveData<Date> actualDate = new MutableLiveData<>(new Date());

    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);

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
        UserModel emptyUserModel = new UserModel();
        new RegisterUserUseCase(emptyUserModel, context).execute(new FitObserver<UserModel>() {

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

}
