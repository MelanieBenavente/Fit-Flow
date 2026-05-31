package com.fit.fitndflow.app.domain.common.models

import java.io.Serializable

data class ExerciseModel(val id: Int? = null, val name: StringInLanguagesModel, var serieList: MutableList<SerieModel> = mutableListOf(), var lastFirstSerie: SerieModel?, var record: SerieModel?): Serializable
