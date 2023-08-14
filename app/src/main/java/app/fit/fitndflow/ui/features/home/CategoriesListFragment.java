package app.fit.fitndflow.ui.features.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fit.fitndflow.databinding.FragmentCategoriesListBinding;

import app.fit.fitndflow.data.common.SharedPrefs;
import app.fit.fitndflow.ui.features.common.CommonFragment;

public class CategoriesListFragment extends CommonFragment {
    private FragmentCategoriesListBinding binding;

    @Override
    protected Class getViewModelClass() {
        return HomeViewModel.class;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCategoriesListBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
