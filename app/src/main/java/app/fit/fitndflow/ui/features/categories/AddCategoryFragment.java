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

import app.fit.fitndflow.domain.model.CategoryModel;
import app.fit.fitndflow.domain.model.ExcerciseModel;
import app.fit.fitndflow.ui.features.common.CommonActivity;
import app.fit.fitndflow.ui.features.common.CommonFragment;
import app.fit.fitndflow.ui.features.common.component.CategoryEditableListener;
import app.fit.fitndflow.ui.features.common.component.EditableAddBtn;
import app.fit.fitndflow.ui.features.common.component.EditableDeleteBtn;

public class AddCategoryFragment extends CommonFragment implements CategoryEditableListener, DialogCallbackDelete {

    public static final String KEY_CATEGORY = "actualCategory";
    private AddFragmentCategoryBinding binding;
    private CategoriesAndExcercisesViewModel categoriesAndExcercisesViewModel;
    private CategoryModel categoryModel;

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
            categoryModel = (CategoryModel) bundle.getSerializable(KEY_CATEGORY);
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
        categoriesAndExcercisesViewModel.getIsLoading().observe(getActivity(), observerLoading);

        final Observer<Boolean> observerIsSuccess = new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isSuccess) {
                if (isSuccess && isVisible()) {
                    requireActivity().onBackPressed();
                }
            }
        };
        categoriesAndExcercisesViewModel.getIsSuccess().observe(getActivity(), observerIsSuccess);

        final Observer<Boolean> observerError = new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isError) {
                if(isError){
                    showSlideError();
                }
            }
        };
        categoriesAndExcercisesViewModel.getMutableSlideError().observe(getActivity(), observerError);
    }

    private void printCategoryDetail(CategoryModel categoryRecived) {
        binding.editTxtContainer.removeAllViews();
        binding.newCategoryTxt.setText(categoryRecived.getName());
        for (int position = 0; position < categoryRecived.getExcerciseList().size(); position++) {
            ExcerciseModel excercise = categoryRecived.getExcerciseList().get(position);
            binding.editTxtContainer.addView(new EditableDeleteBtn(requireContext(), excercise, this, position));
        }

        lastEditable = new EditableAddBtn(requireContext(), this);
        binding.editTxtContainer.addView(lastEditable);
    }

    private void printEmptyExcerciseList() {
        lastEditable = new EditableAddBtn(requireContext(), this);
        binding.editTxtContainer.addView(lastEditable);
    }

    private void setClickListeners() {
        binding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateScreenData();
                if (!categoriesAndExcercisesViewModel.getIsLoading().getValue() && !categoryModel.getName().equals("")) {
                    categoriesAndExcercisesViewModel.saveCategory(requireContext(), categoryModel);
                }
            }
        });

        binding.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!categoriesAndExcercisesViewModel.getIsLoading().getValue()) {
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
            categoryModel = new CategoryModel(categoryName);
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
        return CategoriesAndExcercisesViewModel.class;
    }

    @Override
    public void onClickAdd(ExcerciseModel excercise) {
        if (categoryModel == null) {
            String categoryName = binding.newCategoryTxt.getText().toString();
            categoryModel = new CategoryModel(categoryName);
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
        categoriesAndExcercisesViewModel.deleteCategory(categoryModel, requireContext());
    }

    @Override
    public void onClickAcceptedExercise(int position) {
        categoryModel.getExcerciseList().remove(position);
        printCategoryDetail(categoryModel);
    }
}
