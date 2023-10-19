package app.fit.fitndflow.ui.features.excercises;


import static app.fit.fitndflow.ui.features.categories.AddCategoryFragment.KEY_CATEGORY;

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

import com.fit.fitndflow.databinding.FragmentExcercisesListBinding;

import app.fit.fitndflow.domain.model.CategoryModelKT;
import app.fit.fitndflow.ui.features.categories.AddCategoryFragment;
import app.fit.fitndflow.ui.features.categories.CategoriesAndExercisesViewModel;
import app.fit.fitndflow.ui.features.common.CommonFragment;

public class ExcerciseListFragment extends CommonFragment {

    private FragmentExcercisesListBinding binding;

    private CategoriesAndExercisesViewModel categoriesAndExercisesViewModel;

    @Override
    protected Class getViewModelClass() {
        return CategoriesAndExercisesViewModel.class;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentExcercisesListBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        super.onCreateView(inflater, container, savedInstanceState);
        setViewModelObservers();
        setOnClickListeners();
        return view;
    }

    private void setViewModelObservers() {
        categoriesAndExercisesViewModel = ViewModelProviders.of(getActivity()).get(CategoriesAndExercisesViewModel.class);

        //observing RazaList
        final Observer<CategoryModelKT> observer = new Observer<CategoryModelKT>() {
            @Override
            public void onChanged(CategoryModelKT category) {
                printCategoryDetail(category);
            }
        };
        //Observamos al listado del ViewModel y ejecutamos las acciones indicadas antes en el observer
        categoriesAndExercisesViewModel.getActualCategory().observe(getActivity(), observer);
    }

    private void printCategoryDetail(CategoryModelKT categoryRecived) {
        ExcercisesAdapter excercisesAdapter = new ExcercisesAdapter(categoryRecived.getExcerciseList());
        binding.recyclerCategories.setHasFixedSize(true);
        binding.recyclerCategories.setLayoutManager(new LinearLayoutManager(this.getContext()));
        binding.recyclerCategories.setAdapter(excercisesAdapter);
        excercisesAdapter.notifyDataSetChanged();

        binding.categoryTitle.setText(categoryRecived.getName());
    }

    private void printError() {

        Toast.makeText(this.getContext(), "ERROR", Toast.LENGTH_SHORT).show();
    }

    private void setOnClickListeners() {
        binding.pencilFloatingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddCategoryFragment addCategoryFragment = new AddCategoryFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable(KEY_CATEGORY, categoriesAndExercisesViewModel.getActualCategory().getValue());
                addCategoryFragment.setArguments(bundle);
                nextFragment(addCategoryFragment);
            }
        });
    }
}
