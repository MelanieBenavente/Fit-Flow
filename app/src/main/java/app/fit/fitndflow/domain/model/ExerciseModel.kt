package app.fit.fitndflow.domain.model

import java.io.Serializable

data class ExerciseModel(val id: Int? = null, val name: String, val serieList : List<SerieModel>? = null): Serializable
