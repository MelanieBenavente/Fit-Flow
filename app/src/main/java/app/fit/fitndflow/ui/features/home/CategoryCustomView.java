package app.fit.fitndflow.ui.features.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fit.fitndflow.R;
import com.fit.fitndflow.databinding.ItemTrainingCategoryHomeBinding;
import com.fit.fitndflow.databinding.MainListFragmentBinding;

import app.fit.fitndflow.domain.model.CategoryModel;
import app.fit.fitndflow.domain.model.ExerciseModel;

public class CategoryCustomView extends LinearLayout {
    private TextView textView;

    private LinearLayout container;

    public CategoryCustomView(Context context, CategoryModel category) {
        super(context);
        inflate(getContext(), R.layout.item_training_category_home, this);
        bindView();
        textView.setText(category.getName());
        for (ExerciseModel exercise : category.getExerciseList()){
            ExerciseCustomView exerciseView = new ExerciseCustomView(getContext(), exercise);
            container.addView(exerciseView);
        }
    }

    private void bindView(){
        textView = findViewById(R.id.cat_name);
        container = findViewById(R.id.exercises_container);
    }
}
