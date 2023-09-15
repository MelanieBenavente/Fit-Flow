package app.fit.fitndflow.ui.features.common.component;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.fit.fitndflow.R;

public abstract class EditableComponent extends LinearLayout {

    private EditText editText;
    private int position;

    protected abstract View getView();
    public EditableComponent(Context context, int position) {
        super(context);
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    protected void bindViews(){
        editText = getView().findViewById(R.id.editText);
    }

    public EditText getEditText() {
        return editText;
    }
}
