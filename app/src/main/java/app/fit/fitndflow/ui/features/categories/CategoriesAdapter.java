package app.fit.fitndflow.ui.features.categories;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.fit.fitndflow.R;

import java.util.List;

import app.fit.fitndflow.domain.model.ItemModel;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {
    private List<ItemModel> itemModelList;

    public void setCategoryList(List<ItemModel> categoryList) {
        this.itemModelList = categoryList;
    }
    @NonNull
    @Override
    public CategoriesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item_view, parent, false);
        return new CategoriesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesAdapter.ViewHolder holder, int position) {
        ItemModel category = itemModelList.get(position);
        holder.textList.setText(category.getName());
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(itemModelList.get(position).getExerciseList() != null && !itemModelList.get(position).getExerciseList().isEmpty()){
                    itemModelList = itemModelList.get(position).getExerciseList();
                    notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if(itemModelList != null){
            return itemModelList.size();
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
