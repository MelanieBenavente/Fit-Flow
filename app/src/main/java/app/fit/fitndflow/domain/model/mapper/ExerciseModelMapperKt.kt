package app.fit.fitndflow.domain.model.mapper

import app.fit.fitndflow.data.dto.exercises.ExerciseDto
import app.fit.fitndflow.domain.model.ExerciseModel
import app.fit.fitndflow.domain.model.SerieModel

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
                val record =
                    SerieModel(reps = exerciseDto.record?.reps, kg = exerciseDto.record?.weight)
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
            val record =
                SerieModel(reps = exerciseDto.record?.reps, kg = exerciseDto.record?.weight)
            val exerciseModel = ExerciseModel(
                exerciseDto.id!!,
                exerciseDto.exerciseName.toModel(),
                exerciseDto.serieList?.map { serieDto ->
                    SerieModel(serieDto.serieId, serieDto.reps, serieDto.weight)
                }?.toMutableList(),
                lastFirstSerie,
                record
            )
            return exerciseModel
        }
    }
}