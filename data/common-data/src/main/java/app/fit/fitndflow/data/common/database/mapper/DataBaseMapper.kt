package app.fit.fitndflow.data.common.database.mapper

import app.fit.fitndflow.data.common.database.entities.CategoryEntity
import app.fit.fitndflow.data.common.database.entities.CategoryWithExercisesEntity
import app.fit.fitndflow.data.common.database.entities.ExerciseEntity
import com.fit.fitndflow.app.domain.common.models.CategoryModel
import com.fit.fitndflow.app.domain.common.models.ExerciseModel
import com.fit.fitndflow.app.domain.common.models.StringInLanguagesModel

fun CategoryEntity.toModel() = CategoryModel(id, StringInLanguagesModel(nameEs, nameEn))
fun ExerciseEntity.toModel() = ExerciseModel(id, StringInLanguagesModel(nameEs, nameEn), mutableListOf(), null, null)
fun CategoryWithExercisesEntity.toModel() = CategoryModel(category.id, StringInLanguagesModel(category.nameEs, category.nameEn), exercises.map { it.toModel() }.toMutableList() )