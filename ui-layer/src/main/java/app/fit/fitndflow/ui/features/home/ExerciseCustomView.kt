package app.fit.fitndflow.ui.features.home

import android.content.Context
import android.widget.LinearLayout
import android.widget.TextView
import app.fit.fitndflow.ui.R
import app.fit.fitndflow.ui.features.common.getTranslatedString
import com.fit.fitndflow.app.domain.common.models.ExerciseModel

class ExerciseCustomView(
    context: Context?,
    exercise: ExerciseModel,
    exerciseClickCallback: ExerciseClickCallback) : LinearLayout(context) {
    private var textView: TextView? = null
    private var container: LinearLayout? = null

    init {
        inflate(getContext(), R.layout.item_training_exercises_home, this)
        bindView()
        setOnClickListener { exerciseClickCallback.showExerciseTrainingDetail(exercise) }
        textView?.setText(exercise.name.getTranslatedString(this.context))
        for (i in exercise.serieList!!.indices) {
            val serie = exercise.serieList!![i]
            val isFirst = i == 0
            val serieView = SerieCustomView(
                getContext(),
                serie
            )
            container!!.addView(serieView)
        }
    }

    private fun bindView() {
        textView = findViewById(R.id.txt_exercise)
        container = findViewById(R.id.series_container)
    }
}
