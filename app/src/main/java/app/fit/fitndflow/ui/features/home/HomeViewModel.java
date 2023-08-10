package app.fit.fitndflow.ui.features.home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import app.fit.fitndflow.domain.common.model.ErrorModel;
import app.fit.fitndflow.domain.common.model.None;
import app.fit.fitndflow.domain.model.UserModel;
import app.fit.fitndflow.domain.usecase.RegisterUserUseCase;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class HomeViewModel extends ViewModel {
    private MutableLiveData<UserModel> mutableUserModel = new MutableLiveData<>();

    private MutableLiveData<ErrorModel> mutableError = new MutableLiveData<>(null);

    /*
    getters
     */
    public MutableLiveData<UserModel> getMutableUserModel() {
        return mutableUserModel;
    }

    public MutableLiveData<ErrorModel> getMutableError() {
        return mutableError;
    }
    //end getters

    public void requestRegisterEmptyUser() {
        UserModel emptyUserModel = new UserModel();
        Observable<UserModel> userObservable = new RegisterUserUseCase().executeUseCase(emptyUserModel);
        userObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onRazaListReceived, this::onRazaListError);
    }

    private void onRazaListReceived(UserModel user) {
        mutableUserModel.setValue(user);
        mutableError.setValue(null);
    }

    private void onRazaListError(Throwable throwable) {
        mutableError.setValue(null);
    }


}
