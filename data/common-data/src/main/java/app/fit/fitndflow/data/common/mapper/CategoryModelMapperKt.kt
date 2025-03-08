package app.fit.fitndflow.data.common.mapper

import app.fit.fitndflow.data.common.dto.CategoryDto
import app.fit.fitndflow.data.common.dto.ExerciseDto
import com.fit.fitndflow.app.domain.common.models.CategoryModel
import com.fit.fitndflow.app.domain.common.models.ExerciseModel
import com.fit.fitndflow.app.domain.common.models.SerieModel

class CategoryModelMapperKt {

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
                categoryModelList.add(categoryModel)

                for (exerciseDto: ExerciseDto in categoryDto.exerciseDtoList) {
                    val lastFirstSerie = SerieModel(reps = exerciseDto.lastFirstSerie?.reps, kg = exerciseDto.lastFirstSerie?.weight)
                    val record = exerciseDto.record?.let { SerieModel(reps = it.reps, kg = it.weight) }
                    val exerciseModel = ExerciseModel(
                        exerciseDto.id!!,
                        exerciseDto.exerciseName.toModel(),
                        exerciseDto.serieList?.map { serieDto ->
                            val isRecord = serieDto.reps == record?.reps && serieDto.weight == record?.kg
                            SerieModel(serieDto.serieId, serieDto.reps, serieDto.weight, isRecord)
                        }?.toMutableList() ?: mutableListOf(),
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
