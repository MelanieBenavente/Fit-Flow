package app.fit.fitndflow.domain.model.mapper

import app.fit.fitndflow.data.dto.exercises.ExerciseDto
import app.fit.fitndflow.domain.model.ExerciseModel

class ExerciseModelMapperKt {

    companion object {
        @JvmStatic
        fun toModel(exerciseDtoList: List<ExerciseDto>) : List<ExerciseModel> {
            val exerciseModelList : MutableList<ExerciseModel> = mutableListOf()
            for (exerciseDto : ExerciseDto in exerciseDtoList) {
                val exerciseModel = ExerciseModel(
                    exerciseDto.id!!,
                    exerciseDto.exerciseName!!.spanish!!,
                    mutableListOf()
                )
                exerciseModelList!!.add(exerciseModel)
            }
            return exerciseModelList
        }
    }
}