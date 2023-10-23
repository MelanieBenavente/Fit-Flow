package app.fit.fitndflow.domain.model

import java.io.Serializable

data class SerieModel(val reps: Int? = null, val kg: Double? = null, var exercise: ExerciseModel? = null): Serializable
