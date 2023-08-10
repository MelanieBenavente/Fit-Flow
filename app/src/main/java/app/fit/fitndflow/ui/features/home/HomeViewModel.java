package app.fit.fitndflow.ui.features.home;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import app.fit.fitndflow.domain.common.arq.FitObserver;
import app.fit.fitndflow.domain.model.UserModel;
import app.fit.fitndflow.domain.usecase.RegisterUserUseCase;

public class HomeViewModel extends ViewModel {
    private MutableLiveData<UserModel> mutableUserModel = new MutableLiveData<>();
    private MutableLiveData<Boolean> mutableError = new MutableLiveData<>(false);

    /*
    getters
     */
    public MutableLiveData<UserModel> getMutableUserModel() {
        return mutableUserModel;
    }

    public MutableLiveData<Boolean> getMutableError() {
        return mutableError;
    }
    //end getters

    public void requestRegisterEmptyUser(Context context) {
        UserModel emptyUserModel = new UserModel();
        new RegisterUserUseCase(emptyUserModel, context).execute(new FitObserver<UserModel>() {
            @Override
            public void onSuccess(UserModel apiRegisterResponse) {
                mutableUserModel.setValue(apiRegisterResponse);
                mutableError.setValue(false);
            }

            @Override
            public void onError(Throwable e) {
                mutableError.setValue(true);
            }
        });
    }

}
