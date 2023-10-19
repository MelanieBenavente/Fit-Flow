package app.fit.fitndflow.ui.features.categories;

import static app.fit.fitndflow.ui.features.categories.ConfirmationDialogFragment.DELETE_CATEGORY;
import static app.fit.fitndflow.ui.features.categories.ConfirmationDialogFragment.DELETE_EXERCISE;

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

import app.fit.fitndflow.domain.model.CategoryModelKT;
import app.fit.fitndflow.domain.model.ExcerciseModel;
import app.fit.fitndflow.ui.features.common.CommonFragment;
import app.fit.fitndflow.ui.features.common.component.CategoryEditableListener;
import app.fit.fitndflow.ui.features.common.component.EditableAddBtn;
import app.fit.fitndflow.ui.features.common.component.EditableDeleteBtn;

public class AddCategoryFragment extends CommonFragment implements CategoryEditableListener, DialogCallbackDelete {

    public static final String KEY_CATEGORY = "actualCategory";
    private AddFragmentCategoryBinding binding;
    private CategoriesAndExercisesViewModel categoriesAndExercisesViewModel;
    private CategoryModelKT categoryModel;

    private EditableAddBtn lastEditable;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = AddFragmentCategoryBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        super.onCreateView(inflater, container, savedInstanceState);
        setViewModelObservers();
        setClickListeners();
        Bundle bundle = getArguments();
        if (bundle != null) {
            categoryModel = (CategoryModelKT) bundle.getSerializable(KEY_CATEGORY);
            printCategoryDetail(categoryModel);
        } else {
            printEmptyExerciseList();
        }
        return view;
    }

    public static AddCategoryFragment newInstance(CategoryModelKT category){
       AddCategoryFragment addCategoryFragment = new AddCategoryFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_CATEGORY, category);
        addCategoryFragment.setArguments(bundle);
       return addCategoryFragment;
    }

    private void setViewModelObservers() {
        categoriesAndExercisesViewModel = ViewModelProviders.of(getActivity()).get(CategoriesAndExercisesViewModel.class);

        final Observer<Boolean> observerLoading = new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                try {
                    if (isLoading) {
                        showLoading();

                    } else {
                        hideLoading();
                    }
                } catch (Exception exception) {
                    Log.e("Error", "show loading");
                }

            }
        };
        categoriesAndExercisesViewModel.getIsLoading().observe(getActivity(), observerLoading);

        final Observer<Boolean> observerIsSaveSuccess = new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isSaveSuccess) {
                if (isSaveSuccess && isVisible()) {
                    requireActivity().onBackPressed();
                }
            }
        };
        categoriesAndExercisesViewModel.getIsSaveSuccess().observe(getActivity(), observerIsSaveSuccess);

        final Observer<Boolean> observerIsDeleteSuccess = new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isDeleteSuccess) {
                if(isDeleteSuccess && isVisible()){
                        popBackStack();
                }
            }
        };
        categoriesAndExercisesViewModel.getIsDeleteSuccess().observe(getActivity(), observerIsDeleteSuccess);

        final Observer<Boolean> observerError = new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isError) {
                if(isError){
                    showSlideError();
                }
            }
        };
        categoriesAndExercisesViewModel.getMutableSlideError().observe(getActivity(), observerError);
    }

    private void printCategoryDetail(CategoryModelKT categoryRecived) {
        binding.editTxtContainer.removeAllViews();
        binding.newCategoryTxt.setText(categoryRecived.getName());
        for (int position = 0; position < categoryRecived.getExcerciseList().size(); position++) {
            ExcerciseModel excercise = categoryRecived.getExcerciseList().get(position);
            binding.editTxtContainer.addView(new EditableDeleteBtn(requireContext(), excercise, this, position));
        }

        lastEditable = new EditableAddBtn(requireContext(), this);
        binding.editTxtContainer.addView(lastEditable);
    }

    private void printEmptyExerciseList() {
        lastEditable = new EditableAddBtn(requireContext(), this);
        binding.editTxtContainer.addView(lastEditable);
    }

    private void setClickListeners() {
        binding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateScreenData();
                if (!categoriesAndExercisesViewModel.getIsLoading().getValue() && !categoryModel.getName().equals("")) {
                    categoriesAndExercisesViewModel.saveCategory(requireContext(), categoryModel);
                }
            }
        });

        binding.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!categoriesAndExercisesViewModel.getIsLoading().getValue()) {
                    if (categoryModel == null || categoryModel.getId() == 0) {
                        requireActivity().onBackPressed();
                    } else {
                        ConfirmationDialogFragment.newInstance(AddCategoryFragment.this, DELETE_CATEGORY, -1).show(getChildFragmentManager(), "ConfirmationDialog");
                    }
                }
            }
        });
    }

    private void updateScreenData() {
        if (categoryModel == null) {
            String categoryName = binding.newCategoryTxt.getText().toString();
            categoryModel = new CategoryModelKT(null, categoryName, null);
        } else {
            String categoryName = binding.newCategoryTxt.getText().toString();
            categoryModel.setName(categoryName);
        }
        if (categoryModel.getExcerciseList() == null) {
            categoryModel.setExcerciseList(new ArrayList<>());
            ExcerciseModel excercise = new ExcerciseModel(lastEditable.getEditText().getText().toString());
            categoryModel.getExcerciseList().add(excercise);
            Log.i("Add", "added exercise");
        }
    }

    @Override
    protected Class getViewModelClass() {
        return CategoriesAndExercisesViewModel.class;
    }

    @Override
    public void onClickAdd(ExcerciseModel excercise) {
        if (categoryModel == null) {
            String categoryName = binding.newCategoryTxt.getText().toString();
            categoryModel = new CategoryModelKT(null, categoryName, null);
        }
        if (categoryModel.getExcerciseList() == null) {
            categoryModel.setExcerciseList(new ArrayList<>());
        }
        categoryModel.getExcerciseList().add(excercise);
        printCategoryDetail(categoryModel);
    }

    @Override
    public void onClickDelete(int position) {
        ConfirmationDialogFragment.newInstance(AddCategoryFragment.this, DELETE_EXERCISE, position).show(getChildFragmentManager(), "ConfirmationDialog");
    }

    @Override
    public void onClickAcceptDeleteCategory() {
        categoriesAndExercisesViewModel.deleteCategory(categoryModel, requireContext());
    }

    @Override
    public void onClickAcceptedExercise(int position) {
        categoryModel.getExcerciseList().remove(position);
        printCategoryDetail(categoryModel);
    }
}
