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

public abstract class CommonDialogFragment extends DialogFragment {
    protected static final int NAKED = 1;
    protected static final int BORDERLINE = 2;
    protected abstract int getBorderType();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getDialog() != null && getDialog().getWindow() != null) {
            if(getBorderType() == NAKED){
                getDialog().getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_white_rounded_corners));
            } else if(getBorderType() == BORDERLINE){
                getDialog().getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_purple_rounded_corners));
            }else{
                getDialog().getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_white_rounded_corners));
            }

            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }

        View view = getBinding().getRoot();
        super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    public abstract ViewBinding getBinding();
}
