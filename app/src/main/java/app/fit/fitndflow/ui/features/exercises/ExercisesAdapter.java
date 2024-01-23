package app.fit.fitndflow.ui.features.exercises;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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
    private boolean mIsEditMode;
    public static final int SINGLE_TYPE = 1;
    public static final int SINGLE_LAST_TYPE = 2;

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
        View view;
        if(viewType == SINGLE_TYPE){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item_view, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item_last_view, parent, false);

        }
        return new ExercisesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(position < exerciseModelList.size()) {
            ExerciseModel exercise = exerciseModelList.get(position);
            holder.textList.setText(exercise.getName());
            if(mIsEditMode){
                holder.cancelBtn.setVisibility(VISIBLE);
                holder.cancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        serieAdapterCallback.showDeleteDialog(exercise.getId());
                    }
                });
            } else {
                holder.cancelBtn.setVisibility(INVISIBLE);
            }
            holder.textList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mIsEditMode){
                        serieAdapterCallback.showModifyDialog(exercise.getId(), exercise.getName());
                    } else {
                        serieAdapterCallback.showSeries(exerciseModelList.get(position));
                    }
                }
            });
        } else {
            holder.textList.setText(R.string.exercise_new);
            holder.textList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    serieAdapterCallback.showCreationDialog();
                }
            });
        }
    }
    public void setEditMode(boolean isEditMode){
        mIsEditMode = isEditMode;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (exerciseModelList != null) {
            return exerciseModelList.size()+1;
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (position < exerciseModelList.size()){
            return SINGLE_TYPE;
        } else {
            return SINGLE_LAST_TYPE;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textList;
        ConstraintLayout container;
        ImageButton cancelBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textList = itemView.findViewById(R.id.textList);
            container = itemView.findViewById(R.id.container);
            cancelBtn = itemView.findViewById(R.id.cancel_single_item_btn);

        }
    }
}
