package app.fit.fitndflow.ui.features.home

import android.content.Context
import android.widget.LinearLayout
import android.widget.TextView
import app.fit.fitndflow.ui.R
import app.fit.fitndflow.ui.features.common.getTranslatedString
import com.fit.fitndflow.app.domain.common.models.CategoryModel

class CategoryCustomView(
    context: Context?,
    category: CategoryModel,
    exerciseClickCallback: ExerciseClickCallback) : LinearLayout(context) {
    private var textView: TextView? = null
    private var container: LinearLayout? = null

    init {
        inflate(getContext(), R.layout.item_training_category_home, this)
        bindView()
        textView?.setText(category.name.getTranslatedString(this.context))
        for (exercise in category.exerciseList!!) {
            val exerciseView = ExerciseCustomView(getContext(), exercise, exerciseClickCallback)
            container!!.addView(exerciseView)
        }
    }

    private fun bindView() {
        textView = findViewById(R.id.cat_name)
        container = findViewById(R.id.exercises_container)
    }
}
