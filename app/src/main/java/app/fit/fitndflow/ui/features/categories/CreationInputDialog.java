package app.fit.fitndflow.ui.features.categories;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewbinding.ViewBinding;

import com.fit.fitndflow.R;
import com.fit.fitndflow.databinding.DialogConfirmationFragmentBinding;
import com.fit.fitndflow.databinding.DialogCreationInputBinding;

import app.fit.fitndflow.domain.model.CategoryModel;
import app.fit.fitndflow.ui.features.common.AccessibilityUtils;
import app.fit.fitndflow.ui.features.common.CommonDialogFragment;

public class CreationInputDialog extends CommonDialogFragment {
    public static final int CREATE_CATEGORY = 1;
    public static final int CREATE_EXERCISE = 2;
    private static final String KEY_TYPE = "createType";
    private DialogCreationInputBinding binding;
    private CategoriesAndExercisesViewModel categoriesAndExercisesViewModel;

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
        categoriesAndExercisesViewModel = ViewModelProviders.of(getActivity()).get(CategoriesAndExercisesViewModel.class);
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
        if(categoriesAndExercisesViewModel.getLastName() != null) {
            binding.newCategoryTxt.setText(categoriesAndExercisesViewModel.getLastName());
        }
            if (createType == CREATE_CATEGORY) {
                binding.creationDialogTitle.setText(R.string.choose_category_name);
                binding.newCategoryTxt.setHint("Espalda");
                binding.creationDialogAddBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                            String categoryName = binding.newCategoryTxt.getText().toString();
                            String language = getContext().getString(R.string.language);
                            binding.newCategoryTxt.setAccessibilityDelegate(AccessibilityUtils.createAccesibilityDelegate(language + binding.newCategoryTxt.getText().toString()));
                        if(!binding.newCategoryTxt.getText().toString().equals("")){
                            categoriesAndExercisesViewModel.addNewCategory(requireContext(), language, categoryName);
                        }
                            dismissAllowingStateLoss();
                    }
                });
            } else {
                binding.creationDialogTitle.setText(R.string.choose_exercise_name);
                binding.newCategoryTxt.setHint("Mancuernas");
                binding.creationDialogAddBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(requireContext(), "añade ejercicio", Toast.LENGTH_SHORT).show();
                        dismissAllowingStateLoss();
                        //todo!!! deberá añadir un ejercicio al listado

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
