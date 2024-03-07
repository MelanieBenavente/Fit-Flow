package app.fit.fitndflow.ui.features.home;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fit.fitndflow.R;

import app.fit.fitndflow.domain.model.SerieModel;

public class SerieCustomView extends LinearLayout {
    private TextView textReps;
    private TextView textKg;

    private ImageView iconNews;

    public SerieCustomView(Context context, SerieModel serie, boolean isFirst) {
        super(context);
        inflate(getContext(), R.layout.item_training_series_layout, this);
        bindView();
        String repsToString = serie.getReps() != null? Integer.toString(serie.getReps()) : "0";
        textReps.setText("x "+repsToString);
        String kgToString = serie.getKg() != null? Double.toString(serie.getKg()) : "0";
        textKg.setText(kgToString+" kg");
        if(isFirst){
            iconNews.setVisibility(VISIBLE);
        } else {
            iconNews.setVisibility(INVISIBLE);
        }
    }

    private void bindView(){
        textReps = findViewById(R.id.number_reps);
        textKg = findViewById(R.id.number_kg);
        iconNews = findViewById(R.id.news);
    }
}
