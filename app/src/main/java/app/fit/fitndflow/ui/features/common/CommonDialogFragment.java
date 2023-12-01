package app.fit.fitndflow.ui.features.common;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.viewbinding.ViewBinding;

import com.fit.fitndflow.R;
import com.fit.fitndflow.databinding.DialogNotificationConfigurationBinding;

public abstract class CommonDialogFragment extends DialogFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_white_rounded_corners));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }

        View view = getBinding().getRoot();
        super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    public abstract ViewBinding getBinding();
}
