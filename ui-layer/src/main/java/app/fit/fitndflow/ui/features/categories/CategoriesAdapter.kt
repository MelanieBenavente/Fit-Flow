package app.fit.fitndflow.ui.features.categories

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import app.fit.fitndflow.ui.R
import app.fit.fitndflow.ui.features.common.getTranslatedString
import com.fit.fitndflow.app.domain.common.models.CategoryModel

class CategoriesAdapter(private val categoryAdapterCallback: CategoryAdapterCallback) :
    RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {
    private var categoryModelList: List<CategoryModel>? = null
    private var mIsEditMode = false
    fun setCategoryList(categoryList: List<CategoryModel>?) {
        categoryModelList = categoryList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesAdapter.ViewHolder {
        val view: View
        view = if (viewType == SINGLE_TYPE) {
            LayoutInflater.from(parent.context).inflate(R.layout.single_item_view, parent, false)
        } else {
            LayoutInflater.from(parent.context)
                .inflate(R.layout.single_item_last_view, parent, false)
        }
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoriesAdapter.ViewHolder, position: Int) {
        if (position < categoryModelList!!.size) {
            val (id, name) = categoryModelList!![position]
            holder.textList.setText(name.getTranslatedString(holder.bodyContainer.context))
            if (mIsEditMode) {
                holder.cancelBtn.visibility = View.VISIBLE
                holder.pencilSvg.visibility = View.VISIBLE
                holder.cancelBtn.setOnClickListener { categoryAdapterCallback.showDeleteDialog(id!!) }
            } else {
                holder.cancelBtn.visibility = View.INVISIBLE
                holder.pencilSvg.visibility = View.INVISIBLE
            }
            holder.bodyContainer.setOnClickListener {
                if (mIsEditMode) {
                    categoryAdapterCallback.showModifyDialog(id!!, name.getTranslatedString(holder.bodyContainer.context))
                } else {
                    categoryAdapterCallback.showExercises(categoryModelList!![position])
                }
            }
        } else {
            holder.textList.setOnClickListener { categoryAdapterCallback.showCreationDialog() }
        }
    }

    fun setEditMode(isEditMode: Boolean) {
        mIsEditMode = isEditMode
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return if (categoryModelList != null) {
            categoryModelList!!.size + 1
        } else 0
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < categoryModelList!!.size) {
            SINGLE_TYPE
        } else {
            CategoriesAdapter.Companion.SINGLE_LAST_TYPE
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cancelBtn: ImageButton by lazy { itemView.findViewById(R.id.cancel_single_item_btn) }
        val textList: TextView by lazy { itemView.findViewById(R.id.textList) }
        val container: ConstraintLayout by lazy { itemView.findViewById(R.id.container) }
        val bodyContainer: ConstraintLayout by lazy { itemView.findViewById(R.id.bodyContainer) }
        val pencilSvg: ImageView by lazy { itemView.findViewById(R.id.edit_mode_pencil) }
    }

    companion object {
        const val SINGLE_TYPE = 1
        const val SINGLE_LAST_TYPE = 2
    }
}
