package app.fit.fitndflow.ui.features.common.notification;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.viewbinding.ViewBinding;

import com.fit.fitndflow.R;
import com.fit.fitndflow.databinding.DialogNotificationConfigurationBinding;
import com.fit.fitndflow.databinding.DialogNotificationPermissionBinding;

import app.fit.fitndflow.data.common.SharedPrefs;
import app.fit.fitndflow.ui.features.common.CommonDialogFragment;

public class NotificationConfigurationDialog extends CommonDialogFragment implements View.OnClickListener {

    private DialogNotificationConfigurationBinding binding;

    public static String TAG = "NotificationConfigurationDialog";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        initListeners();
        return view;
    }

    @Override
    public ViewBinding getBinding() {
        binding = DialogNotificationConfigurationBinding.inflate(getLayoutInflater());
        return binding;
    }

    private void initListeners() {
        binding.buttonPanel.setOnClickListener(this);
        binding.closeButton.setOnClickListener(this);
        binding.checkboxNoMostrarMas.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPrefs.get(requireContext()).saveDontShowNotification(isChecked);
            }
        });

    }
        @Override
        public void onClick (View view){
            switch (view.getId()) {
                case R.id.buttonPanel:
                    MyNotificationManager.launchNotificationSettings(requireContext());
                    break;
            }
            dismissAllowingStateLoss();
        }
    }

