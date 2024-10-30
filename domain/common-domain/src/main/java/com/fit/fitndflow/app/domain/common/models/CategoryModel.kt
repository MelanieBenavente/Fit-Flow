package com.fit.fitndflow.app.domain.common.models

import java.io.Serializable

data class CategoryModel(val id: Int? = null, var name: StringInLanguagesModel, var exerciseList: MutableList<ExerciseModel>? = null): Serializable
