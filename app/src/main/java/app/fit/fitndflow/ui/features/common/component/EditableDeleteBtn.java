package app.fit.fitndflow.ui.features.common.component;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.fit.fitndflow.R;

import app.fit.fitndflow.domain.model.ExcerciseModel;

public class EditableDeleteBtn extends LinearLayout {

    private EditText editText;
    private ImageButton deleteBtn;

    public EditableDeleteBtn(Context context, ExcerciseModel excercise, CategoryEditableListener categoryEditableListener, int position) {
        super(context);
        View view = inflate(context, R.layout.component_editable_delete_btn, this);
        bindViews(view);
        printExcercise(excercise);
        setClickListener(categoryEditableListener, position);
    }

    private void bindViews(View view){
        editText = view.findViewById(R.id.editText);
        deleteBtn = view.findViewById(R.id.button);
    }

    private void printExcercise(ExcerciseModel excercise){
        editText.setText(excercise.getName());
    }

    private void setClickListener(CategoryEditableListener categoryEditableListener,int position){
        deleteBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryEditableListener.onClickDelete(position);
            }
        });
    }
}
