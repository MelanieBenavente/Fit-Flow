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

private fun firstSerieToModel(firstReps: Int, firstWeight: Double): SerieModel? =
    if (firstReps == 0 && firstWeight == 0.0) {
        null
    } else {
        SerieModel(reps = firstReps, kg = firstWeight)
    }

fun CategoryEntity.toModel() = CategoryModel(id, StringInLanguagesModel(nameEs, nameEn))
fun ExerciseEntity.toModel() =
    ExerciseModel(
        id,
        StringInLanguagesModel(nameEs, nameEn),
        mutableListOf(),
        firstSerieToModel(firstReps, firstWeight),
        recordToModel(recordHeight, recordReps)
    )

fun CategoryWithExercisesEntity.toModel() = CategoryModel(
    category.id,
    StringInLanguagesModel(category.nameEs, category.nameEn),
    exercises.map { it.toModel() }.toMutableList()
)

fun SerieEntity.toModel() = SerieModel(id, reps, weight)
fun ExerciseWithSeriesEntity.toModel() = ExerciseModel(
    exercise.id,
    StringInLanguagesModel(exercise.nameEs, exercise.nameEn),
    series.map { it.toModel() }.toMutableList(),
    firstSerieToModel(exercise.firstReps, exercise.firstWeight),
    recordToModel(exercise.recordHeight, exercise.recordReps)
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
        lastFirstSerie = firstSerieToModel(first().firstReps, first().firstWeight)
    )


}
