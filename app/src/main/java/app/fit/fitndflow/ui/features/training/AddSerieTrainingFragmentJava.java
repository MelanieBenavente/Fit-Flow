package app.fit.fitndflow.ui.features.training;

import android.os.Bundle;
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
import com.fit.fitndflow.databinding.AddSerieTrainingFragmentBinding;

import java.util.HashMap;
import java.util.List;

import app.fit.fitndflow.domain.model.ExerciseModel;
import app.fit.fitndflow.domain.model.SerieModel;
import app.fit.fitndflow.ui.features.categories.ConfirmationDialogFragment;
import app.fit.fitndflow.ui.features.categories.DialogCallbackDelete;
import app.fit.fitndflow.ui.features.common.CommonFragment;
import app.fit.fitndflow.ui.features.home.HomeViewModel;

public class AddSerieTrainingFragmentJava extends CommonFragment implements TrainingCallback, DialogCallbackDelete {

    public static final double INPUT_KG = 2.5;
    public static final int INPUT_REPS = 1;
    public static final int INPUT_ZERO = 0;
    public static final String KEY_EXCERCISE = "actualExercise";
    private AddSerieTrainingFragmentBinding binding;

    private HomeViewModel homeViewModel;
    private ExerciseModel exercise;
    private SeriesAdapter seriesAdapter;
    private SerieModel currentSelectedSerieModel;


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
        initListeners();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        homeViewModel = ViewModelProviders.of(getActivity()).get(HomeViewModel.class);
        homeViewModel.getSerieListOfExerciseAdded(exercise.getId());
        setViewModelObservers();
    }

    public static AddSerieTrainingFragmentJava newInstance(ExerciseModel exercise){
        AddSerieTrainingFragmentJava addSerieTrainingFragment = new AddSerieTrainingFragmentJava();
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_EXCERCISE, exercise);
        addSerieTrainingFragment.setArguments(bundle);
        return addSerieTrainingFragment;
    }
    private void setViewModelObservers(){
        final Observer<HashMap<Integer, List<SerieModel>>> observer = new Observer<HashMap<Integer, List<SerieModel>>>() {
            @Override
            public void onChanged(HashMap<Integer, List<SerieModel>> actualHashMap) {
                instantiateSeriesAdapter(actualHashMap.get(exercise.getId()));
                printEditMode(false);
            }
        };
        homeViewModel.getHashmapMutableSerieListByExerciseId().observe(getViewLifecycleOwner(), observer);

        final Observer<Boolean> observerIsSaveSuccess = new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isSaveSuccess) {
                if (isSaveSuccess) {
                    showSlideSaved();
                    homeViewModel.getIsSaveSuccess().setValue(false);
                }
            }
        };
        homeViewModel.getIsSaveSuccess().observe(getViewLifecycleOwner(), observerIsSaveSuccess);

        final Observer<Boolean> observerError = new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isError) {
                if(isError){
                    showSlideError();
                    homeViewModel.getMutableSlideError().setValue(false);
                }
            }
        };
        homeViewModel.getMutableSlideError().observe(getViewLifecycleOwner(), observerError);

        final Observer<Boolean> errorObserver = new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isError) {
                if (isError) {
                    printError();
                    homeViewModel.getMutableFullScreenError().setValue(false);
                }
            }
        };
        homeViewModel.getMutableFullScreenError().observe(getViewLifecycleOwner(), errorObserver);

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
        homeViewModel.getIsLoading().observe(getViewLifecycleOwner(), observerLoading);
    }

    private void initListeners(){
        binding.saveAndUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int reps = binding.etCounterReps.getText().toString().equals("")? 0 : Integer.parseInt(binding.etCounterReps.getText().toString());
                double kg = binding.etCounterKg.getText().toString().equals("")? 0 : Double.parseDouble(binding.etCounterKg.getText().toString());
                if(currentSelectedSerieModel != null){
                   homeViewModel.modifySerie(requireContext(), currentSelectedSerieModel.getId(), reps, kg, exercise.getId());
                } else {
                    //utilizamos el operador ternario como un if: texto es igual(equals) a vacío ("")? (entonces pinta) 0 : (sino) pinta texto

                    homeViewModel.addNewSerie(requireContext(), reps, kg, exercise.getId());
                }
            }
        });

        binding.deleteAndCleanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentSelectedSerieModel != null){
                    showDeleteDialog(currentSelectedSerieModel.getId());
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

    private void instantiateSeriesAdapter(List<SerieModel> serieModelList){
        seriesAdapter = new SeriesAdapter(serieModelList, this);
        binding.addSerieLayout.setHasFixedSize(true);
        binding.addSerieLayout.setLayoutManager(new LinearLayoutManager(this.getContext()));
        binding.addSerieLayout.setAdapter(seriesAdapter);
    }

    @Override
    public void clickListenerInterfaceAdapter(SerieModel input) {
        currentSelectedSerieModel = input;
        boolean isEditMode = input != null;
        printEditMode(isEditMode);
    }
    private void printEditMode(boolean isEditMode){
        if(isEditMode){
            binding.etCounterReps.setText(currentSelectedSerieModel.getReps() != null? Integer.toString(currentSelectedSerieModel.getReps()) : "0");
            binding.etCounterKg.setText(currentSelectedSerieModel.getKg() != null? Double.toString(currentSelectedSerieModel.getKg()) : "0");
            binding.saveAndUpdateBtn.setText(R.string.update);
            binding.saveAndUpdateBtn.setBackgroundResource(R.drawable.shape_serie_add_btn);
            binding.deleteAndCleanBtn.setText(R.string.delete_btn);
            binding.deleteAndCleanBtn.setBackgroundResource(R.drawable.shape_serie_delete_btn);
        } else {
            currentSelectedSerieModel = null;
            binding.etCounterReps.setText("");
            binding.etCounterKg.setText("");
            binding.saveAndUpdateBtn.setText(R.string.add);
            binding.saveAndUpdateBtn.setBackgroundResource(R.drawable.shape_serie_add_and_clean_btns);
            binding.deleteAndCleanBtn.setText(R.string.clean);
            binding.deleteAndCleanBtn.setBackgroundResource(R.drawable.shape_serie_add_and_clean_btns);
        }
    }
    private void printError() {
        try {
            showBlockError();
        } catch (Exception exception) {
            Log.e("Error", "Error to print errorContainer");
        }
    }
    private void showDeleteDialog(int id) {
        ConfirmationDialogFragment.newInstance(AddSerieTrainingFragmentJava.this, ConfirmationDialogFragment.DELETE_SERIE, id).show(getChildFragmentManager(), "ConfirmationDialog");
    }

    @Override
    public void onClickAcceptDelete(int serieId) {
        homeViewModel.deleteSerie(serieId, exercise.getId(), requireContext());
    }
}
