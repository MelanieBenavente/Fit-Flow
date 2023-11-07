package app.fit.fitndflow.ui.features.training;

import static android.provider.Settings.System.getString;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.fit.fitndflow.R;

import app.fit.fitndflow.domain.model.SerieModel;
import app.fit.fitndflow.ui.features.common.AccessibilityInterface;
import app.fit.fitndflow.ui.features.common.AccessibilityUtils;

public class EditableBtnAddSerie extends LinearLayout implements AccessibilityInterface {

    private EditText editTxtReps;
    private EditText editTxtKg;
    private ImageButton addBtn;

    public EditableBtnAddSerie(Context context, SerieEditableListener serieEditableListener) {
        super(context);
        View view = inflate(context, R.layout.component_editable_btn_add_serie_training, this);
        bindViews(view);
        initAccessibility();
        setClickListener(serieEditableListener);
        editTxtReps.addTextChangedListener(AccessibilityUtils.createTextWatcher(this));
        editTxtKg.addTextChangedListener(AccessibilityUtils.createTextWatcher(this));
    }

    private void bindViews(View view){
        editTxtReps = view.findViewById(R.id.repsEdTxt);
        editTxtKg = view.findViewById(R.id.kgEdTxt);
        addBtn = view.findViewById(R.id.buttonAddSerie);
    }

    private void setClickListener(SerieEditableListener serieEditableListener){
        addBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String repsString = editTxtReps.getText().toString();
                int reps = repsString.equals("")? 0 : Integer.parseInt(repsString);
                String kgString = editTxtKg.getText().toString();
                double kg = kgString.equals("")? 0 : Double.parseDouble(kgString);
                SerieModel serie = new SerieModel(reps, kg);
                serieEditableListener.onClickAdd(serie);
            }
        });
    }

    @Override
    public void initAccessibility() {
        String reps = getContext().getString(R.string.talkback_reps_edit_text);
        editTxtReps.setAccessibilityDelegate(AccessibilityUtils.createAccesibilityDelegate(reps + editTxtReps.getText().toString()));
        String kg = getContext().getString(R.string.talkback_kg_edit_text);
        editTxtKg.setAccessibilityDelegate(AccessibilityUtils.createAccesibilityDelegate(kg + editTxtKg.getText().toString()));
    }
}
