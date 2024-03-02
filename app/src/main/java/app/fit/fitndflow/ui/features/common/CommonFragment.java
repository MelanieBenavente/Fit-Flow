package app.fit.fitndflow.ui.features.common;

import android.os.Bundle;

import androidx.annotation.CallSuper;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public abstract class CommonFragment extends Fragment {
    public void addFragment(Fragment fragment) {
        ((CommonActivity) getActivity()).addFragment(fragment);
    }

    public void replaceFragment(Fragment fragment) {
        ((CommonActivity) getActivity()).replaceFragment(fragment);
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
    public void showSlideSaved(){
        CommonActivity commonActivity = (CommonActivity)getActivity();
        if (commonActivity != null) {
            commonActivity.showSavedSlideContainer();
        }
    }

    public void showSlideError(){
        CommonActivity commonActivity = (CommonActivity)getActivity();
        if (commonActivity != null) {
            commonActivity.showErrorSlideContainer();
        }
    }

    public void popBackStack(){
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.popBackStack(fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount()-2).getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

   }
