package app.fit.fitndflow.ui.features.exercises;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.fit.fitndflow.databinding.FragmentExercisesListBinding;

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
        ExercisesAdapter exercisesAdapter = new ExercisesAdapter(categoryRecived.getExerciseList(), this);
        binding.recyclerCategories.setHasFixedSize(true);
        binding.recyclerCategories.setLayoutManager(new LinearLayoutManager(this.getContext()));
        binding.recyclerCategories.setAdapter(exercisesAdapter);
        exercisesAdapter.notifyDataSetChanged();

        binding.categoryTitle.setText(categoryRecived.getName());
    }

    private void printError() {

        Toast.makeText(this.getContext(), "ERROR", Toast.LENGTH_SHORT).show();
    }

    private void setOnClickListeners() {
        binding.pencilFloatingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CategoryModel category = categoriesAndExercisesViewModel.getActualCategory().getValue();
                AddCategoryFragment addCategoryFragment = AddCategoryFragment.newInstance(category);
                addFragment(addCategoryFragment);
            }
        });
    }

    @Override
    public void showSeries(ExerciseModel exercise) {
        if(exercise.getId() != 0 && exercise.getName() != null){
            addFragment(AddSerieTrainingFragment.newInstance(exercise));
        } else {
            showBlockError();
        }
    }
}
