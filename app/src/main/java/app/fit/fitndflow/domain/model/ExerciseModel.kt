package app.fit.fitndflow.domain.model

import java.io.Serializable

data class ExerciseModel(val id: Int? = null, val name: String, var serieList : MutableList<SerieModel>? = null): Serializable
