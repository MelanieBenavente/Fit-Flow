package app.fit.fitndflow.ui.features.home;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;

import com.fit.fitndflow.R;
import com.fit.fitndflow.databinding.MainListFragmentBinding;

import java.util.Date;

import app.fit.fitndflow.data.common.SharedPrefs;
import app.fit.fitndflow.domain.Utils;
import app.fit.fitndflow.domain.model.UserModel;
import app.fit.fitndflow.ui.features.categories.CategoriesListFragment;
import app.fit.fitndflow.ui.features.common.CommonActivity;
import app.fit.fitndflow.ui.features.common.CommonToolbarFragment;

public class HomeFragment extends CommonToolbarFragment<HomeViewModel> {
    private MainListFragmentBinding binding;

    @Override
    protected Toolbar getToolbar() {
        return binding.toolbar;
    }

    @Override
    protected int rMenu() {
        return R.menu.itemsmain;
    }

    @Override
    protected Class getViewModelClass() {
        return HomeViewModel.class;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = MainListFragmentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        ((CommonActivity)requireActivity()).showErrorSlideContainer();
        super.onCreateView(inflater, container, savedInstanceState);
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

    private void setClickListeners() {
        binding.btnLeft.setOnClickListener(view -> viewModel.dayBefore());
        binding.btnRight.setOnClickListener(view -> viewModel.dayAfter());
        binding.buttonPanel.setOnClickListener(view -> nextFragment(new CategoriesListFragment()));
    }

    private void setViewModelObservers() {
        final Observer<UserModel> userObserver = new Observer<UserModel>() {
            @Override
            public void onChanged(UserModel userModel) {

                Toast.makeText(requireContext(), "User Registered Succesful", Toast.LENGTH_LONG).show();
            }
        };
        //Observamos al listado del ViewModel y ejecutamos las acciones indicadas antes en el observer
        viewModel.getMutableUserModel().observe(getActivity(), userObserver);

        final Observer<Date> actualDateObserver = new Observer<Date>() {
            @Override
            public void onChanged(Date date) {
                if (Utils.isYesterday(date)) {
                    binding.dateName.setText(R.string.yesterday_date_selector);
                    binding.dayOfWeek.setVisibility(View.GONE);
                } else if (Utils.isToday(date)) {
                    binding.dateName.setText(R.string.today_date_selector);
                    binding.dayOfWeek.setVisibility(View.GONE);
                } else if (Utils.isTomorrow(date)) {
                    binding.dateName.setText(R.string.tomorrow_date_selector);
                    binding.dayOfWeek.setVisibility(View.GONE);
                } else {
                    binding.dateName.setText(Utils.getCalendarFormatDate(date));
                    binding.dayOfWeek.setText(Utils.dayOfWeek(date));
                    binding.dayOfWeek.setVisibility(View.VISIBLE);
                }
            }
        };
        viewModel.getActualDate().observe(getActivity(), actualDateObserver);

        //observing error
        final Observer<Boolean> errorObserver = new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isError) {
                if (isError) {
                    Toast.makeText(requireContext(), "Error trying to register", Toast.LENGTH_LONG).show();
                }
            }
        };
        viewModel.getMutableError().observe(getActivity(), errorObserver);
    }
}
