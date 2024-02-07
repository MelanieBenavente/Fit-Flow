package app.fit.fitndflow.ui.features.home;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static app.fit.fitndflow.ui.features.common.notification.MyNotificationManager.scheduleNotification;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewbinding.ViewBinding;

import com.fit.fitndflow.R;
import com.fit.fitndflow.databinding.AddSerieTrainingFragmentBinding;
import com.fit.fitndflow.databinding.DialogCreationInputBinding;
import com.fit.fitndflow.databinding.MainListFragmentBinding;

import java.util.Date;
import java.util.List;

import app.fit.fitndflow.data.common.SharedPrefs;
import app.fit.fitndflow.domain.Utils;
import app.fit.fitndflow.domain.model.CategoryModel;
import app.fit.fitndflow.domain.model.ExerciseModel;
import app.fit.fitndflow.ui.features.categories.CategoriesListFragment;
import app.fit.fitndflow.ui.features.common.CommonFragment;
import app.fit.fitndflow.ui.features.common.notification.MyNotificationManager;
import app.fit.fitndflow.ui.features.training.AddSerieTrainingFragment;

public class HomeFragment extends CommonFragment<HomeViewModel> implements ExerciseClickCallback {
    private MainListFragmentBinding binding;

    @Override
    protected Class getViewModelClass() {
        return HomeViewModel.class;
    }

    private HomeViewModel homeViewModel;

    private boolean isShownNotificationConfiguration = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = MainListFragmentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        super.onCreateView(inflater, container, savedInstanceState);
        if(!isShownNotificationConfiguration){
            MyNotificationManager.askForPermissions(this);
            isShownNotificationConfiguration = true;
        }
        scheduleNotification(requireActivity(), 6000, MyNotificationManager.TRAINING_TYPE);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (SharedPrefs.getApikeyFromSharedPRefs(requireContext()) == null) {
            viewModel.requestRegisterEmptyUser(requireContext());
        }
        setViewModelObservers();
        setClickListeners();
    }

    private void  printExercises(List<CategoryModel> categoryModelList){
        binding.emptyTxt.setVisibility(GONE);
        binding.parentContainer.removeAllViews();
        for (CategoryModel category : categoryModelList) {
            CategoryCustomView  categoryView = new CategoryCustomView(getContext(), category, this);
            binding.parentContainer.addView(categoryView);
        }
    }

    private void printEmptyView(){
        binding.emptyTxt.setVisibility(VISIBLE);
        binding.parentContainer.removeAllViews();
    }

    private void setClickListeners() {
        binding.btnLeft.setOnClickListener(view ->{
            if(!homeViewModel.getIsLoading().getValue()){
                viewModel.dayBefore();
            }
        });
        binding.btnRight.setOnClickListener(view ->{
            if(!homeViewModel.getIsLoading().getValue()) {
                viewModel.dayAfter();
            }
        });
        binding.buttonPanel.setOnClickListener(view ->{
            if(!homeViewModel.getIsLoading().getValue()) {
                addFragment(new CategoriesListFragment());
            }
        });
    }

    private void setViewModelObservers() {
        homeViewModel = ViewModelProviders.of(getActivity()).get(HomeViewModel.class);

        final Observer<List<CategoryModel>> observer = new Observer<List<CategoryModel>>() {
            @Override
            public void onChanged(List<CategoryModel> categories) {
                if(categories == null || categories.isEmpty()){
                    binding.buttonPanel.setText("Añadir entrenamiento");
                    printEmptyView();
                } else {
                    binding.buttonPanel.setText("Añadir ejercicio");
                    printExercises(categories);
                }
            }
        };
        homeViewModel.getDailyTrainingMutableList().observe(getActivity(), observer);

        final Observer<Boolean> observerLoading = new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                try{
                    if (isLoading) {
                        showLoading();

                    } else {
                        hideLoading();
                    }
                }catch(Exception exception){
                    Log.e("Error","show loading");
                }
            }
        };
        homeViewModel.getIsLoading().observe(getActivity(), observerLoading);


        final Observer<Date> actualDateObserver = new Observer<Date>() {
            @Override
            public void onChanged(Date date) {
                homeViewModel.requestTrainingFromModel(requireContext());

                if (Utils.isYesterday(date)) {
                    binding.dateName.setText(R.string.yesterday_date_selector);
                    binding.dayOfWeek.setVisibility(GONE);
                } else if (Utils.isToday(date)) {
                    binding.dateName.setText(R.string.today_date_selector);
                    binding.dayOfWeek.setVisibility(GONE);
                } else if (Utils.isTomorrow(date)) {
                    binding.dateName.setText(R.string.tomorrow_date_selector);
                    binding.dayOfWeek.setVisibility(GONE);
                } else {
                    binding.dateName.setText(Utils.getCalendarFormatDate(date));
                    binding.dayOfWeek.setText(Utils.dayOfWeek(date));
                    binding.dayOfWeek.setVisibility(VISIBLE);
                }
            }
        };
        viewModel.getMutableActualDate().observe(getActivity(), actualDateObserver);
        //observing error
        final Observer<Boolean> errorObserver = new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isError) {
                if (isError) {
                    showBlockError();
                    viewModel.getMutableFullScreenError().setValue(false);
                }
            }
        };
        viewModel.getMutableFullScreenError().observe(getActivity(), errorObserver);
    }

    @Override
    public void showExerciseTrainingDetail(ExerciseModel exercise) {
        addFragment(AddSerieTrainingFragment.newInstance(exercise));
    }
}
