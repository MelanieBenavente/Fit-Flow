package app.fit.fitndflow.ui.features.excercises;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.fit.fitndflow.R;

import java.util.List;

import app.fit.fitndflow.domain.model.ExcerciseModel;

public class ExcercisesAdapter extends RecyclerView.Adapter<ExcercisesAdapter.ViewHolder> {

    private List<ExcerciseModel> excerciseModelList;
    public ExcercisesAdapter(List<ExcerciseModel> excerciseModelList) {
        this.excerciseModelList = excerciseModelList;
    }

    @NonNull
    @Override
    public ExcercisesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item_view, parent, false);
        return new ExcercisesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ExcerciseModel excercise = excerciseModelList.get(position);
        holder.textList.setText(excercise.getName());
    }

    @Override
    public int getItemCount() {
        if(excerciseModelList != null){
            return excerciseModelList.size();
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
