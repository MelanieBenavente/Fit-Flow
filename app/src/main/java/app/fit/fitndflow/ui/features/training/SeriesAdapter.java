package app.fit.fitndflow.ui.features.training;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fit.fitndflow.R;

import java.util.List;

import app.fit.fitndflow.domain.model.SerieModel;

public class SeriesAdapter extends RecyclerView.Adapter<SeriesAdapter.ViewHolder> {

    private List<SerieModel> serieModelList;
    private TrainingCallback trainingCallback;

    private ViewHolder lastItemClicked;

    public SeriesAdapter(TrainingCallback trainingCallback) {
        //todo pasarle y setearle solo un callback
        this.trainingCallback = trainingCallback;
    }

    public SeriesAdapter(List<SerieModel> serieModelList, TrainingCallback trainingCallback) { //todo pasarle un callback tambien y setearlo
        this.serieModelList = serieModelList;
        this.trainingCallback = trainingCallback;
    }

    public void setSerieModelList(List<SerieModel> serieModelList) {
        this.serieModelList = serieModelList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SeriesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item_add_serie, parent, false);
        return new SeriesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeriesAdapter.ViewHolder holder, int position) {
        //todo aqui, deberia de modificar el fragment a traves del callback
        // (si por ejemplo le clico a numreps, en el fragment (dentro de repeticiones),
        // me deberia de salir el mismo numero. esto se hace mediante el callback

        if (position < serieModelList.size()) {
            SerieModel serieModel = serieModelList.get(position);
            if (serieModel.getReps() != null) {
                holder.numReps.setText(Integer.toString(serieModel.getReps()));
            }
            if (serieModel.getKg() != null) {
                holder.numKg.setText(Double.toString(serieModel.getKg()));
            }
            holder.linearContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (lastItemClicked != null && lastItemClicked != holder) {
                        lastItemClicked.isEditMode = false;
                        lastItemClicked.linearContainer.setBackgroundResource(R.drawable.shape_white_rounded_corners);
                    }

                    holder.isEditMode = !holder.isEditMode;
                    if (holder.isEditMode) {
                        holder.linearContainer.setBackgroundResource(R.drawable.shape_et_add_note_rounded_corners_light_purple);
                        trainingCallback.clickListenerInterfaceAdapter(serieModelList.get(position));
                    } else {
                        holder.linearContainer.setBackgroundResource(R.drawable.shape_white_rounded_corners);
                        trainingCallback.clickListenerInterfaceAdapter(null);
                    }
                    lastItemClicked = holder;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (serieModelList == null) {
            return 0;
        }
        return serieModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView numReps;
        TextView numKg;
        LinearLayout linearContainer;
        boolean isEditMode;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            linearContainer = itemView.findViewById(R.id.linearContainer);
            numKg = itemView.findViewById(R.id.kgEdTxt);
            numReps = itemView.findViewById(R.id.repsEdTxt);
        }
    }
}
