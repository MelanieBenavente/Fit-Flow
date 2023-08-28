package app.fit.fitndflow.ui.features.excercises;

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

import java.util.List;

import app.fit.fitndflow.domain.model.ExcerciseModel;
import app.fit.fitndflow.ui.features.categories.CategoriesAndExcercisesViewModel;
import app.fit.fitndflow.ui.features.common.CommonFragment;

public class ExcerciseListFragment extends CommonFragment {

    private FragmentExcercisesListBinding binding;

    private CategoriesAndExcercisesViewModel categoriesAndExcercisesViewModel;

    @Override
    protected Class getViewModelClass() {return CategoriesAndExcercisesViewModel.class;}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentExcercisesListBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        super.onCreateView(inflater, container, savedInstanceState);
        setViewModelObservers();
        return view;
    }

    private void setViewModelObservers() {
        categoriesAndExcercisesViewModel = ViewModelProviders.of(getActivity()).get(CategoriesAndExcercisesViewModel.class);

        //observing RazaList
        final Observer<List<ExcerciseModel>> observer = new Observer<List<ExcerciseModel>>() {
            @Override
            public void onChanged(List<ExcerciseModel> excercises) {
                printExcercises(excercises);
            }
        };
        //Observamos al listado del ViewModel y ejecutamos las acciones indicadas antes en el observer
        categoriesAndExcercisesViewModel.getMutableExcercise().observe(getActivity(), observer);
    }

    private void printExcercises(List<ExcerciseModel> listRecived){
        ExcercisesAdapter excercisesAdapter = new ExcercisesAdapter(listRecived);
        binding.recyclerCategories.setHasFixedSize(true);
        binding.recyclerCategories.setLayoutManager(new LinearLayoutManager(this.getContext()));
        binding.recyclerCategories.setAdapter(excercisesAdapter);
        excercisesAdapter.notifyDataSetChanged();
    }
    private void printError(){

        Toast.makeText(this.getContext(), "ERROR", Toast.LENGTH_SHORT).show();
    }
}
