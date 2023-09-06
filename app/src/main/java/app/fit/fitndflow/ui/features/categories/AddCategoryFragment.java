package app.fit.fitndflow.ui.features.categories;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.fit.fitndflow.databinding.AddFragmentCategoryBinding;

import java.util.ArrayList;

import app.fit.fitndflow.domain.model.CategoryModel;
import app.fit.fitndflow.domain.model.ExcerciseModel;
import app.fit.fitndflow.ui.features.common.CommonFragment;
import app.fit.fitndflow.ui.features.common.component.CategoryEditableListener;
import app.fit.fitndflow.ui.features.common.component.EditableAddBtn;
import app.fit.fitndflow.ui.features.common.component.EditableDeleteBtn;

public class AddCategoryFragment extends CommonFragment implements CategoryEditableListener {

    public static final String KEY_CATEGORY = "actualCategory";
    private AddFragmentCategoryBinding binding;
    private CategoriesAndExcercisesViewModel categoriesAndExcercisesViewModel;
    private CategoryModel categoryModel;

    private  EditableAddBtn lastEditable;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = AddFragmentCategoryBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        super.onCreateView(inflater, container, savedInstanceState);
        setViewModelObservers();
        setClickListeners();
        Bundle bundle = getArguments();
        if(bundle != null){
            categoryModel= (CategoryModel) bundle.getSerializable(KEY_CATEGORY);
            printCategoryDetail(categoryModel);
        } else {
            printEmptyExcerciseList();
        }
        return view;
    }

    private void setViewModelObservers() {
        categoriesAndExcercisesViewModel = ViewModelProviders.of(getActivity()).get(CategoriesAndExcercisesViewModel.class);

        final Observer<Boolean> observerLoading = new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                if (isLoading) {
                    binding.loading.setVisibility(View.VISIBLE);
                } else {
                    binding.loading.setVisibility(View.GONE);
                }
            }
        };
        categoriesAndExcercisesViewModel.getIsLoading().observe(getActivity(), observerLoading);

        final Observer<Boolean> observerIsSuccess = new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isSuccess) {
                if(isSuccess && isVisible()){
                    requireActivity().onBackPressed();
                }
            }
        };
        categoriesAndExcercisesViewModel.getIsSuccess().observe(getActivity(), observerIsSuccess);
    }

    private void printCategoryDetail(CategoryModel categoryRecived){
        binding.editTxtContainer.removeAllViews();
        binding.newCategoryTxt.setText(categoryRecived.getName());
        for(int position = 0; position < categoryRecived.getExcerciseList().size(); position++){
            ExcerciseModel excercise = categoryRecived.getExcerciseList().get(position);
            binding.editTxtContainer.addView(new EditableDeleteBtn(requireContext(), excercise, this, position));
        }

        lastEditable = new EditableAddBtn(requireContext(), this);
        binding.editTxtContainer.addView(lastEditable);
    }

    private void printEmptyExcerciseList(){
        lastEditable = new EditableAddBtn(requireContext(), this);
        binding.editTxtContainer.addView(lastEditable);
    }

    private void setClickListeners() {
        binding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateScreenData();
                categoriesAndExcercisesViewModel.saveCategory(requireContext(), categoryModel);
            }
        });
    }

    private void updateScreenData(){
        if(categoryModel == null){
            String categoryName = binding.newCategoryTxt.getText().toString();
            categoryModel = new CategoryModel(categoryName);
        } else {
            String categoryName = binding.newCategoryTxt.getText().toString();
            categoryModel.setName(categoryName);
        }
        if (!lastEditable.getEditText().getText().toString().equals("")){
            if(categoryModel.getExcerciseList() == null){
                categoryModel.setExcerciseList(new ArrayList<>());
            }
            ExcerciseModel excercise = new ExcerciseModel(lastEditable.getEditText().getText().toString());
            categoryModel.getExcerciseList().add(excercise);
        }
    }

    @Override
    protected Class getViewModelClass() {
        return CategoriesAndExcercisesViewModel.class;
    }

    @Override
    public void onClickAdd(ExcerciseModel excercise) {
        if(categoryModel == null){
            String categoryName = binding.newCategoryTxt.getText().toString();
            categoryModel = new CategoryModel(categoryName);
        }
        if(categoryModel.getExcerciseList() == null){
            categoryModel.setExcerciseList(new ArrayList<>());
        }
        categoryModel.getExcerciseList().add(excercise);
        printCategoryDetail(categoryModel);
    }

    @Override
    public void onClickDelete(int position) {
        categoryModel.getExcerciseList().remove(position);
        printCategoryDetail(categoryModel);
    }
}
