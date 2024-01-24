package app.fit.fitndflow.ui.features.exercises;


import static app.fit.fitndflow.ui.features.categories.CreationOrModifyInputDialog.TYPE_EXERCISE;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.fit.fitndflow.R;
import com.fit.fitndflow.databinding.FragmentExercisesListBinding;

import java.util.ArrayList;
import java.util.List;

import app.fit.fitndflow.domain.model.CategoryModel;
import app.fit.fitndflow.domain.model.ExerciseModel;
import app.fit.fitndflow.ui.features.categories.CategoriesAndExercisesViewModel;
import app.fit.fitndflow.ui.features.categories.ConfirmationDialogFragment;
import app.fit.fitndflow.ui.features.categories.CreationOrModifyInputDialog;
import app.fit.fitndflow.ui.features.categories.DialogCallbackDelete;
import app.fit.fitndflow.ui.features.common.CommonFragment;
import app.fit.fitndflow.ui.features.training.AddSerieTrainingFragment;
import app.fit.fitndflow.ui.features.training.SerieAdapterCallback;

public class ExerciseListFragment extends CommonFragment implements SerieAdapterCallback, DialogCallbackDelete {

    private FragmentExercisesListBinding binding;

    private CategoriesAndExercisesViewModel categoriesAndExercisesViewModel;

    private ExercisesAdapter exercisesAdapter;
    private boolean isEditMode;

    @Override
    protected Class getViewModelClass() {
        return CategoriesAndExercisesViewModel.class;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentExercisesListBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        super.onCreateView(inflater, container, savedInstanceState);
        categoriesAndExercisesViewModel = ViewModelProviders.of(getActivity()).get(CategoriesAndExercisesViewModel.class);
        printCategoryDetailAndinstantiateAdaper(categoriesAndExercisesViewModel.getActualCategory().getValue());
        setViewModelObservers();
        setOnClickListeners();
        addTextWatcher();
        return view;
    }

    private void setViewModelObservers() {
        categoriesAndExercisesViewModel.getMutableSlideError().setValue(false);
        categoriesAndExercisesViewModel.getIsLoading().setValue(false);

        final Observer<CategoryModel> observer = new Observer<CategoryModel>() {
            @Override
            public void onChanged(CategoryModel category) {
                //setear listado al adapter
                exercisesAdapter.setExerciseModelList(category.getExerciseList());
            }
        };
        categoriesAndExercisesViewModel.getActualCategory().observe(getActivity(), observer);

        final Observer<Boolean> observerError = new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isError) {
                if (isError) {
                    showSlideError();
                    categoriesAndExercisesViewModel.getMutableSlideError().setValue(false);
                }
            }
        };
        categoriesAndExercisesViewModel.getMutableSlideError().observe(getActivity(), observerError);
        final Observer<Boolean> errorObserver = new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isError) {
                if (isError) {
                    printError();
                    categoriesAndExercisesViewModel.getMutableFullScreenError().setValue(false);
                }
            }
        };
        categoriesAndExercisesViewModel.getMutableFullScreenError().observe(getActivity(), errorObserver);
        final Observer<Boolean> observerIsSaveSuccess = new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isSaveSuccess) {
                if (isSaveSuccess) {
                    showSlideSaved();
                    categoriesAndExercisesViewModel.getIsSaveSuccess().setValue(false);
                }
            }
        };
        categoriesAndExercisesViewModel.getIsSaveSuccess().observe(getActivity(), observerIsSaveSuccess);
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
    }

    private void printCategoryDetailAndinstantiateAdaper(CategoryModel categoryRecived) {
        exercisesAdapter = new ExercisesAdapter(categoryRecived.getExerciseList(), this);
        binding.recyclerCategories.setHasFixedSize(true);
        binding.recyclerCategories.setLayoutManager(new LinearLayoutManager(this.getContext()));
        binding.recyclerCategories.setAdapter(exercisesAdapter);

        binding.categoryTitle.setText(categoryRecived.getName());
    }

    private void addTextWatcher() {
        binding.txtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                List<ExerciseModel> filteredList = new ArrayList<>();
                CategoryModel category = categoriesAndExercisesViewModel.getActualCategory().getValue();
                List<ExerciseModel> exerciseList = category.getExerciseList();
                for (int j = 0; j < exerciseList.size(); j++) {
                    if (exerciseList.get(j).getName().toUpperCase().contains(charSequence.toString().toUpperCase())) {
                        filteredList.add(exerciseList.get(j));
                    }
                }
                exercisesAdapter.setExerciseModelList(filteredList);
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    private void setOnClickListeners() {
        binding.floatingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isEditMode = !isEditMode;
                if (isEditMode) {
                    binding.floatingBtn.setImageResource(R.drawable.svg_check);
                } else {
                    binding.floatingBtn.setImageResource(R.drawable.svg_pencil);
                }
                exercisesAdapter.setEditMode(isEditMode);
            }
        });
    }

    @Override
    public void showSeries(ExerciseModel exercise) {
        if (exercise.getId() != 0 && exercise.getName() != null) {
            addFragment(AddSerieTrainingFragment.newInstance(exercise));
        } else {
            showBlockError();
        }
    }
    private void printError() {
        try {
            showBlockError();
        } catch (Exception exception) {
            Log.e("Error", "Error to print errorContainer");
        }
    }
    @Override
    public void showCreationDialog() {
        CreationOrModifyInputDialog.newInstance(TYPE_EXERCISE).show(getChildFragmentManager(), "creationDialog");
    }

    @Override
    public void showDeleteDialog(int id) {
        ConfirmationDialogFragment.newInstance(ExerciseListFragment.this, ConfirmationDialogFragment.DELETE_EXERCISE, id).show(getChildFragmentManager(), "ConfirmationDialog");
    }

    @Override
    public void showModifyDialog(int exerciseId, String exerciseName) {
        CreationOrModifyInputDialog.newInstance(TYPE_EXERCISE, exerciseId, exerciseName).show(getChildFragmentManager(), "creationDialog");
    }

    @Override
    public void onClickAcceptDelete(int exerciseId) {
        categoriesAndExercisesViewModel.deleteExercise(exerciseId,requireContext());
    }
}
