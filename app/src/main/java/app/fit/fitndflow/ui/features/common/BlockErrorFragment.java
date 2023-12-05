package app.fit.fitndflow.ui.features.common;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fit.fitndflow.databinding.BlockErrorFragmentBinding;

import app.fit.fitndflow.ui.features.categories.CategoriesAndExercisesViewModel;
import app.fit.fitndflow.ui.features.home.HomeViewModel;

public class BlockErrorFragment extends CommonFragment {

    private BlockErrorFragmentBinding binding;
    private View.OnClickListener retyro;


    @Override
    protected Class getViewModelClass() {
        return null;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = BlockErrorFragmentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        super.onCreateView(inflater, container, savedInstanceState);
        binding.backErrorScreenBtn.setOnClickListener(retyro);

        return view;
    }
}
