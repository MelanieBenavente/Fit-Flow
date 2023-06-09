package app.fit.fitndflow.ui.features.main.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.fit.fitndflow.R;
import com.fit.fitndflow.databinding.MainListFragmentBinding;

import app.fit.fitndflow.ui.features.common.fragment.CommonToolbarFragment;

public class MainListFragment extends CommonToolbarFragment {

    private MainListFragmentBinding binding;

    @Override
    protected Toolbar getToolbar() {
        return binding.toolbar;
    }

    @Override
    protected int rMenu() {
        return R.menu.itemsmain;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = MainListFragmentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }
}
