package app.fit.fitndflow.ui.features.common.fragment;

import android.os.Bundle;

import androidx.annotation.CallSuper;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import app.fit.fitndflow.ui.features.common.activity.CommonActivity;

public abstract class CommonFragment<V extends ViewModel> extends Fragment {
    protected V viewModel;
    protected abstract Class<V> getViewModelClass();

    public void nextFragment(Fragment fragment) {
        ((CommonActivity) getActivity()).nextFragment(fragment);
    }

    @Override
    @CallSuper
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(getViewModelClass());
    }

}
