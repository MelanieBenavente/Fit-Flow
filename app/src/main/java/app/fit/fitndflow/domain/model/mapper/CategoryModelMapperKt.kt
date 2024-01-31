package app.fit.fitndflow.domain.model.mapper

import app.fit.fitndflow.data.dto.categories.CategoryDto
import app.fit.fitndflow.data.dto.exercises.ExerciseDto
import app.fit.fitndflow.data.dto.trainings.SerieDto
import app.fit.fitndflow.domain.model.CategoryModel
import app.fit.fitndflow.domain.model.ExerciseModel
import app.fit.fitndflow.domain.model.SerieModel

class CategoryModelMapperKt {

    //transforma de DTO A MODEL
    companion object {
        @JvmStatic
        fun toModel(categoryDtoList: List<CategoryDto>): List<CategoryModel> {
            val categoryModelList: MutableList<CategoryModel> = mutableListOf()
            for (categoryDto: CategoryDto in categoryDtoList) {
                val categoryModel = CategoryModel(
                    id = categoryDto.id!!,
                    name = categoryDto.name?.spanish!!,
                    exerciseList = mutableListOf()
                )
                categoryModelList!!.add(categoryModel)

                for (exerciseDto: ExerciseDto in categoryDto.exerciseDtoList) {
                    val exerciseModel = ExerciseModel(
                        id = exerciseDto.id,
                        name = exerciseDto.exerciseName!!.spanish!!,
                        serieList = mutableListOf()
                    )
                    categoryModel.exerciseList!!.add(exerciseModel)
                    if(exerciseDto.serieList != null) {
                        for (serieDto: SerieDto in exerciseDto.serieList!!) {
                            val serieModel =
                                SerieModel(serieDto.serieId, serieDto.reps, serieDto.weight)
                            exerciseModel.serieList?.add(serieModel)
                        }
                    }
                }
            }
            return categoryModelList
        }
    }
}
