package app.fit.fitndflow.ui.features.common;

import android.os.Bundle;

import androidx.annotation.CallSuper;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public abstract class CommonFragment<V extends ViewModel> extends Fragment {
    protected V viewModel;
    protected abstract Class<V> getViewModelClass();

    public void nextFragment(Fragment fragment) {
        ((CommonActivity) getActivity()).nextFragment(fragment);
    }

    public void showLoading(){
        CommonActivity commonActivity = (CommonActivity) getActivity();
        if (commonActivity != null){
            commonActivity.showLoadingFromActivity();
        }
    }

    public void hideLoading(){
        CommonActivity commonActivity = (CommonActivity) getActivity();
        if(commonActivity != null){
            commonActivity.hideLoadingFromActivity();
        }
    }

    public void showBlockError() {
        CommonActivity commonActivity = (CommonActivity) getActivity();
        if (commonActivity != null) {
            commonActivity.showBlockError();
        }
    }

    public void showSlideError(){
        CommonActivity commonActivity = (CommonActivity)getActivity();
        if (commonActivity != null) {
            commonActivity.showErrorSlideContainer();
        }
    }

    @Override
    @CallSuper
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getViewModelClass() != null) {
            viewModel = new ViewModelProvider(getActivity()).get(getViewModelClass());
        }
    }

}
