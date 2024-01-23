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
import com.fit.fitndflow.databinding.DialogCreationInputBinding;

import app.fit.fitndflow.domain.model.CategoryModel;
import app.fit.fitndflow.ui.features.common.AccessibilityUtils;
import app.fit.fitndflow.ui.features.common.CommonDialogFragment;

public class CreationOrModifyInputDialog extends CommonDialogFragment {
    public static final int TYPE_CATEGORY = 1;
    public static final int TYPE_EXERCISE = 2;
    private static final String KEY_TYPE = "createType";
    private static final String KEY_ID = "keyId";
    private static final String KEY_NAME = "keyName";
    private DialogCreationInputBinding binding;
    private CategoriesAndExercisesViewModel categoriesAndExercisesViewModel;

    public static String TAG = "CreationtionDialog";
    private int createType;
    private int id;
    private String name;

    @Override
    protected int getBorderType() {
        return BORDERLINE;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            createType = bundle.getInt(KEY_TYPE);
            id = bundle.getInt(KEY_ID);
            name = bundle.getString(KEY_NAME);
        }
        categoriesAndExercisesViewModel = ViewModelProviders.of(getActivity()).get(CategoriesAndExercisesViewModel.class);
        initListeners();
        return view;
    }

    public static CreationOrModifyInputDialog newInstance(int createType, int id, String name) {
        CreationOrModifyInputDialog modifyInputDialog = new CreationOrModifyInputDialog();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_TYPE, createType);
        bundle.putInt(KEY_ID, id);
        bundle.putString(KEY_NAME, name);
        modifyInputDialog.setArguments(bundle);

        return modifyInputDialog;
    }

    public static CreationOrModifyInputDialog newInstance(int createType) {
        CreationOrModifyInputDialog creationOrModifyInputDialog = new CreationOrModifyInputDialog();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_TYPE, createType);
        creationOrModifyInputDialog.setArguments(bundle);

        return creationOrModifyInputDialog;
    }

    private void initListeners() {
        if (id != 0) {
            binding.newCategoryTxt.setText(name);
        } else if (categoriesAndExercisesViewModel.getLastName() != null) {
            binding.newCategoryTxt.setText(categoriesAndExercisesViewModel.getLastName());
        }
        if (createType == TYPE_CATEGORY) {
            binding.creationDialogTitle.setText(R.string.choose_category_name);
            binding.newCategoryTxt.setHint("Espalda");
            binding.creationDialogAddBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String categoryName = binding.newCategoryTxt.getText().toString();
                    String language = getContext().getString(R.string.language);
                    binding.newCategoryTxt.setAccessibilityDelegate(AccessibilityUtils.createAccesibilityDelegate(language + binding.newCategoryTxt.getText().toString()));
                    if (!binding.newCategoryTxt.getText().toString().equals("")) {
                        if (id != 0) {
                            categoriesAndExercisesViewModel.modifyCategory(requireContext(), language, categoryName, id);
                        } else {
                            categoriesAndExercisesViewModel.addNewCategory(requireContext(), language, categoryName);
                        }
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
                    String exerciseName = binding.newCategoryTxt.getText().toString();
                    String language = "es";
                    binding.newCategoryTxt.setAccessibilityDelegate(AccessibilityUtils.createAccesibilityDelegate(language + binding.newCategoryTxt.getText().toString()));
                    if (!binding.newCategoryTxt.getText().toString().equals("")) {
                        if (id != 0) {
                            categoriesAndExercisesViewModel.modifyExercise(requireContext(), id, language, exerciseName);
                        } else {
                            categoriesAndExercisesViewModel.addNewExercise(requireContext(), language, exerciseName);
                        }
                    }
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
