package app.fit.fitndflow.ui.features.training;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fit.fitndflow.databinding.AddSerieTrainingFragmentBinding;

import app.fit.fitndflow.domain.model.ExcerciseModel;
import app.fit.fitndflow.ui.features.categories.CategoriesAndExcercisesViewModel;
import app.fit.fitndflow.ui.features.common.CommonFragment;

public class AddSerieTrainingFragment extends CommonFragment {

    public static final String KEY_EXCERCISE = "actualExcercise";
    private AddSerieTrainingFragmentBinding binding;

    private CategoriesAndExcercisesViewModel categoriesAndExcercisesViewModel;


    private ExcerciseModel excerciseModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = AddSerieTrainingFragmentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        super.onCreateView(inflater, container, savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle != null){
            excerciseModel = (ExcerciseModel) bundle.getSerializable(KEY_EXCERCISE);
        }
        binding.excerciseNameTitle.setText(excerciseModel.getName());
        return view;
    }

    @Override
    protected Class getViewModelClass() {
        return CategoriesAndExcercisesViewModel.class;
    }

    public static AddSerieTrainingFragment newInstance(ExcerciseModel excercise){
        AddSerieTrainingFragment addSerieTrainingFragment = new AddSerieTrainingFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_EXCERCISE, excercise);
        addSerieTrainingFragment.setArguments(bundle);
        return addSerieTrainingFragment;
    }

}
