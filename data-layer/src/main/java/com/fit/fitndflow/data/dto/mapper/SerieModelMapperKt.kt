package com.fit.fitndflow.data.dto.mapper

import app.fit.fitndflow.domain.model.SerieModel
import com.fit.fitndflow.data.dto.trainings.AddSerieResponseDto
import com.fit.fitndflow.data.dto.trainings.SerieDto

class SerieModelMapperKt {
    companion object {
        @JvmStatic
        fun toModel(addSerieResponseDto : AddSerieResponseDto) : List<SerieModel> {
            val serieModelList : MutableList<SerieModel> = mutableListOf()
            for (serieActualDto : SerieDto in addSerieResponseDto.serieListDto) {
                val serieActualModel = SerieModel(serieActualDto.serieId!!, serieActualDto.reps,serieActualDto.weight)
                serieModelList!!.add(serieActualModel)
            }
            return serieModelList
        }
    }
}