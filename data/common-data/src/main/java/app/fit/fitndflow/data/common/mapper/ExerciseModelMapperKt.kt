package app.fit.fitndflow.data.common.mapper

import app.fit.fitndflow.data.common.dto.ExerciseDto
import com.fit.fitndflow.app.domain.common.models.ExerciseModel
import com.fit.fitndflow.app.domain.common.models.SerieModel

class ExerciseModelMapperKt {

    companion object {
        @JvmStatic
        fun toModel(exerciseDtoList: List<ExerciseDto>): List<ExerciseModel> {
            val exerciseModelList: MutableList<ExerciseModel> = mutableListOf()
            for (exerciseDto: ExerciseDto in exerciseDtoList) {
                val lastFirstSerie = SerieModel(
                    reps = exerciseDto.lastFirstSerie?.reps,
                    kg = exerciseDto.lastFirstSerie?.weight
                )
                val record = exerciseDto.record?.let { SerieModel(reps = it.reps, kg = it.weight) }

                val exerciseModel = ExerciseModel(
                    exerciseDto.id!!,
                    exerciseDto.exerciseName.toModel(),
                    mutableListOf(),
                    lastFirstSerie,
                    record
                )
                exerciseModelList!!.add(exerciseModel)
            }
            return exerciseModelList
        }

        @JvmStatic
        fun toModel(exerciseDto: ExerciseDto): ExerciseModel {

            val lastFirstSerie = SerieModel(
                reps = exerciseDto.lastFirstSerie?.reps,
                kg = exerciseDto.lastFirstSerie?.weight
            )
            val record = exerciseDto.record?.let { SerieModel(reps = it.reps, kg = it.weight) }

            val exerciseModel = ExerciseModel(
                exerciseDto.id!!,
                exerciseDto.exerciseName.toModel(),
                exerciseDto.serieList?.map { serieDto ->
                    val isRecord = serieDto.reps == record?.reps && serieDto.weight == record?.kg
                    SerieModel(serieDto.serieId, serieDto.reps, serieDto.weight, isRecord)
                }.orEmpty().toMutableList(),
                lastFirstSerie,
                record
            )
            return exerciseModel
        }
    }
}