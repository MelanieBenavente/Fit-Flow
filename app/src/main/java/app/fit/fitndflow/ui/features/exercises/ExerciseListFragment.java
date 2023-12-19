package app.fit.fitndflow.ui.features.exercises;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
import app.fit.fitndflow.ui.features.categories.AddCategoryFragment;
import app.fit.fitndflow.ui.features.categories.CategoriesAndExercisesViewModel;
import app.fit.fitndflow.ui.features.common.CommonFragment;
import app.fit.fitndflow.ui.features.training.AddSerieTrainingFragment;
import app.fit.fitndflow.ui.features.training.SerieAdapterCallback;

public class ExerciseListFragment extends CommonFragment implements SerieAdapterCallback {

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
        setViewModelObservers();
        addTextWatcher();
        setOnClickListeners();
        return view;
    }

    private void setViewModelObservers() {
        categoriesAndExercisesViewModel = ViewModelProviders.of(getActivity()).get(CategoriesAndExercisesViewModel.class);

        final Observer<CategoryModel> observer = new Observer<CategoryModel>() {
            @Override
            public void onChanged(CategoryModel category) {
                printCategoryDetail(category);
            }
        };
        //Observamos al listado del ViewModel y ejecutamos las acciones indicadas antes en el observer
        categoriesAndExercisesViewModel.getActualCategory().observe(getActivity(), observer);
    }

    private void printCategoryDetail(CategoryModel categoryRecived) {
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
                if(isEditMode){
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
}
