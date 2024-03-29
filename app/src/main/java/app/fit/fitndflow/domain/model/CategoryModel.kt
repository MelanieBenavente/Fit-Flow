package app.fit.fitndflow.domain.model

import java.io.Serializable

data class CategoryModel(val id: Int? = null, var name: String, var exerciseList: MutableList<ExerciseModel>? = null): Serializable
