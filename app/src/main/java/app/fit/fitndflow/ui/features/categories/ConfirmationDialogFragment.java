package app.fit.fitndflow.ui.features.categories;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.fit.fitndflow.R;

public class ConfirmationDialogFragment extends DialogFragment {
    public static final int DELETE_CATEGORY = 1;
    public static final int DELETE_EXERCISE = 2;

    private DialogCallbackDelete dialogCallbackDelete;
    private int deleteType;

    private int position;

    public static ConfirmationDialogFragment newInstance(DialogCallbackDelete dialogCallbackDelete, int deleteType, int position){
        ConfirmationDialogFragment confirmationDialogFragment = new ConfirmationDialogFragment();
        confirmationDialogFragment.dialogCallbackDelete = dialogCallbackDelete;
        confirmationDialogFragment.deleteType = deleteType;
        confirmationDialogFragment.position = position;
        return  confirmationDialogFragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        String text;
        if (deleteType == DELETE_CATEGORY){
            text = getString(R.string.dialog_category_delete);
        } else {
            text = getString(R.string.dialog_exercise_delete);
        }

        return new AlertDialog.Builder(requireContext())
                .setMessage(text)
                .setPositiveButton(getString(R.string.dialog_btn_delete), (dialog, which) -> {
                    if (deleteType == DELETE_CATEGORY) {
                        dialogCallbackDelete.onClickAcceptDeleteCategory();
                    } else{
                        dialogCallbackDelete.onClickAcceptedExercise(position);
                    }
                })
                .create();
    }
    public static String TAG = "ConfirmationDialog";

}
