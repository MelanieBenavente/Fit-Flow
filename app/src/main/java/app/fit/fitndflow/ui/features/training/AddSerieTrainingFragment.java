package app.fit.fitndflow.ui.features.training;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.fit.fitndflow.databinding.AddSerieTrainingFragmentBinding;

import java.util.ArrayList;
import java.util.List;

import app.fit.fitndflow.domain.model.ExerciseModel;
import app.fit.fitndflow.domain.model.SerieModel;
import app.fit.fitndflow.ui.features.categories.CategoriesAndExercisesViewModel;
import app.fit.fitndflow.ui.features.common.AccessibilityUtils;
import app.fit.fitndflow.ui.features.common.CommonFragment;

public class AddSerieTrainingFragment extends CommonFragment implements TrainingCallback {

    public static final String KEY_EXCERCISE = "actualExercise";
    private AddSerieTrainingFragmentBinding binding;

    private CategoriesAndExercisesViewModel categoriesAndExercisesViewModel;

    private ExerciseModel exercise;
    private List<SerieModel> serieModelList = new ArrayList<>();
    private SeriesAdapter seriesAdapter;
    private boolean isEditMode;




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
            //printSerieDetail();
        }

        initListeners();
        instantiateSeriesAdapter();
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

    private void initListeners(){
        //todo onclickadd()
    }

    private void instantiateSeriesAdapter(){
        //todo delete
         serieModelList.add(new SerieModel(14, 12.0));
        serieModelList.add(new SerieModel(5, 10.0));
        serieModelList.add(new SerieModel(20, 8.0));
         //todo end todo


        seriesAdapter = new SeriesAdapter(serieModelList, this);
        binding.addSerieLayout.setHasFixedSize(true);
        binding.addSerieLayout.setLayoutManager(new LinearLayoutManager(this.getContext()));
        binding.addSerieLayout.setAdapter(seriesAdapter);
    }

    private void printEmptySerieDetail(){
        binding.addSerieLayout.removeAllViews();
        //binding.addSerieLayout.addView(new EditableBtnAddSerie(getContext(), this));
    }

    @Override
    public void clickListenerInterfaceAdapter(SerieModel input) {
        if(input != null){
            binding.tvCounterReps.setText(Integer.toString(input.getReps()));
            binding.tvCounter.setText(Double.toString(input.getKg()));
            binding.saveAndUpdateBtn.setText("Actualizar");
            binding.deleteAndCleanBtn.setText("Eliminar");
        } else {
            binding.tvCounterReps.setText("");
            binding.tvCounter.setText("");
            binding.saveAndUpdateBtn.setText("Añadir");
            binding.deleteAndCleanBtn.setText("Limpiar");
        }

    }
}
