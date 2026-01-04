package app.fit.fitndflow.data.common.database.mapper

import app.fit.fitndflow.data.common.database.entities.CategoryEntity
import app.fit.fitndflow.data.common.database.entities.CategoryWithExercisesAndSeriesEntity
import app.fit.fitndflow.data.common.database.entities.CategoryWithExercisesEntity
import app.fit.fitndflow.data.common.database.entities.ExerciseEntity
import app.fit.fitndflow.data.common.database.entities.ExerciseSerieFlat
import app.fit.fitndflow.data.common.database.entities.ExerciseWithSeriesEntity
import app.fit.fitndflow.data.common.database.entities.SerieEntity
import com.fit.fitndflow.app.domain.common.models.CategoryModel
import com.fit.fitndflow.app.domain.common.models.ExerciseModel
import com.fit.fitndflow.app.domain.common.models.SerieModel
import com.fit.fitndflow.app.domain.common.models.StringInLanguagesModel

fun CategoryEntity.toModel() = CategoryModel(id, StringInLanguagesModel(nameEs, nameEn))
fun ExerciseEntity.toModel() = ExerciseModel(id, StringInLanguagesModel(nameEs, nameEn), mutableListOf(), null, null)
fun CategoryWithExercisesEntity.toModel() = CategoryModel(category.id, StringInLanguagesModel(category.nameEs, category.nameEn), exercises.map { it.toModel() }.toMutableList() )
fun SerieEntity.toModel() = SerieModel(id, reps, weight, false) //todo record
fun ExerciseWithSeriesEntity.toModel() = ExerciseModel(exercise.id, StringInLanguagesModel(exercise.nameEs, exercise.nameEn), series.map { it.toModel() }.toMutableList(), null, null) //todo record
fun CategoryWithExercisesAndSeriesEntity.toModel() = category.toModel().copy(exerciseList = exercises.map { it.toModel() }.toMutableList())
fun List<ExerciseSerieFlat>.toModel() = ExerciseModel(id = first().exerciseId, name = StringInLanguagesModel(first().exerciseNameEs, first().exerciseNameEn), serieList = map { SerieModel(reps = it.reps, kg = it.weight) }.toMutableList(), record = null, lastFirstSerie = null)