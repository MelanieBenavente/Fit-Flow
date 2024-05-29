package app.fit.fitndflow.ui.features.exercises

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import app.fit.fitndflow.domain.model.ExerciseModel
import app.fit.fitndflow.ui.R
import app.fit.fitndflow.ui.features.common.getTranslatedString
import app.fit.fitndflow.ui.features.training.SerieAdapterCallback


class ExercisesAdapter(
    private var exerciseModelList: List<ExerciseModel>?,
    private val serieAdapterCallback: SerieAdapterCallback) :
    RecyclerView.Adapter<ExercisesAdapter.ViewHolder>() {
    private var mIsEditMode = false
    fun setExerciseModelList(exerciseModelList: List<ExerciseModel>?) {
        this.exerciseModelList = exerciseModelList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View
        view = if (viewType == SINGLE_TYPE) {
            LayoutInflater.from(parent.context).inflate(R.layout.single_item_view, parent, false)
        } else {
            LayoutInflater.from(parent.context)
                .inflate(R.layout.single_item_last_view, parent, false)
        }
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position < exerciseModelList!!.size) {
            val (id, name) = exerciseModelList!![position]
            holder.textList.setText(name.getTranslatedString(holder.bodyContainer.context))
            if (mIsEditMode) {
                holder.cancelBtn.visibility = View.VISIBLE
                holder.pencilSvg.visibility = View.VISIBLE
                holder.cancelBtn.setOnClickListener { serieAdapterCallback.showDeleteDialog(id!!) }
            } else {
                holder.cancelBtn.visibility = View.INVISIBLE
                holder.pencilSvg.visibility = View.INVISIBLE
            }
            holder.bodyContainer.setOnClickListener {
                if (mIsEditMode) {
                    serieAdapterCallback.showModifyDialog(id!!, name.getTranslatedString(holder.bodyContainer.context))
                } else {
                    serieAdapterCallback.showSeries(exerciseModelList!![position])
                }
            }
        } else {
            holder.textList.setText(R.string.exercise_new)
            holder.textList.setOnClickListener { serieAdapterCallback.showCreationDialog() }
        }
    }

    fun setEditMode(isEditMode: Boolean) {
        mIsEditMode = isEditMode
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return if (exerciseModelList != null) {
            exerciseModelList!!.size + 1
        } else 0
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < exerciseModelList!!.size) {
            SINGLE_TYPE
        } else {
            SINGLE_LAST_TYPE
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val container: ConstraintLayout by lazy { itemView.findViewById(R.id.container) }
        val bodyContainer: ConstraintLayout by lazy { itemView.findViewById(R.id.bodyContainer) }
        val textList: TextView by lazy { itemView.findViewById(R.id.textList) }
        val cancelBtn: ImageButton by lazy { itemView.findViewById(R.id.cancel_single_item_btn) }
        val pencilSvg: ImageView by lazy { itemView.findViewById(R.id.edit_mode_pencil) }
    }

    companion object {
        const val SINGLE_TYPE = 1
        const val SINGLE_LAST_TYPE = 2
    }
}
