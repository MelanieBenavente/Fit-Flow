package app.fit.fitndflow.ui.features.categories;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.viewbinding.ViewBinding;

import com.fit.fitndflow.R;
import com.fit.fitndflow.databinding.DialogConfirmationFragmentBinding;

import java.io.Serializable;

import app.fit.fitndflow.ui.features.common.CommonDialogFragment;


public class ConfirmationDialogFragment extends CommonDialogFragment {
    public static final int DELETE_CATEGORY = 1;
    public static final int DELETE_EXERCISE = 2;
    private static final String KEY_CALLBACK = "dialogCallback";
    private static final String KEY_TYPE = "deleteType";
    private static final String KEY_POSITION = "position";
    private DialogConfirmationFragmentBinding binding;
    private DialogCallbackDelete dialogCallbackDelete;
    private int deleteType;
    private int position;
    public static String TAG = "ConfirmationDialog";

    @Override
    protected int getBorderType() {
        return BORDERLINE;
    }

    @Override
    public ViewBinding getBinding() {
        binding = DialogConfirmationFragmentBinding.inflate(getLayoutInflater());
        return binding;
    }

    public static ConfirmationDialogFragment newInstance(DialogCallbackDelete dialogCallbackDelete, int deleteType, int position){
        ConfirmationDialogFragment confirmationDialogFragment = new ConfirmationDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_CALLBACK, dialogCallbackDelete);
        bundle.putInt(KEY_TYPE, deleteType);
        bundle.putInt(KEY_POSITION, position);
        confirmationDialogFragment.setArguments(bundle);

        return  confirmationDialogFragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            dialogCallbackDelete = (DialogCallbackDelete) bundle.getSerializable(KEY_CALLBACK);
            deleteType = bundle.getInt(KEY_TYPE);
            position = bundle.getInt(KEY_POSITION);
        }
        initListeners();
        return view;
    }

    private void initListeners(){
        if (deleteType == DELETE_CATEGORY){
            binding.confirmationDialogTitle.setText(R.string.dialog_category_delete);
            binding.confirmationDialogAcceptBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogCallbackDelete.onClickAcceptDeleteCategory();
                }
            });
        } else {
            binding.confirmationDialogTitle.setText(R.string.dialog_exercise_delete);
            binding.confirmationDialogAcceptBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogCallbackDelete.onClickAcceptedExercise(position);
                    dismissAllowingStateLoss();
                }
            });
        }
        binding.confirmationDialogCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismissAllowingStateLoss();
            }
        });
    }
}
