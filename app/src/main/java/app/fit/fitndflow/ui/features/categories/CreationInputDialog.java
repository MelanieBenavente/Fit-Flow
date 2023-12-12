package app.fit.fitndflow.ui.features.categories;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;

import com.fit.fitndflow.databinding.DialogConfirmationFragmentBinding;
import com.fit.fitndflow.databinding.DialogCreationInputBinding;

import app.fit.fitndflow.ui.features.common.CommonDialogFragment;

public class CreationInputDialog extends CommonDialogFragment {
    public final int CREATE_CATEGORY = 1;
    public final int CREATE_EXERCISE = 2;
    private DialogCreationInputBinding binding;
    public static String TAG = "CreationtionDialog";
    @Override
    protected int getBorderType() {
        return BORDERLINE;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    public static CreationInputDialog newInstance(/*todo!!!!!!!!!!!!!!! bundle*/){
        CreationInputDialog creationInputDialog = new CreationInputDialog();
        return creationInputDialog;
    }

    @Override
    public ViewBinding getBinding() {
        binding = DialogCreationInputBinding.inflate(getLayoutInflater());
        return binding;
    }
}
