package app.fit.fitndflow.ui.features.common.component;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.fit.fitndflow.R;

import app.fit.fitndflow.domain.model.ExcerciseModel;

public class EditableAddBtn extends EditableComponent {
    private ImageButton addBtn;

    private View view;

    public EditableAddBtn(Context context, CategoryEditableListener categoryEditableListener, int position) {
        super(context, position);
        view = inflate(context, R.layout.component_editable_add_btn, this);
        bindViews(view);
        super.bindViews();
        setClickListener(categoryEditableListener);
    }

    private void bindViews(View view){
        addBtn = view.findViewById(R.id.button);
    }

    private void setClickListener(CategoryEditableListener categoryEditableListener){
        addBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = getEditText().getText().toString();
                if(name != null && !name.equals("")){
                    ExcerciseModel excercise = new ExcerciseModel(name);
                    categoryEditableListener.onClickAdd(excercise);
                }

            }
        });
    }

    @Override
    protected View getView() {
        return view;
    }
}
