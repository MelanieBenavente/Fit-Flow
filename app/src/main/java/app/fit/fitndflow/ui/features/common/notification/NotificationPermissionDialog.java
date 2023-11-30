package app.fit.fitndflow.ui.features.common.notification;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.fit.fitndflow.R;
import com.fit.fitndflow.databinding.DialogNotificationPermissionBinding;
import com.fit.fitndflow.databinding.MainListFragmentBinding;

public class NotificationPermissionDialog extends DialogFragment implements View.OnClickListener {
    private DialogNotificationPermissionBinding binding;
    public static String TAG = "NotificationDialog";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DialogNotificationPermissionBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        super.onCreateView(inflater, container, savedInstanceState);
        initListeners();
        return view;
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


