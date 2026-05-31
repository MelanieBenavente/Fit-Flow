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

private fun recordToModel(recordHeight: Double, recordReps: Int): SerieModel? =
    if (recordHeight == 0.0 && recordReps == 0) {
        null
    } else {
        SerieModel(reps = recordReps, kg = recordHeight, isRecord = true)
    }

fun CategoryEntity.toModel() = CategoryModel(id, StringInLanguagesModel(nameEs, nameEn))
fun ExerciseEntity.toModel() =
    ExerciseModel(
        id = id,
        name = StringInLanguagesModel(nameEs, nameEn),
        serieList = mutableListOf(),
        lastFirstSerie = SerieModel(reps = firstReps, kg = firstWeight),
        record = recordToModel(recordHeight, recordReps)
    )

fun CategoryWithExercisesEntity.toModel() = CategoryModel(
    category.id,
    StringInLanguagesModel(category.nameEs, category.nameEn),
    exercises.map { it.toModel() }.toMutableList()
)

fun SerieEntity.toModel() = SerieModel(id, reps, weight)
fun ExerciseWithSeriesEntity.toModel() = ExerciseModel(
    id = exercise.id,
    name = StringInLanguagesModel(exercise.nameEs, exercise.nameEn),
    serieList = series.map { it.toModel() }.toMutableList(),
    lastFirstSerie = SerieModel(id = exercise.id, reps = exercise.firstReps, kg = exercise.firstWeight),
    record = recordToModel(exercise.recordHeight, exercise.recordReps)
)

fun CategoryWithExercisesAndSeriesEntity.toModel() =
    category.toModel().copy(exerciseList = exercises.map { it.toModel() }.toMutableList())

fun List<ExerciseSerieFlat>.toModel(record: SerieModel?): ExerciseModel {
    val exerciseList = map { serie ->
        SerieModel(
            reps = serie.reps,
            kg = serie.weight,
            isRecord = record != null && serie.weight == record.kg && serie.reps == record.reps
        )
    }.toMutableList()

    return ExerciseModel(
        id = first().exerciseId,
        name = StringInLanguagesModel(first().exerciseNameEs, first().exerciseNameEn),
        serieList = exerciseList,
        record = record,
        lastFirstSerie = SerieModel(reps = first().firstReps, kg = first().firstWeight),
    )


}
