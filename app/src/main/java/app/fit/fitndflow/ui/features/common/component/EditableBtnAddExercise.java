package app.fit.fitndflow.ui.features.common.component;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.fit.fitndflow.R;

import app.fit.fitndflow.domain.model.ExerciseModel;
import app.fit.fitndflow.ui.features.common.AccessibilityInterface;
import app.fit.fitndflow.ui.features.common.AccessibilityUtils;
@Deprecated
public class EditableBtnAddExercise extends LinearLayout implements AccessibilityInterface {

    private EditText editText;

    private ImageButton addBtn;


    public EditableBtnAddExercise(Context context, CategoryEditableListener categoryEditableListener) {
        super(context);
        View view = inflate(context, R.layout.component_editable_btn_add_exercise, this);
        bindViews(view);
        initAccessibility();
        setClickListener(categoryEditableListener);
        editText.addTextChangedListener(AccessibilityUtils.createTextWatcher(this));
    }

    private void bindViews(View view){
        editText = view.findViewById(R.id.editText);
        addBtn = view.findViewById(R.id.button);
    }

    private void setClickListener(CategoryEditableListener categoryEditableListener){
        addBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editText.getText().toString();
                if(name != null && !name.equals("")){
                    ExerciseModel exercise = new ExerciseModel(null, name, null);
                    categoryEditableListener.onClickAdd(exercise);
                }

            }
        });
    }

    public EditText getEditText() {
        return editText;
    }

    @Override
    public void initAccessibility() {
        String exerciseText = getContext().getString(R.string.new_exercise);
        editText.setAccessibilityDelegate(AccessibilityUtils.createAccesibilityDelegate(exerciseText + editText.getText().toString()));
    }
}
