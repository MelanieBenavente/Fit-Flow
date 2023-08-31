package app.fit.fitndflow.ui.features.categories;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.fit.fitndflow.databinding.AddFragmentCategoryBinding;

import app.fit.fitndflow.domain.model.CategoryModel;
import app.fit.fitndflow.ui.features.common.CommonFragment;
import app.fit.fitndflow.ui.features.excercises.ExcercisesAdapter;

public class AddCategoryFragment extends CommonFragment {

    public static final String KEY_CATEGORY = "actualCategory";

    private AddFragmentCategoryBinding binding;
    private CategoriesAndExcercisesViewModel categoriesAndExcercisesViewModel;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = AddFragmentCategoryBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        super.onCreateView(inflater, container, savedInstanceState);
        setViewModelObservers();
        setClickListeners();
        Bundle bundle = getArguments();
        if(bundle != null){
            CategoryModel categoryModel= (CategoryModel) bundle.getSerializable(KEY_CATEGORY);
            printCategoryDetail(categoryModel);
        }
        return view;
    }

    private void setViewModelObservers() {
        categoriesAndExcercisesViewModel = ViewModelProviders.of(getActivity()).get(CategoriesAndExcercisesViewModel.class);
    }

    private void printCategoryDetail(CategoryModel categoryRecived){
        binding.newCategoryTxt.setText(categoryRecived.getName());
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
