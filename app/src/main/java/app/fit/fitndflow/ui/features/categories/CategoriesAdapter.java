package app.fit.fitndflow.ui.features.categories;

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

import app.fit.fitndflow.domain.model.CategoryModel;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {
    private List<CategoryModel> categoryModelList;
    private CategoryAdapterCallback categoryAdapterCallback;
    private boolean mIsEditMode;
    public static final int SINGLE_TYPE = 1;
    public static final int SINGLE_LAST_TYPE = 2;

    public CategoriesAdapter(CategoryAdapterCallback categoryAdapterCallback) {
        this.categoryAdapterCallback = categoryAdapterCallback;
    }

    public void setCategoryList(List<CategoryModel> categoryList) {
        this.categoryModelList = categoryList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoriesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if(viewType == SINGLE_TYPE){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item_view, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item_last_view, parent, false);
        }
        return new CategoriesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesAdapter.ViewHolder holder, int position) {
       if(position < categoryModelList.size()) {
           CategoryModel category = categoryModelList.get(position);
           holder.textList.setText(category.getName());
           if(mIsEditMode){
               holder.cancelBtn.setVisibility(VISIBLE);
                   holder.cancelBtn.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {
                           categoryAdapterCallback.showDeleteDialog(-1);
                       }
                   });
           } else {
               holder.cancelBtn.setVisibility(INVISIBLE);
           }
           holder.textList.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   categoryAdapterCallback.showExercises(categoryModelList.get(position));
               }
           });
       } else {
           holder.textList.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   categoryAdapterCallback.showCreationDialog();
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
        if(categoryModelList != null){
            return categoryModelList.size()+1;
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (position < categoryModelList.size()){
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
            cancelBtn = itemView.findViewById(R.id.cancel_single_item_btn);
            textList = itemView.findViewById(R.id.textList);
            container = itemView.findViewById(R.id.container);
        }
    }
}
