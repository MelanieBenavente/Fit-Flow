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

import app.fit.fitndflow.ui.features.common.CommonDialogFragment;


public class ConfirmationDialogFragment extends CommonDialogFragment {
    public static final int DELETE_CATEGORY = 1;
    public static final int DELETE_EXERCISE = 2;
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
        confirmationDialogFragment.dialogCallbackDelete = dialogCallbackDelete;
        confirmationDialogFragment.deleteType = deleteType;
        confirmationDialogFragment.position = position;

        //todo!!!!! pasarlo a bundle
        return  confirmationDialogFragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
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
