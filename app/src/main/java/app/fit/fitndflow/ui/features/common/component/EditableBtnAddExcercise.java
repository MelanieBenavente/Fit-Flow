package app.fit.fitndflow.ui.features.common.component;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.fit.fitndflow.R;

import app.fit.fitndflow.domain.model.ExcerciseModel;

public class EditableBtnAddExcercise extends LinearLayout {

    private EditText editText;

    private ImageButton addBtn;

    public EditableBtnAddExcercise(Context context, CategoryEditableListener categoryEditableListener) {
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
                    ExcerciseModel excercise = new ExcerciseModel(name);
                    categoryEditableListener.onClickAdd(excercise);
                }

            }
        });
    }

    public EditText getEditText() {
        return editText;
    }
}
