package app.fit.fitndflow.ui.features.categories;

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
import com.fit.fitndflow.databinding.FragmentCategoriesListBinding;

import java.util.ArrayList;
import java.util.List;

import app.fit.fitndflow.data.dto.categories.CategoryDto;
import app.fit.fitndflow.domain.model.CategoryModel;
import app.fit.fitndflow.domain.model.ExerciseModel;
import app.fit.fitndflow.ui.features.common.AccessibilityInterface;
import app.fit.fitndflow.ui.features.common.AccessibilityUtils;
import app.fit.fitndflow.ui.features.common.CommonFragment;
import app.fit.fitndflow.ui.features.exercises.ExerciseListFragment;
import app.fit.fitndflow.ui.features.exercises.ExercisesAdapter;
import app.fit.fitndflow.ui.features.training.AddSerieTrainingFragment;
import app.fit.fitndflow.ui.features.training.SerieAdapterCallback;

public class CategoriesListFragment extends CommonFragment implements CategoryAdapterCallback, AccessibilityInterface, SerieAdapterCallback {

    private List<CategoryModel> categoryList = new ArrayList<>();
    private FragmentCategoriesListBinding binding;
    private CategoriesAndExercisesViewModel categoriesAndExercisesViewModel;
    private CategoriesAdapter categoriesAdapter;

    private ExercisesAdapter exercisesAdapter;

    @Override
    protected Class getViewModelClass() {
        return CategoriesAndExercisesViewModel.class;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCategoriesListBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        super.onCreateView(inflater, container, savedInstanceState);
        instantiateCategoriesAdapter();
        setViewModelObservers();
        setOnClickListeners();
        initAccessibility();
        binding.txtSearch.addTextChangedListener(AccessibilityUtils.createTextWatcher(this));
        categoriesAndExercisesViewModel.requestCategoriesFromModel(requireContext());
        addTextWatcher();
        return view;
    }

    private void setViewModelObservers() {
        categoriesAndExercisesViewModel = ViewModelProviders.of(getActivity()).get(CategoriesAndExercisesViewModel.class);
        categoriesAndExercisesViewModel.getMutableSlideError().setValue(false);
        categoriesAndExercisesViewModel.getIsLoading().setValue(false);

        //observing RazaList
        final Observer<List<CategoryModel>> observer = new Observer<List<CategoryModel>>() {
            @Override
            public void onChanged(List<CategoryModel> categories) {
                printCategories(categories);
            }
        };
        //Observamos al listado del ViewModel y ejecutamos las acciones indicadas antes en el observer
        categoriesAndExercisesViewModel.getMutableCategory().observe(getActivity(), observer);

        //observing error
        final Observer<Boolean> errorObserver = new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isError) {
                if (isError) {
                    printError();
                }
            }
        };
        categoriesAndExercisesViewModel.getMutableFullScreenError().observe(getActivity(), errorObserver);

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

    private void printCategories(List<CategoryModel> listRecived) {
        categoryList = listRecived;
        categoriesAdapter.setCategoryList(categoryList);
    }

    private void addTextWatcher() {
        binding.txtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().equals("")) {
                    instantiateCategoriesAdapter();
                    categoriesAdapter.setCategoryList(categoryList);
                } else {
                    List<ExerciseModel> filteredList = new ArrayList<>();
                    for (int j = 0; j < categoryList.size(); j++) {
                        CategoryModel category = categoryList.get(j);
                        List<ExerciseModel> exerciseList = category.getExerciseList();
                        for (int k = 0; k < exerciseList.size(); k++) {
                            ExerciseModel exercise = exerciseList.get(k);
                            if (exercise.getName().toUpperCase().contains(charSequence.toString().toUpperCase())) {
                                filteredList.add(exercise);
                            }
                        }
                    }
                    instantiateExercisesAdapter(filteredList);
                    exercisesAdapter.setExerciseModelList(filteredList);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void printError() {
        try {
            showBlockError();
        } catch (Exception exception) {
            Log.e("Error", "Error to print errorContainer");
        }
    }

    private void instantiateCategoriesAdapter() {
        categoriesAdapter = new CategoriesAdapter(this);
        binding.recyclerCategories.setHasFixedSize(true);
        binding.recyclerCategories.setLayoutManager(new LinearLayoutManager(this.getContext()));
        binding.recyclerCategories.setAdapter(categoriesAdapter);
    }

    private void instantiateExercisesAdapter(List<ExerciseModel> filteredList) {
        exercisesAdapter = new ExercisesAdapter(filteredList, this);
        binding.recyclerCategories.setHasFixedSize(true);
        binding.recyclerCategories.setLayoutManager(new LinearLayoutManager(this.getContext()));
        binding.recyclerCategories.setAdapter(exercisesAdapter);
    }

    private void setOnClickListeners() {
        binding.floatingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFragment(new AddCategoryFragment());
            }
        });
    }

    @Override
    public void showExercises(CategoryModel category) {
        categoriesAndExercisesViewModel.getActualCategory().setValue(category);
        if (category.getExerciseList() != null && !category.getExerciseList().isEmpty()) {
            addFragment(new ExerciseListFragment());
        } else {
            AddCategoryFragment addCategoryFragment = AddCategoryFragment.newInstance(category);
            addFragment(addCategoryFragment);
        }

    }

    @Override
    public void initAccessibility() {
        String searchExercise = getContext().getString(R.string.search_exercise);
        binding.txtSearch.setAccessibilityDelegate(AccessibilityUtils.createAccesibilityDelegate(searchExercise + binding.txtSearch.getText().toString()));
    }

    @Override
    public void showSeries(ExerciseModel exercise) {
        if (exercise.getId() != 0 && exercise.getName() != null) {
            addFragment(AddSerieTrainingFragment.newInstance(exercise));
        } else {
            showBlockError();
        }
    }
}
