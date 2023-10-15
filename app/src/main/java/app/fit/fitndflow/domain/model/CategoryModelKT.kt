package app.fit.fitndflow.domain.model

import java.io.Serializable

data class CategoryModelKT(val id: Int? = null, var name: String, var excerciseList: List<ExcerciseModel>? = null): Serializable
