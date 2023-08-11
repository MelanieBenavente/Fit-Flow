package app.fit.fitndflow.ui.features.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;

import com.fit.fitndflow.R;
import com.fit.fitndflow.databinding.MainListFragmentBinding;

import app.fit.fitndflow.data.common.SharedPrefs;
import app.fit.fitndflow.domain.model.UserModel;
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //if is not registered... register now with empty user
        if (SharedPrefs.getApikeyFromSharedPRefs(getContext()) == null) {
            viewModel.requestRegisterEmptyUser(getContext());
        }
        setViewModelObservers();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = MainListFragmentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        super.onCreateView(inflater, container, savedInstanceState);
        return view;
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

        //observing error
        final Observer<Boolean> errorObserver = new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isError) {
                if(isError) {
                    Toast.makeText(requireContext(), "Error trying to register", Toast.LENGTH_LONG).show();
                }
            }
        };
        viewModel.getMutableError().observe(getActivity(), errorObserver);
    }


}
