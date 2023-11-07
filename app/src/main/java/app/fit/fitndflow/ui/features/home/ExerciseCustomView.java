package app.fit.fitndflow.ui.features.home;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fit.fitndflow.R;

import app.fit.fitndflow.domain.model.ExerciseModel;
import app.fit.fitndflow.domain.model.SerieModel;

public class ExerciseCustomView extends LinearLayout {
    private TextView textView;
    private LinearLayout container;
    public ExerciseCustomView(Context context, ExerciseModel exercise) {
        super(context);
        inflate(getContext(), R.layout.item_training_exercises_home, this);
        bindView();
        textView.setText(exercise.getName());
        for (int i = 0; i < exercise.getSerieList().size(); i++){
            SerieModel serie = exercise.getSerieList().get(i);
            boolean isFirst = i == 0;
            SerieCustomView serieView = new SerieCustomView(getContext(), serie, isFirst);
            container.addView(serieView);
        }
    }

    private void bindView(){
        textView = findViewById(R.id.txt_exercise);
        container = findViewById(R.id.series_container);
    }
}
