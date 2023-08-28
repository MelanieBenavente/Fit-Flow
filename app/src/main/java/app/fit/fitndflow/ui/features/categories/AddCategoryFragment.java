package app.fit.fitndflow.ui.features.categories;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fit.fitndflow.databinding.AddFragmentCategoryBinding;

import app.fit.fitndflow.ui.features.common.CommonFragment;

public class AddCategoryFragment extends CommonFragment {

    private AddFragmentCategoryBinding binding;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = AddFragmentCategoryBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        super.onCreateView(inflater, container, savedInstanceState);
        setClickListeners();
        return view;
    }

    private void setClickListeners() {
        binding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo call to viewmodel function savecategory!!!!!!!!!!!
            }
        });
    }

    @Override
    protected Class getViewModelClass() {
        return CategoriesAndExcercisesViewModel.class;
    }
}
