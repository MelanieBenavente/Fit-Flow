package app.fit.fitndflow.domain.model.mapper

import app.fit.fitndflow.data.dto.categories.CategoryDto
import app.fit.fitndflow.data.dto.exercises.ExerciseDto
import app.fit.fitndflow.data.dto.trainings.AddSerieResponseDto
import app.fit.fitndflow.data.dto.trainings.SerieDto
import app.fit.fitndflow.domain.model.CategoryModel
import app.fit.fitndflow.domain.model.ExerciseModel
import app.fit.fitndflow.domain.model.SerieModel

class SerieModelMapperKt {
    companion object {
        @JvmStatic
        fun toModel(addSerieResponseDto : AddSerieResponseDto) : List<SerieModel> {
            val serieModelList : MutableList<SerieModel> = mutableListOf()
            for (serieActualDto : SerieDto in addSerieResponseDto.serieListDto) {
                val serieActualModel = SerieModel(serieActualDto.serieId!!, serieActualDto.reps!!,serieActualDto.weight!!)
                serieModelList!!.add(serieActualModel)
            }
            return serieModelList
        }
    }
}