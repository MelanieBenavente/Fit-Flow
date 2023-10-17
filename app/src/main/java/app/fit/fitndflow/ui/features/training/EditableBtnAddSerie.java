package app.fit.fitndflow.ui.features.training;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.fit.fitndflow.R;

import app.fit.fitndflow.domain.model.SerieModel;

public class EditableBtnAddSerie extends LinearLayout {

    private EditText editTxtReps;
    private EditText editTxtKg;
    private ImageButton addBtn;
    public EditableBtnAddSerie(Context context, SerieEditableListener serieEditableListener) {
        super(context);
        View view = inflate(context, R.layout.component_editable_btn_add_serie_training, this);
        bindViews(view);
        setClickListener(serieEditableListener);
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
                SerieModel serie = new SerieModel(reps, kg, null);
                serieEditableListener.onClickAdd(serie);
            }
        });
    }

    public EditText getEditTxtReps(){return editTxtReps;}

    public EditText getEditTxtKg(){return editTxtKg;}
}
