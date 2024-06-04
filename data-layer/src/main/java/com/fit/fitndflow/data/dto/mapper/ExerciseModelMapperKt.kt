package com.fit.fitndflow.data.dto.mapper

import app.fit.fitndflow.domain.model.ExerciseModel
import app.fit.fitndflow.domain.model.SerieModel
import com.fit.fitndflow.data.dto.exercises.ExerciseDto

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
                }?.toMutableList(),
                lastFirstSerie,
                record
            )
            return exerciseModel
        }
    }
}