package app.fit.fitndflow.ui.features.common.component;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.fit.fitndflow.R;

import app.fit.fitndflow.domain.model.ExerciseModel;

public class EditableBtnAddExercise extends LinearLayout {

    private EditText editText;

    private ImageButton addBtn;

    public EditableBtnAddExercise(Context context, CategoryEditableListener categoryEditableListener) {
        super(context);
        View view = inflate(context, R.layout.component_editable_add_btn, this);
        bindViews(view);
        setClickListener(categoryEditableListener);
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
                    ExerciseModel exercise = new ExerciseModel(null, name);
                    categoryEditableListener.onClickAdd(exercise);
                }

            }
        });
    }

    public EditText getEditText() {
        return editText;
    }
}
