package app.fit.fitndflow.domain.model.mapper

import app.fit.fitndflow.data.dto.categories.CategoryDto
import app.fit.fitndflow.data.dto.categories.ExerciseDto
import app.fit.fitndflow.domain.model.CategoryModel
import app.fit.fitndflow.domain.model.ExerciseModel

class CategoryModelMapperKt {
    companion object {
        @JvmStatic
        fun toModel(categoryDtoList : List<CategoryDto>) : List<CategoryModel> {
            val categoryModelList : MutableList<CategoryModel> = mutableListOf()
            for (categoryDto : CategoryDto in categoryDtoList) {
                val categoryModel = CategoryModel(categoryDto.id!!, categoryDto.name!!.spanish!!, mutableListOf())
                for (exerciseDto : ExerciseDto in categoryDto.exerciseDtoList) {
                    val exerciseModel = ExerciseModel(exerciseDto.id, exerciseDto.exerciseName!!.spanish!!)
                    categoryModel.exerciseList!!.add(exerciseModel)
                }
                categoryModelList!!.add(categoryModel)
            }
            return categoryModelList
        }
    }
}
