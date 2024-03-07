package app.fit.fitndflow.ui.features.home;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fit.fitndflow.R;

import app.fit.fitndflow.domain.model.CategoryModel;
import app.fit.fitndflow.domain.model.ExerciseModel;

public class CategoryCustomView extends LinearLayout {
    private TextView textView;

    private LinearLayout container;

    public CategoryCustomView(Context context, CategoryModel category, ExerciseClickCallback exerciseClickCallback) {
        super(context);
        inflate(getContext(), R.layout.item_training_category_home, this);
        bindView();
        textView.setText(category.getName());
        for (ExerciseModel exercise : category.getExerciseList()){
            ExerciseCustomView exerciseView = new ExerciseCustomView(getContext(), exercise, exerciseClickCallback);
            container.addView(exerciseView);
        }
    }

    private void bindView(){
        textView = findViewById(R.id.cat_name);
        container = findViewById(R.id.exercises_container);
    }
}
