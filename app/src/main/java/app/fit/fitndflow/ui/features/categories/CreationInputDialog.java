package app.fit.fitndflow.ui.features.categories;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;

import com.fit.fitndflow.databinding.DialogConfirmationFragmentBinding;
import com.fit.fitndflow.databinding.DialogCreationInputBinding;

import app.fit.fitndflow.ui.features.common.CommonDialogFragment;

public class CreationInputDialog extends CommonDialogFragment {
    public static final int CREATE_CATEGORY = 1;
    public static final int CREATE_EXERCISE = 2;
    private static final String KEY_TYPE = "createType";
    private DialogCreationInputBinding binding;
    public static String TAG = "CreationtionDialog";
    private int createType;
    @Override
    protected int getBorderType() {
        return BORDERLINE;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle != null){
            createType = bundle.getInt(KEY_TYPE);
        }
        initListeners();
        return view;
    }

    public static CreationInputDialog newInstance(int createType){
        CreationInputDialog creationInputDialog = new CreationInputDialog();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_TYPE, createType);
        creationInputDialog.setArguments(bundle);

        return creationInputDialog;
    }

    private void initListeners(){
        if(createType == CREATE_CATEGORY){
            binding.creationDialogTitle.setText("Elige un nombre para tu categoría!");
            binding.newCategoryTxt.setHint("Espalda");
            binding.creationDialogAddBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(requireContext(), "añade categoria", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            binding.creationDialogTitle.setText("¡Elige un nombre para tu ejercicio!");
            binding.newCategoryTxt.setHint("Mancuernas");
            binding.creationDialogAddBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(requireContext(), "añade ejercicio", Toast.LENGTH_SHORT).show();
                    dismissAllowingStateLoss();
                }
            });
        }
        binding.creationDialogCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismissAllowingStateLoss();
            }
        });
    }

    @Override
    public ViewBinding getBinding() {
        binding = DialogCreationInputBinding.inflate(getLayoutInflater());
        return binding;
    }
}
