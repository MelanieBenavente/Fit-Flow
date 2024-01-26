package app.fit.fitndflow.ui.features.training;

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

import com.fit.fitndflow.R;
import com.fit.fitndflow.databinding.AddSerieTrainingFragmentBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import app.fit.fitndflow.domain.model.ExerciseModel;
import app.fit.fitndflow.domain.model.SerieModel;
import app.fit.fitndflow.ui.features.categories.ConfirmationDialogFragment;
import app.fit.fitndflow.ui.features.categories.DialogCallbackDelete;
import app.fit.fitndflow.ui.features.common.CommonFragment;
import app.fit.fitndflow.ui.features.home.HomeViewModel;

public class AddSerieTrainingFragment extends CommonFragment implements TrainingCallback, DialogCallbackDelete {

    public static final double INPUT_KG = 2.5;
    public static final int INPUT_REPS = 1;
    public static final int INPUT_ZERO = 0;
    public static final String KEY_EXCERCISE = "actualExercise";
    private AddSerieTrainingFragmentBinding binding;

    private HomeViewModel homeViewModel;
    private ExerciseModel exercise;
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
        homeViewModel = ViewModelProviders.of(getActivity()).get(HomeViewModel.class);
        instantiateSeriesAdapter();
        setViewModelObservers();
        initListeners();
        return view;
    }

    @Override
    protected Class getViewModelClass() {
        return HomeViewModel.class;
    }

    public static AddSerieTrainingFragment newInstance(ExerciseModel exercise){
        AddSerieTrainingFragment addSerieTrainingFragment = new AddSerieTrainingFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_EXCERCISE, exercise);
        addSerieTrainingFragment.setArguments(bundle);
        return addSerieTrainingFragment;
    }
    private void setViewModelObservers(){
        final Observer<HashMap<Integer, List<SerieModel>>> observer = new Observer<HashMap<Integer, List<SerieModel>>>() {
            @Override
            public void onChanged(HashMap<Integer, List<SerieModel>> actualHashMap) {
                seriesAdapter.setSerieModelList(actualHashMap.get(exercise.getId()));
            }
        };
        homeViewModel.getSerieMutableList().observe(getActivity(), observer);
    }

    private void initListeners(){
        binding.saveAndUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(serieModel != null){
                   //todo llamar al server para editar serie
                } else {
                    int reps = Integer.parseInt(binding.etCounterReps.getText().toString());
                    double kg = Double.parseDouble(binding.etCounterKg.getText().toString());
                    homeViewModel.addNewSerie(requireContext(), reps, kg, exercise.getId());
                }
            }
        });

        binding.deleteAndCleanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(serieModel != null){
                    showDeleteDialog(serieModel.getId());
                } else {
                    binding.etCounterReps.setText("");
                    binding.etCounterKg.setText("");
                }
            }
        });

        binding.btnIncrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.etCounterKg.getText().toString().equals("")) {
                    binding.etCounterKg.setText(Double.toString(INPUT_KG));
                } else {
                    double currentValue = Double.parseDouble(binding.etCounterKg.getText().toString());
                    double newValue = currentValue + INPUT_KG;
                    binding.etCounterKg.setText(Double.toString(newValue));
                }
            }
        });
        binding.btnDecrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.etCounterKg.getText().toString().equals("")) {
                    binding.etCounterKg.setText(Double.toString(INPUT_ZERO));
                } else {
                    double currentValue = Double.parseDouble(binding.etCounterKg.getText().toString());
                    double newValue = currentValue - INPUT_KG;
                    if(newValue > INPUT_ZERO) {
                        binding.etCounterKg.setText(Double.toString(newValue));
                    } else{
                        binding.etCounterKg.setText(Double.toString(INPUT_ZERO));
                    }
                }
            }
        });

        binding.btnIncrement2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.etCounterReps.getText().toString().equals("")) {
                    binding.etCounterReps.setText(Integer.toString(INPUT_REPS));
                } else {
                    int currentValue = Integer.valueOf(binding.etCounterReps.getText().toString());
                    int newValue = currentValue + INPUT_REPS;
                    binding.etCounterReps.setText(Integer.toString(newValue));
                }
            }
        });

        binding.btnDecrement2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.etCounterReps.getText().toString().equals("")) {
                    binding.etCounterReps.setText(Integer.toString(INPUT_ZERO));
                } else {
                    int currentValue = Integer.valueOf(binding.etCounterReps.getText().toString());
                    int newValue = currentValue - INPUT_REPS;
                    if(newValue > INPUT_ZERO) {
                        binding.etCounterReps.setText(Integer.toString(newValue));
                    } else {
                        binding.etCounterReps.setText(Integer.toString(INPUT_ZERO));
                    }
                }
            }
        });
    }

    private void instantiateSeriesAdapter(){
        seriesAdapter = new SeriesAdapter(new ArrayList<>(), this);
        binding.addSerieLayout.setHasFixedSize(true);
        binding.addSerieLayout.setLayoutManager(new LinearLayoutManager(this.getContext()));
        binding.addSerieLayout.setAdapter(seriesAdapter);
    }

    @Override
    public void clickListenerInterfaceAdapter(SerieModel input) {
        serieModel = input;
        if(input != null){
            binding.etCounterReps.setText(Integer.toString(input.getReps()));
            binding.etCounterKg.setText(Double.toString(input.getKg()));
            binding.saveAndUpdateBtn.setText(R.string.update);
            binding.deleteAndCleanBtn.setText(R.string.delete_btn);
        } else {
            binding.etCounterReps.setText("");
            binding.etCounterKg.setText("");
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
