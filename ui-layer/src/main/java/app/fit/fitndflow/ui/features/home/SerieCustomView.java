package app.fit.fitndflow.ui.features.home;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import app.fit.fitndflow.domain.model.SerieModel;
import app.fit.fitndflow.ui.R;

public class SerieCustomView extends LinearLayout {
    private TextView textReps;
    private TextView textKg;

    private ImageView iconChampion;

    public SerieCustomView(Context context, SerieModel serie) {
        super(context);
        inflate(getContext(), R.layout.item_training_series_layout, this);
        bindView();
        String repsToString = serie.getReps() != null? Integer.toString(serie.getReps()) : "0";
        textReps.setText("x "+repsToString);
        String kgToString = serie.getKg() != null? Double.toString(serie.getKg()) : "0";
        textKg.setText(kgToString+" kg");

        boolean isRecord = Boolean.TRUE.equals(serie.isRecord());
        if(isRecord){
            iconChampion.setVisibility(VISIBLE);
        } else {
            iconChampion.setVisibility(INVISIBLE);
        }
    }

    private void bindView(){
        textReps = findViewById(R.id.number_reps);
        textKg = findViewById(R.id.number_kg);
        iconChampion = findViewById(R.id.championsImg);
    }
}
