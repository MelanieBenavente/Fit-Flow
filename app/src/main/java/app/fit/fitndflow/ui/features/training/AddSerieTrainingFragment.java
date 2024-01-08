package app.fit.fitndflow.ui.features.training;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.fit.fitndflow.R;
import com.fit.fitndflow.databinding.AddSerieTrainingFragmentBinding;

import java.util.ArrayList;
import java.util.List;

import app.fit.fitndflow.domain.model.ExerciseModel;
import app.fit.fitndflow.domain.model.SerieModel;
import app.fit.fitndflow.ui.features.categories.CategoriesAndExercisesViewModel;
import app.fit.fitndflow.ui.features.categories.ConfirmationDialogFragment;
import app.fit.fitndflow.ui.features.categories.DialogCallbackDelete;
import app.fit.fitndflow.ui.features.common.AccessibilityUtils;
import app.fit.fitndflow.ui.features.common.CommonFragment;

public class AddSerieTrainingFragment extends CommonFragment implements TrainingCallback, DialogCallbackDelete {

    public static final double INPUT_KG = 2.5;
    public static final int INPUT_REPS = 1;
    public static final int INPUT_ZERO = 0;
    public static final String KEY_EXCERCISE = "actualExercise";
    private AddSerieTrainingFragmentBinding binding;

    private CategoriesAndExercisesViewModel categoriesAndExercisesViewModel;

    private ExerciseModel exercise;
    private List<SerieModel> serieModelList = new ArrayList<>();
    private SeriesAdapter seriesAdapter;

    private SerieModel serieModel;




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
        binding.saveAndUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(serieModel != null){
                   //todo llamar al server para editar serie
                } else {
                    //todo llamar al server para añadir serie
                }
            }
        });

        binding.deleteAndCleanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(serieModel != null){
                    showDeleteDialog(serieModel.getId());
                } else {
                    binding.tvCounterReps.setText("");
                    binding.tvCounter.setText("");
                }
            }
        });

        binding.btnIncrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.tvCounter.getText().toString().equals("")) {
                    binding.tvCounter.setText(Double.toString(INPUT_KG));
                } else {
                    double currentValue = Double.parseDouble(binding.tvCounter.getText().toString());
                    double newValue = currentValue + INPUT_KG;
                    binding.tvCounter.setText(Double.toString(newValue));
                }
            }
        });
        binding.btnDecrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.tvCounter.getText().toString().equals("")) {
                    binding.tvCounter.setText(Double.toString(INPUT_ZERO));
                } else {
                    double currentValue = Double.parseDouble(binding.tvCounter.getText().toString());
                    double newValue = currentValue - INPUT_KG;
                    if(newValue > INPUT_ZERO) {
                        binding.tvCounter.setText(Double.toString(newValue));
                    } else{
                        binding.tvCounter.setText(Double.toString(INPUT_ZERO));
                    }
                }
            }
        });

        binding.btnIncrement2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.tvCounterReps.getText().toString().equals("")) {
                    binding.tvCounterReps.setText(Integer.toString(INPUT_REPS));
                } else {
                    int currentValue = Integer.valueOf(binding.tvCounterReps.getText().toString());
                    int newValue = currentValue + INPUT_REPS;
                    binding.tvCounterReps.setText(Integer.toString(newValue));
                }
            }
        });

        binding.btnDecrement2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.tvCounterReps.getText().toString().equals("")) {
                    binding.tvCounterReps.setText(Integer.toString(INPUT_ZERO));
                } else {
                    int currentValue = Integer.valueOf(binding.tvCounterReps.getText().toString());
                    int newValue = currentValue - INPUT_REPS;
                    if(newValue > INPUT_ZERO) {
                        binding.tvCounterReps.setText(Integer.toString(newValue));
                    } else {
                        binding.tvCounterReps.setText(Integer.toString(INPUT_ZERO));
                    }
                }
            }
        });
    }

    private void instantiateSeriesAdapter(){
        //todo delete
        serieModelList.add(new SerieModel(1,14, 12.0));
        serieModelList.add(new SerieModel(2, 5, 10.0));
        serieModelList.add(new SerieModel(3, 20, 8.0));
        serieModelList.add(new SerieModel(1,14, 12.0));
        serieModelList.add(new SerieModel(2, 5, 10.0));
        serieModelList.add(new SerieModel(3, 20, 8.0));
         //todo end todo


        seriesAdapter = new SeriesAdapter(serieModelList, this);
        binding.addSerieLayout.setHasFixedSize(true);
        binding.addSerieLayout.setLayoutManager(new LinearLayoutManager(this.getContext()));
        binding.addSerieLayout.setAdapter(seriesAdapter);
    }

    private void printEmptySerieDetail(){
        binding.addSerieLayout.removeAllViews();
    }

    @Override
    public void clickListenerInterfaceAdapter(SerieModel input) {
        serieModel = input;
        if(input != null){
            binding.tvCounterReps.setText(Integer.toString(input.getReps()));
            binding.tvCounter.setText(Double.toString(input.getKg()));
            binding.saveAndUpdateBtn.setText(R.string.update);
            binding.deleteAndCleanBtn.setText(R.string.delete_btn);
        } else {
            binding.tvCounterReps.setText("");
            binding.tvCounter.setText("");
            binding.saveAndUpdateBtn.setText(R.string.add);
            binding.deleteAndCleanBtn.setText(R.string.clean);
        }

    }
    private void showDeleteDialog(int id) {
        ConfirmationDialogFragment.newInstance(AddSerieTrainingFragment.this, ConfirmationDialogFragment.DELETE_SERIE, id).show(getChildFragmentManager(), "ConfirmationDialog");
    }

    @Override
    public void onClickAcceptDelete(int id) {
        Toast.makeText(requireContext(), "delete serie", Toast.LENGTH_SHORT).show();
        //todo borrar serie del listado de series
    }
}
