package app.fit.fitndflow.ui.features.categories;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.fit.fitndflow.databinding.AddFragmentCategoryBinding;

import java.util.ArrayList;
import java.util.List;

import app.fit.fitndflow.domain.model.CategoryModel;
import app.fit.fitndflow.domain.model.ExcerciseModel;
import app.fit.fitndflow.ui.features.common.CommonActivity;
import app.fit.fitndflow.ui.features.common.CommonFragment;
import app.fit.fitndflow.ui.features.common.component.CategoryEditableListener;
import app.fit.fitndflow.ui.features.common.component.EditableAddBtn;
import app.fit.fitndflow.ui.features.common.component.EditableComponent;
import app.fit.fitndflow.ui.features.common.component.EditableDeleteBtn;

public class AddCategoryFragment extends CommonFragment implements CategoryEditableListener {

    public static final String KEY_CATEGORY = "actualCategory";
    private AddFragmentCategoryBinding binding;
    private CategoriesAndExcercisesViewModel categoriesAndExcercisesViewModel;
    private CategoryModel categoryModel;

    private List<EditableComponent> editableComponentList = new ArrayList<>();

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
                try{
                    if (isLoading) {
                        ((CommonActivity) requireActivity()).showLoading();

                    } else {
                        ((CommonActivity) requireActivity()).hideLoading();
                    }
                }catch(Exception exception){
                    Log.e("Error","show loading");
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
            EditableDeleteBtn editableDeleteBtn = new EditableDeleteBtn(requireContext(), excercise, this, position);
            binding.editTxtContainer.addView(editableDeleteBtn);
            editableComponentList.add(editableDeleteBtn);
        }
        EditableAddBtn editableAddBtn = new EditableAddBtn(requireContext(), this, categoryRecived.getExcerciseList().size());
        editableComponentList.add(editableAddBtn);
        binding.editTxtContainer.addView(editableAddBtn);
    }

    private void printEmptyExcerciseList(){
        EditableAddBtn editableAddBtn = new EditableAddBtn(requireContext(), this, 0);
        binding.editTxtContainer.addView(editableAddBtn);
        editableComponentList.add(editableAddBtn);
    }

    private void setClickListeners() {
        binding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateScreenData();
                categoriesAndExcercisesViewModel.saveCategory(requireContext(), categoryModel);
            }
        });

        binding.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(categoryModel == null || categoryModel.getId() == 0){
                    requireActivity().onBackPressed();
                } else {
                    categoriesAndExcercisesViewModel.deleteCategory(categoryModel, requireContext());
                }
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
        if (!editableComponentList.get(editableComponentList.size()-1).getEditText().getText().toString().equals("")){
            if(categoryModel.getExcerciseList() == null){
                categoryModel.setExcerciseList(new ArrayList<>());
            }
            if(categoryModel.getExcerciseList().size() == editableComponentList.get(editableComponentList.size()-1).getPosition()){
                ExcerciseModel excercise = new ExcerciseModel(editableComponentList.get(editableComponentList.size()-1).getEditText().getText().toString());
                categoryModel.getExcerciseList().add(excercise);
            } else {
                categoryModel.getExcerciseList().set(editableComponentList.get(editableComponentList.size()-1).getPosition(), new ExcerciseModel(editableComponentList.get(editableComponentList.size()-1).getEditText().getText().toString()));
            }

            Log.i("Add", "added exercise");
        }
        for(int i = 0; i < editableComponentList.size(); i++){

            int position = editableComponentList.get(i).getPosition();
            String name = editableComponentList.get(i).getEditText().getText().toString();
            if (position >= categoryModel.getExcerciseList().size()){
                categoryModel.getExcerciseList().add(new ExcerciseModel(name));
            } else {
                categoryModel.getExcerciseList().set(position, new ExcerciseModel(name));

            }
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
