package app.fit.fitndflow.data.trainings.mapper

import app.fit.fitndflow.data.common.dto.SerieDto
import app.fit.fitndflow.data.trainings.dto.AddSerieResponseDto
import app.fit.fitndflow.domain.model.SerieModel

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
} //fixme!!!!! delete if not used