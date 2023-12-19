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

public class EditableBtnDeleteExercise extends LinearLayout implements AccessibilityInterface {

    private EditText editText;
    private ImageButton deleteBtn;

    public EditableBtnDeleteExercise(Context context, ExerciseModel exercise, CategoryEditableListener categoryEditableListener, int position) {
        super(context);
        View view = inflate(context, R.layout.component_editable_btn_delete_exercise, this);
        bindViews(view);
        printExercise(exercise);
        setClickListener(categoryEditableListener, position);
        initAccessibility();
        editText.addTextChangedListener(AccessibilityUtils.createTextWatcher(this));

    }

    private void bindViews(View view){
        editText = view.findViewById(R.id.editText);
        deleteBtn = view.findViewById(R.id.button);
    }

    private void printExercise(ExerciseModel exercise){
        editText.setText(exercise.getName());
    }

    private void setClickListener(CategoryEditableListener categoryEditableListener,int position){
        deleteBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryEditableListener.onClickDelete(position);
            }
        });
    }

    @Override
    public void initAccessibility() {
        String exerciseText = getContext().getString(R.string.new_exercise);
        editText.setAccessibilityDelegate(AccessibilityUtils.createAccesibilityDelegate(exerciseText + editText.getText().toString()));
    }
}
