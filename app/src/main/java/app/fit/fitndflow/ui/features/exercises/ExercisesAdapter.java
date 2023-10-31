package app.fit.fitndflow.ui.features.exercises;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.fit.fitndflow.R;

import java.util.List;

import app.fit.fitndflow.domain.model.ExerciseModel;
import app.fit.fitndflow.ui.features.training.SerieAdapterCallback;

public class ExercisesAdapter extends RecyclerView.Adapter<ExercisesAdapter.ViewHolder> {

    private List<ExerciseModel> exerciseModelList;
    private SerieAdapterCallback serieAdapterCallback;

    public ExercisesAdapter(List<ExerciseModel> exerciseModelList, SerieAdapterCallback serieAdapterCallback) {
        this.exerciseModelList = exerciseModelList;
        this.serieAdapterCallback = serieAdapterCallback;
    }

    public void setExerciseModelList(List<ExerciseModel> exerciseModelList) {
        this.exerciseModelList = exerciseModelList;
        notifyDataSetChanged();

    }
    @NonNull
    @Override
    public ExercisesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item_view, parent, false);
        return new ExercisesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ExerciseModel exercise = exerciseModelList.get(position);
        holder.textList.setText(exercise.getName());
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                serieAdapterCallback.showSeries(exerciseModelList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        if (exerciseModelList != null) {
            return exerciseModelList.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textList;
        ConstraintLayout container;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textList = itemView.findViewById(R.id.textList);
            container = itemView.findViewById(R.id.container);
        }
    }
}
