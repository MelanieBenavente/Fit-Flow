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
                    name = categoryDto.name.toModel(),
                    exerciseList = mutableListOf()
                )
                categoryModelList!!.add(categoryModel)

                for (exerciseDto: ExerciseDto in categoryDto.exerciseDtoList) {
                    val lastFirstSerie = SerieModel(reps = exerciseDto.lastFirstSerie?.reps, kg = exerciseDto.lastFirstSerie?.weight)
                    val record = SerieModel(reps = exerciseDto.record?.reps, kg = exerciseDto.record?.weight)
                    val exerciseModel = ExerciseModel(
                        exerciseDto.id!!,
                        exerciseDto.exerciseName.toModel(),
                        exerciseDto.serieList?.map { serieDto ->
                            val isRecord = serieDto.reps == record.reps && serieDto.weight == record.kg
                            SerieModel(serieDto.serieId, serieDto.reps, serieDto.weight, isRecord)
                        }?.toMutableList(),
                        lastFirstSerie,
                        record
                    )
                    categoryModel.exerciseList!!.add(exerciseModel)
                }
            }
            return categoryModelList
        }
    }
}
