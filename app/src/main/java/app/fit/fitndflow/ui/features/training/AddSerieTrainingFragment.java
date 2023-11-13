package app.fit.fitndflow.ui.features.training;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fit.fitndflow.databinding.AddSerieTrainingFragmentBinding;

import java.util.ArrayList;
import java.util.List;

import app.fit.fitndflow.domain.model.ExerciseModel;
import app.fit.fitndflow.domain.model.SerieModel;
import app.fit.fitndflow.ui.features.categories.CategoriesAndExercisesViewModel;
import app.fit.fitndflow.ui.features.common.CommonFragment;

public class AddSerieTrainingFragment extends CommonFragment implements SerieEditableListener {

    public static final String KEY_EXCERCISE = "actualExercise";
    private AddSerieTrainingFragmentBinding binding;

    private CategoriesAndExercisesViewModel categoriesAndExercisesViewModel;

    private ExerciseModel exercise;
    private List<SerieModel> serieModelList = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = AddSerieTrainingFragmentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        super.onCreateView(inflater, container, savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle != null){
            exercise = (ExerciseModel) bundle.getSerializable(KEY_EXCERCISE);
        }
        binding.exerciseNameTitle.setText(exercise.getName());
        if (serieModelList.isEmpty()){
            printEmptySerieDetail();
        } else {
            printSerieDetail();
        }

        return view;
    }

    @Override
    protected Class getViewModelClass() {
        return CategoriesAndExercisesViewModel.class;
    }

    public static AddSerieTrainingFragment newInstance(ExerciseModel exercise){
        AddSerieTrainingFragment addSerieTrainingFragment = new AddSerieTrainingFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_EXCERCISE, exercise);
        addSerieTrainingFragment.setArguments(bundle);
        return addSerieTrainingFragment;
    }

    private void printEmptySerieDetail(){
        binding.addSerieLayout.removeAllViews();
        binding.addSerieLayout.addView(new EditableBtnAddSerie(getContext(), this));
    }

    private void printSerieDetail(){
        binding.addSerieLayout.removeAllViews();
        for(int i = 0; i < serieModelList.size(); i++){
            SerieModel serie = serieModelList.get(i);
            binding.addSerieLayout.addView(new EditableBtnDeleteSerie(getContext(), this, serie, i ));
        }
        binding.addSerieLayout.addView(new EditableBtnAddSerie(getContext(), this));
    }

    @Override
    public void onClickAdd(SerieModel serie) {
        serieModelList.add(serie);
        printSerieDetail();
    }

    @Override
    public void onClickDelete(int position) {
        serieModelList.remove(position);
        printSerieDetail();
    }
}
