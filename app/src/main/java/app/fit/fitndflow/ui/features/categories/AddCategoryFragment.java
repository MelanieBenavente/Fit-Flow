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

import com.fit.fitndflow.R;
import com.fit.fitndflow.databinding.AddFragmentCategoryBinding;

import java.util.ArrayList;

import app.fit.fitndflow.domain.model.CategoryModel;
import app.fit.fitndflow.domain.model.ExerciseModel;
import app.fit.fitndflow.ui.features.common.AccessibilityInterface;
import app.fit.fitndflow.ui.features.common.AccessibilityUtils;
import app.fit.fitndflow.ui.features.common.CommonFragment;
import app.fit.fitndflow.ui.features.common.component.CategoryEditableListener;
import app.fit.fitndflow.ui.features.common.component.EditableBtnAddExercise;
import app.fit.fitndflow.ui.features.common.component.EditableBtnDeleteExercise;

public class AddCategoryFragment extends CommonFragment implements CategoryEditableListener, DialogCallbackDelete, AccessibilityInterface {

    public static final String KEY_CATEGORY = "actualCategory";
    private AddFragmentCategoryBinding binding;
    private CategoriesAndExercisesViewModel categoriesAndExercisesViewModel;
    private CategoryModel categoryModel;

    private EditableBtnAddExercise lastEditable;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = AddFragmentCategoryBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        super.onCreateView(inflater, container, savedInstanceState);
        setViewModelObservers();
        setClickListeners();
        initAccessibility();
        binding.newCategoryTxt.addTextChangedListener(AccessibilityUtils.createTextWatcher(this));
        Bundle bundle = getArguments();
        if (bundle != null) {
            categoryModel = (CategoryModel) bundle.getSerializable(KEY_CATEGORY);
            printCategoryDetail(categoryModel);
        } else {
            printEmptyExerciseList();
        }
        return view;
    }

    public static AddCategoryFragment newInstance(CategoryModel category){
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

    private void printCategoryDetail(CategoryModel categoryRecived) {
        binding.editTxtContainer.removeAllViews();
        binding.newCategoryTxt.setText(categoryRecived.getName());
        for (int position = 0; position < categoryRecived.getExerciseList().size(); position++) {
            ExerciseModel exercise = categoryRecived.getExerciseList().get(position);
            binding.editTxtContainer.addView(new EditableBtnDeleteExercise(requireContext(), exercise, this, position));
        }

        lastEditable = new EditableBtnAddExercise(requireContext(), this);
        binding.editTxtContainer.addView(lastEditable);
    }

    private void printEmptyExerciseList() {
        lastEditable = new EditableBtnAddExercise(requireContext(), this);
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
            categoryModel = new CategoryModel(null, categoryName, null);
        } else {
            String categoryName = binding.newCategoryTxt.getText().toString();
            categoryModel.setName(categoryName);
        }
        if (categoryModel.getExerciseList() == null) {
            categoryModel.setExerciseList(new ArrayList<>());
            ExerciseModel exercise = new ExerciseModel(null, lastEditable.getEditText().getText().toString());
            categoryModel.getExerciseList().add(exercise);
            Log.i("Add", "added exercise");
        }
    }

    @Override
    protected Class getViewModelClass() {
        return CategoriesAndExercisesViewModel.class;
    }

    @Override
    public void onClickAdd(ExerciseModel exercise) {
        if (categoryModel == null) {
            String categoryName = binding.newCategoryTxt.getText().toString();
            categoryModel = new CategoryModel(null, categoryName, null);
        }
        if (categoryModel.getExerciseList() == null) {
            categoryModel.setExerciseList(new ArrayList<>());
        }
        categoryModel.getExerciseList().add(exercise);
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
        categoryModel.getExerciseList().remove(position);
        printCategoryDetail(categoryModel);
    }

    @Override
    public void initAccessibility() {
        String categoryName = getContext().getString(R.string.name_category);
        binding.newCategoryTxt.setAccessibilityDelegate(AccessibilityUtils.createAccesibilityDelegate(categoryName + binding.newCategoryTxt.getText().toString()));
    }
}
