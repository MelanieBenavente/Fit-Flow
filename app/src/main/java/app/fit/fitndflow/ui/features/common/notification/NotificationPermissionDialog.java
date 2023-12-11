package app.fit.fitndflow.ui.features.common.notification;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;

import com.fit.fitndflow.R;
import com.fit.fitndflow.databinding.DialogNotificationPermissionBinding;

import app.fit.fitndflow.ui.features.common.CommonDialogFragment;

public class NotificationPermissionDialog extends CommonDialogFragment implements View.OnClickListener {
    private DialogNotificationPermissionBinding binding;
    public static String TAG = "NotificationDialog";


    @Override
    protected int getBorderType() {
        return NAKED;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        initListeners();
        return view;
    }

    @Override
    public ViewBinding getBinding() {
        binding = DialogNotificationPermissionBinding.inflate(getLayoutInflater());
        return binding;
    }


    private void initListeners(){
        binding.buttonPanel.setOnClickListener(this);
        binding.closeButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

            switch (view.getId()) {
                case R.id.buttonPanel:
                    MyNotificationManager.requestPermission(requireActivity());
                    break;
            }
            dismissAllowingStateLoss();
        }
}


