package com.fit.fitndflow.app.domain.trainings.model

import com.fit.fitndflow.app.domain.common.models.SerieModel


data class SerieInfoWrapper(val serieList : List<SerieModel>, val showRecord: Boolean, val newRecord : SerieModel?)